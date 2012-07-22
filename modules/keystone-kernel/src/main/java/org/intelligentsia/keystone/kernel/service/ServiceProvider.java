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
package org.intelligentsia.keystone.kernel.service;

import org.intelligentsia.keystone.api.artifacts.ArtifactIdentifier;

/**
 * ServiceProvider.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 * @param <S>
 *            class which extends {@link Service}
 */
public interface ServiceProvider extends Iterable<ServiceRegistryKey> {

	/**
	 * @return an {@link Iterable} instance on {@link ArtifactIdentifier}
	 *         contains in this {@link ServiceProvider}.
	 */
	public Iterable<ArtifactIdentifier> keys();

	/**
	 * @param key
	 *            {@link ArtifactIdentifier}
	 * @return True if specified key is present in this {@link ServiceProvider}.
	 */
	public boolean contains(final ArtifactIdentifier key);

	/**
	 * @param key
	 *            {@link ArtifactIdentifier}
	 * @return ServiceRegistryKey instance for specified key or null if not
	 *         exists.
	 */
	public ServiceRegistryKey get(final ArtifactIdentifier key);
}
