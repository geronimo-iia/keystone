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
package org.intelligentsia.keystone.kernel.artifact;

import java.util.Collection;

import org.intelligentsia.keystone.api.artifacts.ArtifactIdentifier;
import org.intelligentsia.keystone.api.artifacts.KeystoneRuntimeException;
import org.intelligentsia.keystone.kernel.Service;

/**
 * KernelArtifactService declare methods to manage artifact load/unload.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public interface KernelArtifactService extends Service {

	/**
	 * @return an unmodifiable collection of loaded artifact identifier.
	 */
	public Collection<ArtifactIdentifier> findAllArtifactIdentifiers();

	/**
	 * Check if an artifact with the specified artifact identifier is managed by
	 * this instance of kernel service.
	 * 
	 * @param artifactIdentifier
	 *            artifact Identifier
	 * @return true if specified artifact Identifier is managed by this kernel
	 *         instance.
	 */
	public boolean contains(ArtifactIdentifier artifactIdentifier);

	/**
	 * Find specified artifact.
	 * 
	 * @param artifactIdentifier
	 * @return ArtifactContext instance or null if none is found
	 * @throws KeystoneRuntimeException
	 *             if an error occurs
	 */
	public ArtifactContext find(ArtifactIdentifier artifactIdentifier) throws KeystoneRuntimeException;

	/**
	 * Load specified artefact.
	 * 
	 * @param artifactIdentifier
	 *            artifact Identifier
	 * @param isolationLevel
	 *            isolation level
	 * @throws KeystoneRuntimeException
	 *             if error occurs
	 * @return an ArtifactContext instance.
	 */
	public ArtifactContext load(ArtifactIdentifier artifactIdentifier, IsolationLevel isolationLevel) throws KeystoneRuntimeException;

	/**
	 * Unload specified artefact.
	 * 
	 * @param artifactIdentifier
	 *            artifact Identifier
	 * @throws KeystoneRuntimeException
	 *             if error occurs
	 */
	public void unload(ArtifactIdentifier artifactIdentifier) throws KeystoneRuntimeException;

}
