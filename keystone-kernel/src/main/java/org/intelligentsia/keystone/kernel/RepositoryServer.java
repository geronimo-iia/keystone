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

import org.intelligentsia.keystone.api.artifacts.repository.RepositoryService;

/**
 * RepositoryServer act as a {@link RepositoryService}, declare methods to
 * manage multiple {@link RepositoryService} a one aggregate, and acts as a
 * collection of {@link RepositoryService}.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface RepositoryServer extends RepositoryService, Iterable<RepositoryService>, KernelServer {

	/**
	 * Add specified Repository to this server.
	 * 
	 * @param repositoryService
	 *            repository Service instance to add
	 * @throws NullPointerException
	 *             if repositoryService is null
	 */
	public void add(RepositoryService repositoryService) throws NullPointerException;

	/**
	 * Remove specified Repository from this server.
	 * 
	 * @param repositoryService
	 *            repository Service instance to remove
	 * @throws NullPointerException
	 *             if repositoryService is null
	 */
	public void remove(RepositoryService repositoryService) throws NullPointerException;

}
