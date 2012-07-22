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
import org.intelligentsia.keystone.kernel.service.Service;
import org.intelligentsia.keystone.kernel.service.ServiceProvider;

/**
 * {@link KernelContext} see as an interface between client and micro kernel.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public interface KernelContext {

	/**
	 * Find a {@link ServiceProvider} for specified service.
	 * 
	 * @param service
	 *            service class name
	 * @return ServiceProvider instance.
	 */
	public ServiceProvider find(Class<? extends Service> service) throws KeystoneRuntimeException;

	/**
	 * @return an {@link EventPublisher} instance.
	 */
	public EventPublisher getEventPublisher();
}
