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
package org.intelligentsia.keystone.api.artifacts.repository;

import java.io.File;

import org.intelligentsia.keystone.api.artifacts.ResourceDoesNotExistException;
import org.intelligentsia.keystone.api.artifacts.TransferFailedException;

/**
 * Repository Service Interface.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface RepositoryService {
	/**
	 * Put a new resource.
	 * 
	 * @param resource
	 *            resource destination.
	 * @param source
	 *            source file.
	 * @throws ResourceDoesNotExistException
	 *             if source does not exists
	 * @throws TransferFailedException
	 *             if error occurs when transferring data.
	 */
	public void put(String resource, File source) throws ResourceDoesNotExistException, TransferFailedException;

	/**
	 * @param resource
	 * @return a file instance of specified resource.
	 * @throws ResourceDoesNotExistException
	 *             if source does not exists
	 * @throws TransferFailedException
	 *             if error occurs when transferring data.
	 */
	public File get(String resource) throws ResourceDoesNotExistException, TransferFailedException;

	/**
	 * @param resource
	 * @return true if resource exist.
	 * @throws TransferFailedException
	 *             if error occurs when transferring data.
	 */
	public boolean exists(String resource) throws TransferFailedException;

	/**
	 * @param resource
	 *            resource to delete
	 * @throws ResourceDoesNotExistException
	 *             if source does not exists
	 * @throws TransferFailedException
	 *             if error occurs when transferring data.
	 * @return true if delete is completed
	 */
	public boolean delete(String resource) throws ResourceDoesNotExistException;
}
