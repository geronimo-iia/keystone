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
package org.intelligentsia.keystone.kernel;

import java.io.PrintStream;

import org.intelligentsia.keystone.api.artifacts.repository.RepositoryService;
import org.intelligentsia.keystone.kernel.impl.DefaultArtifactServer;
import org.intelligentsia.keystone.kernel.impl.DefaultEventBusServer;
import org.intelligentsia.keystone.kernel.impl.DefaultKernel;
import org.intelligentsia.keystone.kernel.impl.DefaultRepositoryServer;

/**
 * KernelBuilder implement a builder fo {@link Kernel}.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class KernelBuilder {

	private RepositoryServer repositoryServer;
	private ArtifactServer artifactServer;
	private EventBusServer eventBusServer;
	private PrintStream errStream;

	/**
	 * Build a new instance of KernelBuilder with all default server.
	 */
	public KernelBuilder() {
		eventBusServer = new DefaultEventBusServer();
		repositoryServer = new DefaultRepositoryServer();
		artifactServer = new DefaultArtifactServer();
		errStream = System.err;
	}

	/**
	 * @return a new {@link Kernel} instance.
	 */
	public Kernel build() {
		return new DefaultKernel(repositoryServer, artifactServer, eventBusServer, errStream);
	}

	public KernelBuilder setEventBusServer(EventBusServer eventBusServer) {
		this.eventBusServer = eventBusServer;
		return this;
	}

	public KernelBuilder setRepositoryServer(RepositoryServer repositoryServer) {
		this.repositoryServer = repositoryServer;
		return this;
	}

	public KernelBuilder addRepositoryService(RepositoryService... repositoryServices) {
		for (RepositoryService repositoryService : repositoryServices) {
			repositoryServer.add(repositoryService);
		}
		return this;
	}

	public KernelBuilder setArtifactServer(ArtifactServer artifactServer) {
		this.artifactServer = artifactServer;
		return this;
	}

	public KernelBuilder setErrStream(PrintStream errStream) {
		this.errStream = errStream;
		return this;
	}

}
