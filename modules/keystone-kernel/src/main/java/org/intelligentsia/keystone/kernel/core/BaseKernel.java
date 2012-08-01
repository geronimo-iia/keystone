/**
 *        Licensed to the Apache Software Foundation (ASF) under one
 *        or more contributor license agreements.  See the NOTICE file
 *        distributed with this work for additional information
 *        regarding copyright ownership.  The ASF licenses this file
 *        to you under the Apache License, Version 2.0 (the
 *        "License"); you may not use this file except in compliance
 *        with the License.  You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *        Unless required by applicable law or agreed to in writing,
 *        software distributed under the License is distributed on an
 *        "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *        KIND, either express or implied.  See the License for the
 *        specific language governing permissions and limitations
 *        under the License.
 *
 */
package org.intelligentsia.keystone.kernel.core;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.intelligentsia.keystone.api.Preconditions;
import org.intelligentsia.keystone.api.StringUtils;
import org.intelligentsia.keystone.api.artifacts.ArtifactIdentifier;
import org.intelligentsia.keystone.api.artifacts.KeystoneRuntimeException;
import org.intelligentsia.keystone.kernel.ArtifactContext;
import org.intelligentsia.keystone.kernel.ArtifactServer;
import org.intelligentsia.keystone.kernel.EventBusServer;
import org.intelligentsia.keystone.kernel.IsolationLevel;
import org.intelligentsia.keystone.kernel.Kernel;
import org.intelligentsia.keystone.kernel.KernelExecutor;
import org.intelligentsia.keystone.kernel.KernelProviderService;
import org.intelligentsia.keystone.kernel.KernelServer;
import org.intelligentsia.keystone.kernel.RepositoryServer;
import org.intelligentsia.keystone.kernel.ServiceServer;
import org.intelligentsia.keystone.kernel.core.artifact.DefaultArtifactContext;
import org.xeustechnologies.jcl.JarClassLoader;

/**
 * BaseKernel implementation.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class BaseKernel implements Kernel, Iterable<KernelServer> {

	/**
	 * {@link Map} of {@link KernelServer} instance.
	 */
	private final Map<Class<? extends KernelServer>, KernelServer> servers = new LinkedHashMap<Class<? extends KernelServer>, KernelServer>();

	/**
	 * Internal {@link RepositoryServer} instance.
	 */
	private final RepositoryServer repositoryServer;
	/**
	 * Internal {@link ArtifactServer} instance.
	 */
	private final ArtifactServer artifactServer;
	/**
	 * Internal {@link EventBusServer} instance.
	 */
	private final EventBusServer eventBusServer;
	/**
	 * Internal {@link ServiceServer} instance.
	 */
	private final ServiceServer serviceServer;
	/**
	 * {@link Runnable} instance : main kernel process.
	 */
	private final Runnable mainKernelProcess;
	/**
	 * Error Stream instance.
	 */
	private final PrintStream errStream;
	/**
	 * {@link KernelExecutor} instance.
	 */
	private final KernelExecutor kernelExecutor;
	/**
	 * {@link ArtifactContext} kernel instance.
	 */
	private final ArtifactContext kernelArtifactContext;

	/**
	 * awaitTermination time out and unit. Every seconde per default.
	 */
	private long awaitTerminationTimeout = 1;

	private TimeUnit awaitTerminationTimeUnit = TimeUnit.SECONDS;

	/**
	 * Build a new instance of BaseKernel.java.
	 * 
	 * @param eventBusServer
	 *            {@link EventBusServer} instance
	 * @param repositoryServer
	 *            {@link RepositoryServer} instance
	 * @param artifactServer
	 *            {@link ArtifactServer} instance
	 * @param serviceServer
	 *            {@link ServiceServer} instance
	 * @param errStream
	 *            {@link PrintStream} instance to output message
	 * @param mainKernelProcess
	 *            {@link Runnable} optional runnable process
	 * @param kernelExecutor
	 *            {@link KernelExecutor} instance
	 * @param classLoader
	 *            {@link JarClassLoader} instance
	 * @throws NullPointerException
	 *             if one of server, kernelExecutor or classLoader is null.
	 */
	public BaseKernel(final EventBusServer eventBusServer, final RepositoryServer repositoryServer, final ArtifactServer artifactServer, final ServiceServer serviceServer, final PrintStream errStream, final Runnable mainKernelProcess,
			final KernelExecutor kernelExecutor, final JarClassLoader classLoader) throws NullPointerException {
		super();
		// create context
		this.kernelArtifactContext = loadKernelArtifactContext(Preconditions.checkNotNull(classLoader, "classLoader"));

		// basic kernel element
		this.errStream = errStream;
		this.mainKernelProcess = mainKernelProcess;
		this.kernelExecutor = Preconditions.checkNotNull(kernelExecutor, "kernelExecutor");

		// initialize base kernel server member
		this.eventBusServer = register(EventBusServer.class, Preconditions.checkNotNull(eventBusServer, "eventBusServer"));
		this.repositoryServer = register(RepositoryServer.class, Preconditions.checkNotNull(repositoryServer, "repositoryServer"));
		this.artifactServer = register(ArtifactServer.class, Preconditions.checkNotNull(artifactServer, "artifactServer"));
		this.serviceServer = register(ServiceServer.class, Preconditions.checkNotNull(serviceServer, "serviceServer"));
	}

	@Override
	public RepositoryServer getRepositoryServer() {
		return repositoryServer;
	}

	@Override
	public ArtifactServer getArtifactServer() {
		return artifactServer;
	}

	@Override
	public EventBusServer getEventBus() {
		return eventBusServer;
	}

	@Override
	public ServiceServer getServiceServer() {
		return serviceServer;
	}

	@Override
	public KernelExecutor getKernelExecutor() {
		return kernelExecutor;
	}

	@Override
	public void dmesg(final String message, final Object... args) {
		if (errStream != null) {
			errStream.println(StringUtils.format(message, args));
		}
	}

	@Override
	public void run() {
		try {
			// initializing all server resource
			initializeKernelServer();
			// register kernel service
			getServiceServer().register(kernelArtifactContext, KernelProviderService.class, new DefaultKernelProviderService(this));

			// add main task to scheduler
			if (mainKernelProcess != null) {
				kernelExecutor.execute(mainKernelProcess);
			}
			// waiting termination
			try {
				while (!kernelExecutor.awaitTermination(awaitTerminationTimeout, awaitTerminationTimeUnit)) {
					// do something ?
				}
			} catch (InterruptedException e) {
			}

			// unregister service
			getServiceServer().unregister(kernelArtifactContext, KernelProviderService.class);
		} finally {
			// destroy all server resource
			destroyKernelServer();
		}
	}

	/**
	 * @return kernel {@link ArtifactContext} instance.
	 */
	public ArtifactContext getArtifactContext() {
		return kernelArtifactContext;
	}

	/**
	 * Register a new {@link KernelServer} with specified class name.
	 * 
	 * @param className
	 * @param instance
	 * @return registerd instance.
	 * @throws KeystoneRuntimeException
	 *             if a {@link KernelServer} is ever registered with specified
	 *             class name.
	 */
	public <K extends KernelServer> K register(final Class<K> className, final K instance) throws KeystoneRuntimeException {
		if (servers.containsKey(className)) {
			throw new KeystoneRuntimeException(className + " is ever registered");
		}
		servers.put(className, instance);
		return instance;
	}

	/**
	 * Un register a {@link KernelServer} instance associated with specified
	 * class name.
	 * 
	 * @param className
	 * @return removed {@link KernelServer}.
	 */
	public KernelServer unregister(final Class<? extends KernelServer> className) {
		return servers.remove(className);
	}

	@Override
	public Iterator<KernelServer> iterator() {
		return servers.values().iterator();
	}

	/**
	 * Initialize kernel server in registration order.
	 * 
	 * @throws KeystoneRuntimeException
	 *             if error occurs.
	 */
	protected void initializeKernelServer() throws KeystoneRuntimeException {
		dmesg("initialize kernel server");
		for (final KernelServer kernelServer : servers.values()) {
			try {
				kernelServer.initialize(this);
			} catch (final KeystoneRuntimeException e) {
				dmesg("error when initializing %s: %s", kernelServer.getName(), e.getMessage());
				throw e;
			}
		}
	}

	/**
	 * Destroy kernel server in reverse registration order.
	 */
	protected void destroyKernelServer() {
		dmesg("destroy kernel server");
		// reverse order
		final List<KernelServer> list = new ArrayList<KernelServer>(servers.values());
		Collections.reverse(list);
		// destroy
		for (final KernelServer kernelServer : list) {
			try {
				kernelServer.destroy();
			} catch (final Throwable e) {
				dmesg("error when destroying %s: %s", kernelServer.getName(), e.getMessage());
			}
		}
	}

	/**
	 * Instanciate Kernel {@link ArtifactContext} instance by loading version
	 * information from maven. If an error occur, no version will be available.
	 * 
	 * @param classLoader
	 * @return kernel {@link ArtifactContext} instance.
	 */
	private ArtifactContext loadKernelArtifactContext(final JarClassLoader classLoader) {
		ArtifactIdentifier artifactIdentifier;
		try {
			artifactIdentifier = ArtifactIdentifier.parse(classLoader, "org.intelligents-ia.keystone", "keystone-kernel");
		} catch (final KeystoneRuntimeException e) {
			dmesg("Error when loading maven information on artifact identifier, continue without version information (%s).", e.getMessage());
			artifactIdentifier = ArtifactIdentifier.parse("org.intelligents-ia.keystone:keystone-kernel");
		}
		final DefaultArtifactContext artifactContext = new DefaultArtifactContext(artifactIdentifier);
		artifactContext.setClassLoader(classLoader);
		artifactContext.setIsolationLevel(IsolationLevel.ISOLATED);
		return artifactContext;
	}

	/**
	 * Set wait terminaison frequency.
	 * 
	 * @param awaitTerminationTimeout
	 * @param timeUnit
	 */
	public void setAwaitTerminationFrequency(long awaitTerminationTimeout, TimeUnit timeUnit) {
		this.awaitTerminationTimeout = awaitTerminationTimeout;
		this.awaitTerminationTimeUnit = timeUnit;
	}
}
