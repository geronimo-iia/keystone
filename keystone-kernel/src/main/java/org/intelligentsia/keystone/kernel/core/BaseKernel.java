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

import org.intelligentsia.keystone.api.artifacts.KeystoneRuntimeException;
import org.intelligentsia.keystone.kernel.ArtifactServer;
import org.intelligentsia.keystone.kernel.EventBusServer;
import org.intelligentsia.keystone.kernel.Kernel;
import org.intelligentsia.keystone.kernel.KernelExecutor;
import org.intelligentsia.keystone.kernel.KernelServer;
import org.intelligentsia.keystone.kernel.RepositoryServer;
import org.intelligentsia.keystone.kernel.ServiceServer;
import org.intelligentsia.utilities.Preconditions;
import org.intelligentsia.utilities.StringUtils;

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
	 * Build a new instance of BaseKernel.java.
	 * 
	 * @param eventBusServer
	 * @param repositoryServer
	 * @param artifactServer
	 * @param serviceServer
	 * @param errStream
	 * @param mainKernelProcess
	 * @param kernelExecutor
	 * @throws NullPointerException
	 *             if one of server is null.
	 */
	public BaseKernel(final EventBusServer eventBusServer, final RepositoryServer repositoryServer, final ArtifactServer artifactServer, final ServiceServer serviceServer, final PrintStream errStream, final Runnable mainKernelProcess,
			final KernelExecutor kernelExecutor) throws NullPointerException {
		super();
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
			// TODO add time info
			errStream.println(StringUtils.format(message, args));
		}
	}

	@Override
	public void run() {
		try {
			// initializing all server resource
			initializeKernelServer();
			// do something
			if (mainKernelProcess != null) {
				mainKernelProcess.run();
			}
		} finally {
			// destroy all server resource
			destroyKernelServer();
		}
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

}
