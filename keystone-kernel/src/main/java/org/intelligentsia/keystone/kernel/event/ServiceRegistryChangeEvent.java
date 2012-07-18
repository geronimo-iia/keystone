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
package org.intelligentsia.keystone.kernel.event;

import org.intelligentsia.keystone.api.artifacts.ArtifactIdentifier;
import org.intelligentsia.keystone.kernel.ServiceServer;
import org.intelligentsia.keystone.kernel.service.Service;

/**
 * ServiceRegistryChangeEvent raised when {@link Service} is
 * registered/unregistredfrom {@link ServiceServer}.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class ServiceRegistryChangeEvent {

	public enum State {
		REGISTERED, UNREGISTERED
	}

	private final ArtifactIdentifier artifactIdentifier;

	private final Class<Service> serviceClassName;

	private final State state;

	/**
	 * Build a new instance of ServiceRegistryChangeEvent.java.
	 * 
	 * @param artifactIdentifier
	 * @param serviceClassName
	 * @param state
	 */
	public ServiceRegistryChangeEvent(final ArtifactIdentifier artifactIdentifier, final Class<Service> serviceClassName, final State state) {
		super();
		if (artifactIdentifier == null) {
			throw new NullPointerException("artifactIdentifier");
		}
		if (serviceClassName == null) {
			throw new NullPointerException("serviceClassName");
		}
		if (state == null) {
			throw new NullPointerException("state");
		}
		this.artifactIdentifier = artifactIdentifier;
		this.serviceClassName = serviceClassName;
		this.state = state;
	}

	/**
	 * @return the artifactIdentifier
	 */
	public final ArtifactIdentifier getArtifactIdentifier() {
		return artifactIdentifier;
	}

	/**
	 * @return the serviceClassName
	 */
	public final Class<Service> getServiceClassName() {
		return serviceClassName;
	}

	/**
	 * @return the state
	 */
	public final State getState() {
		return state;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ServiceRegistryChangeEvent [artifactIdentifier=" + artifactIdentifier + ", serviceClassName=" + serviceClassName + ", state=" + state + "]";
	}

}