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
package org.intelligentsia.keystone.api.kernel;

import java.util.Set;

import org.intelligentsia.keystone.api.artifacts.ArtifactIdentifier;
import org.intelligentsia.keystone.api.artifacts.KeystoneRuntimeException;

/**
 * <p>
 * Microkernel Responsibility:
 * </p>
 * <ul>
 * <li>Provides core mechanisms</li>
 * <li>Offers communication facilities</li>
 * <li>Encapsulates system dependencies</li>
 * <li>Manages and controls resources</li>
 * </ul>
 * 
 * <p>
 * “Perfection is not achieved when there is nothing left to add, but when there
 * is nothing left to take away”<br />
 * --Antoine de St. Exupery
 * </p>
 * 
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface Microkernel {
	/**
	 * @return an unmodifiable set of loaded artifact identifier.
	 */
	public Set<ArtifactIdentifier> list();

	/**
	 * @param artifactIdentifier
	 *            artifact Identifier
	 * @return true if specified artifact Identifier is managed by this kernel
	 *         instance.
	 */
	public boolean contains(ArtifactIdentifier artifactIdentifier);

	/**
	 * Load specified artefact.
	 * 
	 * @param artifactIdentifier
	 *            artifact Identifier
	 * @throws KeystoneRuntimeException
	 *             if error occurs
	 * @return true if loaded, false if it was previously loaded.
	 */
	public boolean load(ArtifactIdentifier artifactIdentifier) throws KeystoneRuntimeException;
}
