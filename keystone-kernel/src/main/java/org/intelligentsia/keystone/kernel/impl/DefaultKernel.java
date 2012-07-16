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
package org.intelligentsia.keystone.kernel.impl;

import java.io.PrintStream;

import org.intelligentsia.keystone.kernel.ArtifactServer;
import org.intelligentsia.keystone.kernel.EventBusServer;
import org.intelligentsia.keystone.kernel.Kernel;
import org.intelligentsia.keystone.kernel.RepositoryServer;
import org.intelligentsia.utilities.StringUtils;

/**
 * DefaultKernel implementation.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class DefaultKernel implements Kernel {

	private RepositoryServer repositoryServer;
	private ArtifactServer artifactServer;
	private EventBusServer eventBusServer;
	private PrintStream errStream;

	/**
	 * Build a new instance of DefaultKernel.java.
	 * 
	 * @param repositoryServer
	 * @param artifactServer
	 * @param eventBusServer
	 * @param errStream
	 */
	public DefaultKernel(RepositoryServer repositoryServer, ArtifactServer artifactServer, EventBusServer eventBusServer, PrintStream errStream) {
		super();
		this.repositoryServer = repositoryServer;
		this.artifactServer = artifactServer;
		this.eventBusServer = eventBusServer;
		this.errStream = errStream;
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
	public void dmesg(String message, Object... args) {
		errStream.println(StringUtils.format(message, args));
	}

}
