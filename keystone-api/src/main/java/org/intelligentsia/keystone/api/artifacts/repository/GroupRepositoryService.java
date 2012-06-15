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
package org.intelligentsia.keystone.api.artifacts.repository;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.intelligentsia.keystone.api.artifacts.ResourceDoesNotExistException;
import org.intelligentsia.keystone.api.artifacts.TransferFailedException;

/**
 * 
 * GroupRepositoryService implement a Read Only composite of RepositoryService.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class GroupRepositoryService implements RepositoryService, Iterable<RepositoryService> {

	private final List<RepositoryService> repositoryServices = new ArrayList<RepositoryService>();

	/**
	 * Build a new instance of GroupRepositoryService.java.
	 */
	public GroupRepositoryService() {
		super();
	}

	/**
	 * Build a new instance of GroupRepositoryService.java.
	 * 
	 * @param repositoryServices
	 */
	public GroupRepositoryService(List<RepositoryService> repositoryServices) {
		super();
		if (repositoryServices != null) {
			this.repositoryServices.addAll(repositoryServices);
		}
	}

	@Override
	public void put(String resource, File source) throws ResourceDoesNotExistException, TransferFailedException {
		boolean done = Boolean.FALSE;
		Iterator<RepositoryService> iterator = repositoryServices.iterator();
		while (!done && iterator.hasNext()) {
			try {
				iterator.next().put(resource, source);
				done = Boolean.TRUE;
			} catch (TransferFailedException e) {
			} catch (ResourceDoesNotExistException e) {
			}
		}
		if (!done) {
			throw new TransferFailedException();
		}
	}

	@Override
	public File get(String resource) throws ResourceDoesNotExistException, TransferFailedException {
		File result = null;
		Iterator<RepositoryService> iterator = repositoryServices.iterator();
		while (result == null && iterator.hasNext()) {
			try {
				result = iterator.next().get(resource);
			} catch (TransferFailedException e) {
			} catch (ResourceDoesNotExistException e) {
			}
		}
		return result;
	}

	@Override
	public boolean exists(String resource) throws TransferFailedException {
		boolean result = Boolean.FALSE;
		Iterator<RepositoryService> iterator = repositoryServices.iterator();
		while (!result && iterator.hasNext()) {
			try {
				result = iterator.next().exists(resource);
			} catch (TransferFailedException e) {
			}
		}
		return result;
	}

	@Override
	public boolean delete(String resource) throws ResourceDoesNotExistException {
		boolean done = Boolean.FALSE;
		Iterator<RepositoryService> iterator = repositoryServices.iterator();
		while (!done && iterator.hasNext()) {
			try {
				done = iterator.next().delete(resource);
			} catch (ResourceDoesNotExistException e) { 
			}
		}
		return done;
	}

	/**
	 * @return
	 * @see java.util.List#isEmpty()
	 */
	public boolean isEmpty() {
		return repositoryServices.isEmpty();
	}

	/**
	 * @param o
	 * @return
	 * @see java.util.List#contains(java.lang.Object)
	 */
	public boolean contains(RepositoryService o) {
		return repositoryServices.contains(o);
	}

	/**
	 * @param e
	 * @return
	 * @see java.util.List#add(java.lang.Object)
	 */
	public boolean add(RepositoryService e) {
		return repositoryServices.add(e);
	}

	/**
	 * @param o
	 * @return
	 * @see java.util.List#remove(java.lang.Object)
	 */
	public boolean remove(RepositoryService o) {
		return repositoryServices.remove(o);
	}

	@Override
	public Iterator<RepositoryService> iterator() {
		return repositoryServices.iterator();
	}

}
