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
package org.intelligentsia.keystone.api.artifacts;

import org.intelligentsia.keystone.api.artifacts.pom.Pom;
import org.intelligentsia.keystone.api.artifacts.repository.metadata.Metadata;

/**
 * Artifacts Service API.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public interface ArtifactsService {

	/**
	 * Downloads specified artifact from repositories.
	 * 
	 * @param identifier
	 *            Artifact identifier
	 * @return Resource instance.
	 * @throws ResourceDoesNotExistException
	 *             if source does not exists
	 * @throws TransferFailedException
	 *             if error occurs when transferring data.
	 */
	public Resource get(final ArtifactIdentifier identifier) throws ResourceDoesNotExistException, TransferFailedException;

	/**
	 * @param identifier
	 *            artifact Identifier
	 * @return Metadata instance for specified artifact
	 * @throws ResourceDoesNotExistException
	 *             if source does not exists
	 * @throws TransferFailedException
	 *             if error occurs when transferring data.
	 */
	public Metadata getMetadata(final ArtifactIdentifier identifier) throws ResourceDoesNotExistException, TransferFailedException;

	/**
	 * Resolve an ArtifactIdentifier for specified parameter.
	 * 
	 * @param group
	 *            group identifier
	 * @param artifact
	 *            artifact identifier
	 * @param releaseOnly
	 *            if true, try to resolve artifact without snapshot
	 * @return artifact identifier
	 * @throws ResourceDoesNotExistException
	 *             if source does not exists
	 * @throws TransferFailedException
	 *             if error occurs when transferring data.
	 */
	public ArtifactIdentifier resolve(final String group, final String artifact, boolean releaseOnly) throws ResourceDoesNotExistException, TransferFailedException;

	/**
	 * @param identifier
	 *            artifact Identifier
	 * @return pom instance for specified artifact. If artifact must be
	 *         resolved, only release will be used.
	 * @throws ResourceDoesNotExistException
	 *             if source does not exists
	 * @throws TransferFailedException
	 *             if error occurs when transferring data.
	 */
	public Pom getPom(final ArtifactIdentifier identifier) throws ResourceDoesNotExistException, TransferFailedException;

}
