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
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
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
import org.intelligentsia.keystone.kernel.KernelProviderService;
import org.intelligentsia.keystone.kernel.KernelServer;
import org.intelligentsia.keystone.kernel.RepositoryServer;
import org.intelligentsia.keystone.kernel.ServiceServer;
import org.intelligentsia.keystone.kernel.core.artifact.DefaultArtifactContext;
import org.intelligentsia.keystone.kernel.init.Predicate;
import org.xeustechnologies.jcl.JarClassLoader;

/**
 * BaseKernel implementation.
 * 
 * 
 * <p>
 * if no termination {@link Predicate} is set, a default one will be created
 * with this paremeters
 * </p>
 * <ul>
 * <li>create a {@link Predicate} instance which evaluate to
 * {@link Boolean#TRUE} when all kernel task are done.</li>
 * <li>Time out is et to 1 second</li>
 * </ul>
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
	 * {@link State}.
	 */
	private State state = State.SOL;

	/**
	 * Flag to halt kernel.
	 */
	private boolean halt = Boolean.FALSE;

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
	public RepositoryServer repositoryServer() {
		return repositoryServer;
	}

	@Override
	public ArtifactServer artifactServer() {
		return artifactServer;
	}

	@Override
	public EventBusServer eventBus() {
		return eventBusServer;
	}

	@Override
	public ServiceServer serviceServer() {
		return serviceServer;
	}

	@Override
	public void dmesg(final String message, final Object... args) {
		if (errStream != null) {
			errStream.println(StringUtils.format(message, args));
		}
	}

	@Override
	public State state() {
		return state;
	}

	@Override
	public <V> Future<V> submit(final Callable<V> task) {
		return kernelExecutor.submit(task);
	}

	@Override
	public <V> Future<V> submit(final Runnable task, final V result) {
		return kernelExecutor.submit(task, result);
	};

	/**
	 * Initialize kernel instance.
	 * 
	 */
	public void initialize() {
		if (State.SOL.equals(state)) {
			dmesg("Initializing Kernel");
			// pause all futur submitted task
			kernelExecutor.pause();
			// initializing all server resource
			initializeKernelServer();
			// register kernel service
			serviceServer().register(kernelArtifactContext, KernelProviderService.class, new DefaultKernelProviderService(this));
			// add main task to scheduler
			if (mainKernelProcess != null) {
				kernelExecutor.submit(mainKernelProcess, Boolean.TRUE);
			}
			// resume all submitted task
			kernelExecutor.resume();
			// up state
			state = State.READY;
		}
	}

	/**
	 * Dispose kernel instance.
	 */
	public void dispose() {
		if (State.READY.equals(state)) {
			dmesg("Disposing Kernel");
			// shutdown executor
			kernelExecutor.shutdown();
			if (!kernelExecutor.awaitTermination()) {
				dmesg("Kernel executor: force Shutdown");
				kernelExecutor.shutdownNow();
			}
			// unregister service
			serviceServer().unregister(kernelArtifactContext, KernelProviderService.class);
			// destroy all server resource
			destroyKernelServer();
			// up state
			state = State.EOL;
		}
	}

	@Override
	public void run() {
		try {
			initialize();

			// waiting termination
			try {
				while (halt || (!kernelExecutor.awaitTermination(awaitTerminationTimeout, awaitTerminationTimeUnit) && eventBusServer.hasPendingEvents())) {
					// yield to another thread
					Thread.yield();
				}
				if (halt) {
					dmesg("Halt Kernel");
				}
			} catch (final InterruptedException e) {
			}
		} finally {
			dispose();
		}
	}

	/**
	 * Halt Kernel.
	 */
	public void halt() {
		halt = true;
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
		dmesg("Initialize kernel server.");
		for (final KernelServer kernelServer : servers.values()) {
			try {
				kernelServer.initialize(this);
			} catch (final KeystoneRuntimeException e) {
				dmesg("Error when initializing %s: %s", kernelServer.getName(), e.getMessage());
				throw e;
			}
		}
	}

	/**
	 * Destroy kernel server in reverse registration order.
	 */
	protected void destroyKernelServer() {
		dmesg("Destroying all kernel server instance.");
		// reverse order
		final List<KernelServer> list = new ArrayList<KernelServer>(servers.values());
		Collections.reverse(list);
		// destroy
		for (final KernelServer kernelServer : list) {
			try {
				kernelServer.destroy();
			} catch (final Throwable e) {
				dmesg("Error when destroying %s: %s", kernelServer.getName(), e.getMessage());
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
	public void setAwaitTerminationFrequency(final long awaitTerminationTimeout, final TimeUnit timeUnit) {
		this.awaitTerminationTimeout = awaitTerminationTimeout;
		this.awaitTerminationTimeUnit = timeUnit;
	}

}
