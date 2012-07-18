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

import org.intelligentsia.keystone.api.artifacts.KeystoneRuntimeException;
import org.intelligentsia.keystone.api.artifacts.Version;
import org.intelligentsia.keystone.kernel.service.Service;

/**
 * {@link ServiceServer} manage a collection of {@link Service} class name.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface ServiceServer extends Iterable<Class<? extends Service>> {

	/**
	 * Register a new instance of {@link Service} with specified service class
	 * name on specified {@link ArtifactContext}.
	 * 
	 * @param artifactContext
	 *            artifact Context instance which own {@link Service} instance
	 * @param serviceClassName
	 *            service Class Name
	 * @param service
	 *            {@link Service} instance to register
	 * @throws KeystoneRuntimeException
	 *             if error occurs
	 */
	public void register(ArtifactContext artifactContext, Class<Service> serviceClassName, Service service) throws KeystoneRuntimeException;

	/**
	 * Unregister specified {@link Service}.
	 * 
	 * @param artifactContext
	 *            artifact Context instance which own {@link Service} instance
	 * @param serviceClassName
	 *            {@link Service} class name to unregister
	 * @throws KeystoneRuntimeException
	 *             if error occurs
	 */
	public void unregister(ArtifactContext artifactContext, Class<Service> serviceClassName) throws KeystoneRuntimeException;

	/**
	 * Find specified service.
	 * 
	 * @param service
	 *            service class name
	 * @return service instance or null is none is found.
	 */
	public Service find(Class<? extends Service> service) throws KeystoneRuntimeException;

	/**
	 * Find specified service which is compatible with specified {@link version}
	 * .
	 * 
	 * @param service
	 *            service class name
	 * @param version
	 *            a version
	 * @return service instance or null is none is found.
	 */
	public Service find(Class<? extends Service> service, Version version) throws KeystoneRuntimeException;
}
