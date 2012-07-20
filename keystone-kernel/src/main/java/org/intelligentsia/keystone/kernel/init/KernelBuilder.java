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
package org.intelligentsia.keystone.kernel.init;

import java.io.PrintStream;
import java.util.LinkedHashMap;
import java.util.Map;

import org.intelligentsia.keystone.api.artifacts.KeystoneRuntimeException;
import org.intelligentsia.keystone.api.artifacts.repository.RepositoryService;
import org.intelligentsia.keystone.kernel.ArtifactServer;
import org.intelligentsia.keystone.kernel.EventBusServer;
import org.intelligentsia.keystone.kernel.Kernel;
import org.intelligentsia.keystone.kernel.KernelServer;
import org.intelligentsia.keystone.kernel.RepositoryServer;
import org.intelligentsia.keystone.kernel.ServiceServer;
import org.intelligentsia.keystone.kernel.core.BaseKernel;
import org.intelligentsia.keystone.kernel.core.DefaultEventBusServer;
import org.intelligentsia.keystone.kernel.core.DefaultRepositoryServer;
import org.intelligentsia.keystone.kernel.core.DefaultServiceServer;
import org.intelligentsia.keystone.kernel.core.artifact.DefaultArtifactServer;

/**
 * KernelBuilder implement a builder fo {@link Kernel}.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class KernelBuilder {

	private PrintStream errStream;
	private Runnable mainKernelProcess;
	private final Map<Class<? extends KernelServer>, KernelServer> servers = new LinkedHashMap<Class<? extends KernelServer>, KernelServer>();

	/**
	 * Build a new instance of KernelBuilder with all default server.
	 */
	public KernelBuilder() {
		addKernelServer(EventBusServer.class, new DefaultEventBusServer());
		addKernelServer(RepositoryServer.class, new DefaultRepositoryServer());
		addKernelServer(ArtifactServer.class, new DefaultArtifactServer());
		addKernelServer(ServiceServer.class, new DefaultServiceServer());
		this.errStream = System.err;
	}

	/**
	 * @return a new {@link Kernel} instance.
	 */
	public Kernel build() {
		return build(this.mainKernelProcess);
	}

	/**
	 * @param mainKernelProcess
	 *            main kernel process
	 * @return a new {@link Kernel} instance.
	 */
	public Kernel build(Runnable mainKernelProcess) {
		return new BaseKernel(getKernelServer(EventBusServer.class), getKernelServer(RepositoryServer.class), getKernelServer(ArtifactServer.class), getKernelServer(ServiceServer.class), errStream, mainKernelProcess);
	}

	public KernelBuilder setEventBusServer(final EventBusServer eventBusServer) {
		return addKernelServer(EventBusServer.class, eventBusServer);
	}

	public KernelBuilder setRepositoryServer(final RepositoryServer repositoryServer) {
		return addKernelServer(RepositoryServer.class, repositoryServer);
	}

	public KernelBuilder setArtifactServer(final ArtifactServer artifactServer) {
		return addKernelServer(ArtifactServer.class, artifactServer);
	}

	public KernelBuilder setServiceServer(final ServiceServer serviceServer) {
		return addKernelServer(ServiceServer.class, serviceServer);
	}

	public KernelBuilder setErrStream(final PrintStream errStream) {
		this.errStream = errStream;
		return this;
	}

	public KernelBuilder setMainKernelProcess(final Runnable mainKernelProcess) {
		this.mainKernelProcess = mainKernelProcess;
		return this;
	}

	public KernelBuilder addRepositoryService(final RepositoryService... repositoryServices) {
		for (final RepositoryService repositoryService : repositoryServices) {
			getKernelServer(RepositoryServer.class).add(repositoryService);
		}
		return this;
	}

	public <K extends KernelServer> KernelBuilder addKernelServer(Class<K> className, K instance) throws KeystoneRuntimeException {
		servers.put(className, instance);
		return this;
	}

	@SuppressWarnings("unchecked")
	protected <T extends KernelServer> T getKernelServer(Class<T> className) {
		return (T) servers.get(className);
	}
}
