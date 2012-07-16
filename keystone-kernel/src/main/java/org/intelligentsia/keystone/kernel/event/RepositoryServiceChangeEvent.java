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

import org.intelligentsia.keystone.api.artifacts.repository.RepositoryService;
import org.intelligentsia.keystone.kernel.RepositoryServer;

/**
 * {@link RepositoryServiceChangeEvent} raised when {@link RepositoryService} is
 * added/removed from {@link RepositoryServer}.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class RepositoryServiceChangeEvent {

	public enum State {
		ADDED, REMOVED
	}

	private final RepositoryService repositoryService;

	private final State state;

	/**
	 * Build a new instance of RepositoryServiceChangeEvent.java.
	 * 
	 * @param repositoryService
	 * @param state
	 */
	public RepositoryServiceChangeEvent(final RepositoryService repositoryService, final State state) {
		super();
		this.repositoryService = repositoryService;
		this.state = state;
	}

	/**
	 * @return the repositoryService
	 */
	public final RepositoryService getRepositoryService() {
		return repositoryService;
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
		return "RepositoryServiceChangeEvent [repositoryService=" + repositoryService + ", state=" + state + "]";
	}

}
