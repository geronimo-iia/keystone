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

import java.util.Iterator;

import org.intelligentsia.keystone.api.artifacts.ArtifactIdentifier;
import org.intelligentsia.keystone.api.artifacts.KeystoneRuntimeException;

/**
 * 
 * ArtifactServer declare methods to manage artifact and acts as a collection of
 * {@link ArtifactIdentifier}.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public interface ArtifactServer extends Iterable<ArtifactIdentifier>, KernelServer {

	/**
	 * Check if an artifact with the specified artifact identifier is managed by
	 * this instance of kernel service.
	 * 
	 * @param artifactIdentifier
	 *            artifact Identifier
	 * @return true if specified artifact Identifier is managed by this kernel
	 *         instance.
	 * @throws NullPointerException
	 *             if artifactIdentifier is null
	 */
	public boolean contains(ArtifactIdentifier artifactIdentifier) throws NullPointerException;

	/**
	 * Find specified artifact.
	 * 
	 * @param artifactIdentifier
	 * @return ArtifactContext instance or null if none is found
	 * @throws KeystoneRuntimeException
	 *             if an error occurs
	 * @throws NullPointerException
	 *             if artifactIdentifier is null
	 */
	public ArtifactContext find(ArtifactIdentifier artifactIdentifier) throws KeystoneRuntimeException, NullPointerException;

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
	 * @throws NullPointerException
	 *             if artifactIdentifier or {@link IsolationLevel} is null
	 */
	public ArtifactContext load(ArtifactIdentifier artifactIdentifier, IsolationLevel isolationLevel) throws KeystoneRuntimeException, NullPointerException;

	/**
	 * Unload specified artefact.
	 * 
	 * @param artifactIdentifier
	 *            artifact Identifier
	 * @throws KeystoneRuntimeException
	 *             if error occurs
	 * @throws NullPointerException
	 *             if artifactIdentifier is null
	 */
	public void unload(ArtifactIdentifier artifactIdentifier) throws KeystoneRuntimeException, NullPointerException;

	/**
	 * Add an {@link ArtifactEntryPointLocalizer} instance.
	 * 
	 * @param artifactEntryPointLocalizer
	 *            {@link ArtifactEntryPointLocalizer} instance to add.
	 * @throws NullPointerException
	 *             if artifactEntryPointLocalizer is null
	 * @return true if {@link ArtifactEntryPointLocalizer} was not already
	 *         present.
	 */
	public boolean addArtifactEntryPointLocalizer(ArtifactEntryPointLocalizer artifactEntryPointLocalizer) throws NullPointerException;

	/**
	 * Remove specified {@link ArtifactEntryPointLocalizer} instance.
	 * 
	 * @param artifactEntryPointLocalizer
	 *            {@link ArtifactEntryPointLocalizer} instance to remove.
	 * @throws NullPointerException
	 *             if artifactEntryPointLocalizer is null
	 * @return true if {@link ArtifactEntryPointLocalizer} instance was found
	 *         and removed.
	 */
	public boolean removeArtifactEntryPointLocalizer(ArtifactEntryPointLocalizer artifactEntryPointLocalizer) throws NullPointerException;

	/**
	 * @return an {@link Iterator} on {@link ArtifactEntryPointLocalizer}.
	 */
	public Iterator<ArtifactEntryPointLocalizer> artifactEntryPointLocalizers();
}
