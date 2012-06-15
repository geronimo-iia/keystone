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

import org.intelligentsia.keystone.api.artifacts.KeystoneRuntimeException;
import org.intelligentsia.keystone.api.artifacts.ResourceDoesNotExistException;
import org.intelligentsia.keystone.api.artifacts.TransferFailedException;

import com.ning.http.client.AsyncHttpClient;

/**
 * 
 * ProxyRepositoryService.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class ProxyRepositoryService implements RepositoryService {

	private final FileRepository local;

	private final ClientHttpRepository target;

	/**
	 * Build a new instance of ProxyRepositoryService.java.
	 * 
	 * @param localStoragePath
	 * @param repository
	 */
	public ProxyRepositoryService(final File localStoragePath, final Repository repository) {
		this(localStoragePath, repository, null);
	}

	/**
	 * Build a new instance of ProxyRepositoryService.java.
	 * 
	 * @param localStoragePath
	 * @param repository
	 * @param httpClient
	 */
	public ProxyRepositoryService(final File localStoragePath, final Repository repository, final AsyncHttpClient httpClient) {
		super();
		local = new FileRepository(localStoragePath);
		target = new ClientHttpRepository(repository, httpClient);
	}

	/**
	 * Build a new instance of ProxyRepositoryService.java.
	 * 
	 * @param local
	 * @param target
	 */
	public ProxyRepositoryService(FileRepository local, ClientHttpRepository target) {
		super();
		this.local = local;
		this.target = target;
	}

	@Override
	public void put(String resource, File source) throws ResourceDoesNotExistException, TransferFailedException {
		try {
			local.put(resource, source);
		} catch (KeystoneRuntimeException e) {
		}
		target.put(resource, source);
	}

	@Override
	public File get(String resource) throws ResourceDoesNotExistException, TransferFailedException {
		try {
			return local.get(resource);
		} catch (KeystoneRuntimeException e) {
			// get it and put in cache
			File result = target.get(resource);
			local.put(resource, result);
			return result;
		}
	}

	@Override
	public boolean exists(String resource) throws TransferFailedException {
		boolean result = false;
		try {
			result = local.exists(resource);
		} catch (TransferFailedException e) {
		}
		if (!result) {
			result = target.exists(resource);
		}
		return result;
	}

	@Override
	public boolean delete(String resource) throws ResourceDoesNotExistException {
		try {
			local.delete(resource);
		} catch (ResourceDoesNotExistException e) {
		}
		return target.delete(resource);
	}

}
