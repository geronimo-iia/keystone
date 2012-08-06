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

import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import org.intelligentsia.keystone.api.artifacts.repository.Repository;

/**
 * KernelConfiguration.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class KernelConfiguration {

	/**
	 * Artifact to load
	 */
	private String artifactIdentifier;

	/**
	 * List of {@link Repository}
	 */
	private List<Repository> repositories = new LinkedList<Repository>();

	/**
	 * Local repository.
	 */
	private URL localRepository;

	/**
	 * Build a new instance of KernelConfiguration.java.
	 */
	public KernelConfiguration() {
		super();
	}

	/**
	 * Build a new instance of KernelConfiguration.java.
	 * 
	 * @param artifactIdentifier
	 * @param repositories
	 * @param proxyServer
	 */
	public KernelConfiguration(final String artifactIdentifier, final List<Repository> repositories) {
		super();
		this.artifactIdentifier = artifactIdentifier;
		if ((repositories != null) && !repositories.isEmpty()) {
			this.repositories.addAll(repositories);
		}
	}

	/**
	 * @return the artifactIdentifier
	 */
	public final String getArtifactIdentifier() {
		return artifactIdentifier;
	}

	/**
	 * @param artifactIdentifier
	 *            the artifactIdentifier to set
	 */
	public final void setArtifactIdentifier(final String artifactIdentifier) {
		this.artifactIdentifier = artifactIdentifier;
	}

	/**
	 * @return the repositories
	 */
	public final List<Repository> getRepositories() {
		return repositories;
	}

	/**
	 * @param repositories
	 *            the repositories to set
	 */
	public final void setRepositories(final List<Repository> repositories) {
		this.repositories = repositories;
	}

	/**
	 * @param repository
	 * @return
	 * @see java.util.List#add(java.lang.Object)
	 */
	public boolean add(final Repository repository) {
		return repositories.add(repository);
	}

	/**
	 * @return the localRepository
	 */
	public final URL getLocalRepository() {
		return localRepository;
	}

	/**
	 * @param localRepository
	 *            the localRepository to set
	 */
	public final void setLocalRepository(final URL localRepository) {
		this.localRepository = localRepository;
	}

}
