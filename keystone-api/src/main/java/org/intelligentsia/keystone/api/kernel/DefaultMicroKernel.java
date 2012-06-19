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

import java.net.MalformedURLException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.intelligentsia.keystone.api.artifacts.ArtifactIdentifier;
import org.intelligentsia.keystone.api.artifacts.ArtifactsService;
import org.intelligentsia.keystone.api.artifacts.DefaultArtifactsService;
import org.intelligentsia.keystone.api.artifacts.Resource;
import org.intelligentsia.keystone.api.artifacts.ResourceDoesNotExistException;
import org.intelligentsia.keystone.api.artifacts.TransferFailedException;
import org.intelligentsia.keystone.api.artifacts.repository.GroupRepositoryService;
import org.xeustechnologies.jcl.JarClassLoader;

/**
 * 
 * DefaultMicroKernel.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class DefaultMicroKernel implements Microkernel, Initializable, Disposable {

	/**
	 * GroupRepositoryService instance.
	 */
	private GroupRepositoryService repositoryService;
	/**
	 * ArtifactsService instance.
	 */
	private ArtifactsService artifactsService;
	/**
	 * JarClassLoader instance.
	 */
	private JarClassLoader jarClassLoader;

	/**
	 * Set of loaded ArtifactIdentifier.
	 */
	private Set<ArtifactIdentifier> artifactIdentifiers;

	/**
	 * Build a new instance of DefaultMicroKernel.java.
	 */
	public DefaultMicroKernel() {
		super();
	}

	/**
	 * Initialize instance.
	 */
	@Override
	@PostConstruct
	public void initialize() {
		artifactIdentifiers = new HashSet<ArtifactIdentifier>();
		jarClassLoader = new JarClassLoader();
		// repository management
		repositoryService = new GroupRepositoryService();
		artifactsService = new DefaultArtifactsService(repositoryService);
	}

	/**
	 * Free resource.
	 */
	@Override
	@PreDestroy
	public void dispose() {
		artifactIdentifiers = null;
		artifactsService = null;
		jarClassLoader = null;
		repositoryService = null;
	}

	/**
	 * @return an unmodifiable set of loaded artifact identifier.
	 */
	@Override
	public Set<ArtifactIdentifier> list() {
		return Collections.unmodifiableSet(artifactIdentifiers);
	}

	/**
	 * @param artifactIdentifier
	 *            artifact Identifier
	 * @return true if specified artifact Identifier is managed by this kernel
	 *         instance.
	 */
	@Override
	public boolean contains(final ArtifactIdentifier artifactIdentifier) {
		return artifactIdentifiers.contains(artifactIdentifier);
	}

	/**
	 * Load specified artefact.
	 * 
	 * @param artifactIdentifier
	 *            artifact Identifier
	 * @throws MalformedURLException
	 * @throws ResourceDoesNotExistException
	 *             if source does not exists
	 * @throws TransferFailedException
	 *             if error occurs when transferring data.
	 * @return true if loaded, false if it was previously loaded.
	 */
	@Override
	public boolean load(final ArtifactIdentifier artifactIdentifier) throws ResourceDoesNotExistException, TransferFailedException, MalformedURLException {
		if (!artifactIdentifiers.contains(artifactIdentifier)) {
			final Resource resource = artifactsService.get(artifactIdentifier);
			jarClassLoader.add(resource.getLocalFile().toURI().toURL());
			artifactIdentifiers.add(artifactIdentifier);
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
}
