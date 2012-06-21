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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.intelligentsia.keystone.api.artifacts.ArtifactIdentifier;
import org.intelligentsia.keystone.api.artifacts.ArtifactsService;
import org.intelligentsia.keystone.api.artifacts.DefaultArtifactsService;
import org.intelligentsia.keystone.api.artifacts.KeystoneRuntimeException;
import org.intelligentsia.keystone.api.artifacts.Resource;
import org.intelligentsia.keystone.api.artifacts.repository.GroupRepositoryService;
import org.intelligentsia.keystone.api.kernel.handler.CompositeArtifactLoaderHandler;
import org.xeustechnologies.jcl.JarClassLoader;

/**
 * 
 * DefaultMicroKernel.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class DefaultMicroKernel implements Microkernel, Initializable, Disposable, ArtifactLoader {

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
	 * Map of ArtifactIdentifier and ArtifactContext.
	 */
	private final Map<ArtifactIdentifier, DefaultArtifactContext> artifacts = new HashMap<ArtifactIdentifier, DefaultArtifactContext>();

	/**
	 * CompositeArtifactLoaderHandler instance.
	 */
	private final CompositeArtifactLoaderHandler artifactLoaderHandler = new CompositeArtifactLoaderHandler();

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

		artifactsService = null;
		jarClassLoader = null;
		repositoryService = null;
		// clear all context
		artifacts.clear();
		// clear all artifact loader handler.
		artifactLoaderHandler.clear();
	}

	/**
	 * @return an unmodifiable set of loaded artifact identifier.
	 */
	@Override
	public Set<ArtifactIdentifier> list() {
		return Collections.unmodifiableSet(artifacts.keySet());
	}

	/**
	 * @param artifactIdentifier
	 *            artifact Identifier
	 * @return true if specified artifact Identifier is managed by this kernel
	 *         instance.
	 */
	@Override
	public boolean contains(final ArtifactIdentifier artifactIdentifier) {
		return artifacts.containsKey(artifactIdentifier);
	}

	@Override
	public DefaultArtifactContext load(ArtifactIdentifier artifactIdentifier, IsolationLevel isolationLevel) throws KeystoneRuntimeException{
		return null;
	}

	/**
	 * Load specified artefact.
	 * 
	 * @param artifactIdentifier
	 *            artifact Identifier
	 * @throws KeystoneRuntimeException
	 *             if error occurs
	 * @return true if loaded, false if it was previously loaded.
	 */
	@Override
	public boolean load(final ArtifactIdentifier artifactIdentifier) throws KeystoneRuntimeException {
		if (!contains(artifactIdentifier)) {
			DefaultArtifactContext defaultArtifactContext = new DefaultArtifactContext(artifactIdentifier);
			final Resource resource = artifactsService.get(artifactIdentifier);
			try {
				// set local resource
				defaultArtifactContext.setLocalResource(resource.getLocalFile().toURI().toURL());
				// initialize classpath
				jarClassLoader.add(resource.getLocalFile().toURI().toURL());

				JarClassLoader classLoader = new JarClassLoader();
				classLoader.getParentLoader().setEnabled(false);

				defaultArtifactContext.setClassLoader(jarClassLoader);
				defaultArtifactContext.setIsolationLevel(IsolationLevel.NONE);
			} catch (MalformedURLException e) {
				throw new KeystoneRuntimeException(e);
			}
			artifacts.put(defaultArtifactContext.getArtifactIdentifier(), defaultArtifactContext);
			artifactLoaderHandler.handle(resource);
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

}
