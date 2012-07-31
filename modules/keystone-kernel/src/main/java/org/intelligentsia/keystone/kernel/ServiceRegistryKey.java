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

import java.io.Serializable;

import org.intelligentsia.keystone.api.artifacts.ArtifactIdentifier;

/**
 * ServiceRegistryKey associate an {@link ArtifactIdentifier} and a
 * {@link Service} instance.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public final class ServiceRegistryKey implements Serializable {
	/**
	 * serialVersionUID:long
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * {@link ArtifactIdentifier} instance.
	 */
	private final ArtifactIdentifier artifactIdentifier;
	/**
	 * {@link Service} instacne.
	 */
	private final transient Service service;

	/**
	 * Build a new instance of ServiceProvider.java.
	 * 
	 * @param artifactIdentifier
	 * @param service
	 * @throws NullPointerException
	 *             if artifactIdentifier or service is null
	 */
	public ServiceRegistryKey(final ArtifactIdentifier artifactIdentifier, final Service service) throws NullPointerException {
		super();
		if (artifactIdentifier == null) {
			throw new NullPointerException("artifactIdentifier");
		}
		if (service == null) {
			throw new NullPointerException("service");
		}
		this.artifactIdentifier = artifactIdentifier;
		this.service = service;
	}

	/**
	 * @return the artifactIdentifier
	 */
	public final ArtifactIdentifier getArtifactIdentifier() {
		return artifactIdentifier;
	}

	/**
	 * @return the service
	 */
	public final Service getService() {
		return service;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ServiceRegistryKey [artifactIdentifier=" + artifactIdentifier + ", service=" + service.getClass().getName() + "]";
	}

}
