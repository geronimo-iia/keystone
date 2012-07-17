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

import org.intelligentsia.keystone.kernel.ArtifactServer;
import org.intelligentsia.keystone.kernel.EventBusServer;
import org.intelligentsia.keystone.kernel.Kernel;
import org.intelligentsia.keystone.kernel.RepositoryServer;
import org.intelligentsia.keystone.kernel.ServiceServer;
import org.intelligentsia.utilities.StringUtils;

/**
 * BaseKernel implementation.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class BaseKernel implements Kernel {

	private final RepositoryServer repositoryServer;
	private final ArtifactServer artifactServer;
	private final EventBusServer eventBusServer;
	private final PrintStream errStream;
	private final ServiceServer serviceServer;

	/**
	 * Build a new instance of BaseKernel.java.
	 * 
	 * @param repositoryServer
	 * @param artifactServer
	 * @param eventBusServer
	 * @param errStream
	 * @param serviceServer
	 */
	public BaseKernel(RepositoryServer repositoryServer, ArtifactServer artifactServer, EventBusServer eventBusServer, PrintStream errStream, ServiceServer serviceServer) {
		super();
		this.repositoryServer = repositoryServer;
		this.artifactServer = artifactServer;
		this.eventBusServer = eventBusServer;
		this.errStream = errStream;
		this.serviceServer = serviceServer;
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
	public void dmesg(final String message, final Object... args) {
		if (errStream != null) {
			errStream.println(StringUtils.format(message, args));
		}
	}

}
