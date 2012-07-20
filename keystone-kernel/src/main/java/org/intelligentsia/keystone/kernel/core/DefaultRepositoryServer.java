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
/**
 * 
 */
package org.intelligentsia.keystone.kernel.core;

import java.io.File;
import java.util.Iterator;

import org.intelligentsia.keystone.api.artifacts.KeystoneRuntimeException;
import org.intelligentsia.keystone.api.artifacts.ResourceDoesNotExistException;
import org.intelligentsia.keystone.api.artifacts.TransferFailedException;
import org.intelligentsia.keystone.api.artifacts.repository.GroupRepositoryService;
import org.intelligentsia.keystone.api.artifacts.repository.RepositoryService;
import org.intelligentsia.keystone.kernel.RepositoryServer;
import org.intelligentsia.keystone.kernel.event.RepositoryServiceChangeEvent;
import org.intelligentsia.utilities.Preconditions;

/**
 * DefaultRepositoryServer implements {@link RepositoryServer} by delegating to
 * a {@link GroupRepositoryService} instance.
 * 
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class DefaultRepositoryServer extends AbstractKernelServer implements RepositoryServer {

	/**
	 * {@link GroupRepositoryService} instance.
	 * 
	 * @uml.property name="repositoryService"
	 * @uml.associationEnd
	 */
	private final GroupRepositoryService groupRepositoryService;

	/**
	 * Build a new instance of DefaultRepositoryServer.java.
	 */
	public DefaultRepositoryServer() {
		groupRepositoryService = new GroupRepositoryService();
	}

	@Override
	protected void onInitialize() {
	}

	@Override
	protected void onDestroy() {
		groupRepositoryService.clear();
	}

	/**
	 * @see org.intelligentsia.keystone.api.artifacts.repository.RepositoryService#put(java.lang.String,
	 *      java.io.File)
	 */
	@Override
	public void put(final String resource, final File source) throws ResourceDoesNotExistException, TransferFailedException {
		groupRepositoryService.put(resource, source);
	}

	/**
	 * @see org.intelligentsia.keystone.api.artifacts.repository.RepositoryService#get(java.lang.String)
	 */
	@Override
	public File get(final String resource) throws ResourceDoesNotExistException, TransferFailedException {
		return groupRepositoryService.get(resource);
	}

	/**
	 * @see org.intelligentsia.keystone.api.artifacts.repository.RepositoryService#exists(java.lang.String)
	 */
	@Override
	public boolean exists(final String resource) throws TransferFailedException {
		return groupRepositoryService.exists(resource);
	}

	/**
	 * @see org.intelligentsia.keystone.api.artifacts.repository.RepositoryService#delete(java.lang.String)
	 */
	@Override
	public boolean delete(final String resource) throws ResourceDoesNotExistException {
		return groupRepositoryService.delete(resource);
	}

	/**
	 * @see org.intelligentsia.keystone.kernel.RepositoryServer#add(org.intelligentsia.keystone.api.artifacts.repository.RepositoryService)
	 */
	@Override
	public void add(final RepositoryService repositoryService) throws NullPointerException {
		if (isDestroying()) {
			throw new KeystoneRuntimeException("Cannot add repositoryService when destroying server");
		}
		if (!this.groupRepositoryService.contains(Preconditions.checkNotNull(repositoryService, "repositoryService"))) {
			this.groupRepositoryService.add(repositoryService);
			kernel.getEventBus().publish(new RepositoryServiceChangeEvent(repositoryService, RepositoryServiceChangeEvent.State.ADDED));
		}
	}

	/**
	 * @see org.intelligentsia.keystone.kernel.RepositoryServer#remove(org.intelligentsia.keystone.api.artifacts.repository.RepositoryService)
	 */
	@Override
	public void remove(final RepositoryService repositoryService) throws NullPointerException {
		this.groupRepositoryService.remove(Preconditions.checkNotNull(repositoryService, "repositoryService"));
		if (!isDestroying()) {
			kernel.getEventBus().publish(new RepositoryServiceChangeEvent(repositoryService, RepositoryServiceChangeEvent.State.REMOVED));
		}
	}

	@Override
	public Iterator<RepositoryService> iterator() {
		return groupRepositoryService.iterator();
	}
}
