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
package org.intelligentsia.keystone.kernel.init;

import java.util.Set;

import org.intelligentsia.keystone.api.artifacts.ArtifactIdentifier;
import org.intelligentsia.keystone.api.artifacts.repository.Repository;

import com.ning.http.client.ProxyServer;

/**
 * KernelConfiguration.
 * 
 * <code>
 * {g:a:v}+ loader as no isolated artifact
 * [repository definition}*
 * </code>
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class KernelConfiguration {

	/**
	 * Artifact to load
	 */
	private ArtifactIdentifier artifactIdentifier;

	/**
	 * {@link Set} of {@link RepositoryConfiguration}.
	 */
	private Set<RepositoryConfiguration> repositories;

	/**
	 * RepositoryConfiguration.
	 * 
	 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
	 */
	public class RepositoryConfiguration {
		private final Repository repository;
		private final ProxyServer proxy;

		/**
		 * Build a new instance of KernelConfiguration.java.
		 * 
		 * @param repository
		 * @param proxy
		 */
		public RepositoryConfiguration(Repository repository, ProxyServer proxy) {
			super();
			this.repository = repository;
			this.proxy = proxy;
		}

		/**
		 * @return the repository
		 */
		public final Repository getRepository() {
			return repository;
		}

		/**
		 * @return the proxy
		 */
		public final ProxyServer getProxy() {
			return proxy;
		}

	}

}
