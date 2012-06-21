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

import org.intelligentsia.keystone.api.artifacts.ArtifactIdentifier;
import org.intelligentsia.keystone.api.artifacts.ArtifactsService;
import org.intelligentsia.keystone.api.artifacts.KeystoneRuntimeException;
import org.intelligentsia.keystone.api.artifacts.Resource;
import org.xeustechnologies.jcl.JarClassLoader;

/**
 * DefaultArtifactLoader implements ArtifactLoader.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class DefaultArtifactLoader implements ArtifactLoader {
	/**
	 * ArtifactsService instance.
	 */
	private final ArtifactsService artifactsService;
	/**
	 * Parent JarClassLoader instance.
	 */
	private final JarClassLoader parent;

	private final CompositeProxyClassLoader compositeProxyClassLoader;

	/**
	 * Build a new instance of DefaultArtifactLoader.java.
	 * 
	 * @param artifactsService
	 *            artifacts Service instance
	 * @throws NullPointerException
	 *             if artifactsService is null
	 */
	public DefaultArtifactLoader(final ArtifactsService artifactsService) throws NullPointerException {
		super();
		if (artifactsService == null) {
			throw new NullPointerException("artifactsService is null");
		}
		this.artifactsService = artifactsService;
		this.parent = initialize(new JarClassLoader());
		compositeProxyClassLoader = new CompositeProxyClassLoader();
		compositeProxyClassLoader.setOrder(15);
		parent.addLoader(compositeProxyClassLoader);
	}

	@Override
	public ArtifactContext load(final ArtifactIdentifier artifactIdentifier, final IsolationLevel isolationLevel) throws KeystoneRuntimeException {
		final DefaultArtifactContext result = new DefaultArtifactContext(artifactIdentifier);
		// get resource
		final Resource resource = artifactsService.get(artifactIdentifier);
		try {
			// set local resource
			result.setLocalResource(resource.getLocalFile().toURI().toURL());
			// initialize classpath
			result.setClassLoader(newInstanceOfJarClassLoader(isolationLevel));
			result.setIsolationLevel(isolationLevel);
		} catch (final MalformedURLException e) {
			throw new KeystoneRuntimeException(e);
		}
		return result;
	}

	/**
	 * @param isolationLevel
	 *            isolation level
	 * @return a new instance of JarClassLoader.
	 */
	public JarClassLoader newInstanceOfJarClassLoader(final IsolationLevel isolationLevel) {
		final JarClassLoader classLoader = initialize(new JarClassLoader());
		classLoader.getSystemLoader().setEnabled(false);
		classLoader.getThreadLoader().setEnabled(false);
		classLoader.getParentLoader().setEnabled(false);
		classLoader.getCurrentLoader().setEnabled(false);
		classLoader.getOsgiBootLoader().setEnabled(false);
		// delegate to parent after local proxy
		final DelegateProxyClassLoader delegateProxyClassLoader = new DelegateProxyClassLoader(parent);
		delegateProxyClassLoader.setOrder(15);
		classLoader.addLoader(delegateProxyClassLoader);
		if (IsolationLevel.NONE.equals(isolationLevel)) {
			// link back to kernel on local proxy
			compositeProxyClassLoader.add(classLoader.getLocalLoader());
		}
		return classLoader;
	}

	/**
	 * Initialize internal priority of proxy loader.
	 * 
	 * @param classLoader
	 *            classLoader to initialize
	 * @return initialized classLoader
	 */
	public JarClassLoader initialize(final JarClassLoader classLoader) {
		classLoader.getSystemLoader().setOrder(50);
		classLoader.getThreadLoader().setOrder(40);
		classLoader.getParentLoader().setOrder(30);
		classLoader.getCurrentLoader().setOrder(20);
		classLoader.getLocalLoader().setOrder(10);
		classLoader.getOsgiBootLoader().setOrder(0);
		classLoader.getOsgiBootLoader().setEnabled(false);
		return classLoader;
	}

	/**
	 * @return class loader instance used as parent.
	 */
	public JarClassLoader getParent() {
		return parent;
	}
}
