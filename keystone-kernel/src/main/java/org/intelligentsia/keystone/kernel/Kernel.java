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

/**
 * Kernel interface which declare core methods.
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
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface Kernel {

	/**
	 * @return a {@link RepositoryServer} instance.
	 */
	public RepositoryServer getRepositoryServer();

	/**
	 * @return a {@link ArtifactServer} instance.
	 */
	public ArtifactServer getArtifactServer();

	/**
	 * @return a {@link EventBusServer} instance.
	 */
	public EventBusServer getEventBus();
}
