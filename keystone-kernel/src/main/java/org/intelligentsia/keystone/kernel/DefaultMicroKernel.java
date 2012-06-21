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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.intelligentsia.keystone.api.artifacts.ArtifactIdentifier;
import org.intelligentsia.keystone.api.artifacts.DefaultArtifactsService;
import org.intelligentsia.keystone.api.artifacts.KeystoneRuntimeException;
import org.intelligentsia.keystone.api.artifacts.repository.GroupRepositoryService;
import org.intelligentsia.keystone.kernel.handler.CompositeArtifactLoaderHandler;
import org.intelligentsia.keystone.kernel.loader.ArtifactContext;
import org.intelligentsia.keystone.kernel.loader.ArtifactLoader;
import org.intelligentsia.keystone.kernel.loader.DefaultArtifactLoader;
import org.intelligentsia.keystone.kernel.loader.IsolationLevel;

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
	 * ArtifactLoader instance.
	 */
	private ArtifactLoader artifactLoader;

	/**
	 * Map of ArtifactIdentifier and ArtifactContext.
	 */
	private final Map<ArtifactIdentifier, ArtifactContext> artifacts = new HashMap<ArtifactIdentifier, ArtifactContext>();

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
		// initialize internal service: repository service
		repositoryService = new GroupRepositoryService();

		// initialize internal service: artifact loader
		artifactLoader = new DefaultArtifactLoader(new DefaultArtifactsService(repositoryService));
	}

	/**
	 * Free resource.
	 */
	@Override
	@PreDestroy
	public void dispose() {
		repositoryService = null;
		artifactLoader = null;
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
	public ArtifactContext load(final ArtifactIdentifier artifactIdentifier, final IsolationLevel isolationLevel) throws KeystoneRuntimeException {
		final ArtifactContext context = artifactLoader.load(artifactIdentifier, isolationLevel);
		artifacts.put(context.getArtifactIdentifier(), context);
		artifactLoaderHandler.handle(context);
		return context;
	}

}
