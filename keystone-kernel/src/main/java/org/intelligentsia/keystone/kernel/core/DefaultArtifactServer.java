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
/**
 * 
 */
package org.intelligentsia.keystone.kernel.core;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.intelligentsia.keystone.api.artifacts.ArtifactIdentifier;
import org.intelligentsia.keystone.api.artifacts.ArtifactsService;
import org.intelligentsia.keystone.api.artifacts.DefaultArtifactsService;
import org.intelligentsia.keystone.api.artifacts.KeystoneRuntimeException;
import org.intelligentsia.keystone.api.artifacts.Resource;
import org.intelligentsia.keystone.kernel.ArtifactContext;
import org.intelligentsia.keystone.kernel.ArtifactServer;
import org.intelligentsia.keystone.kernel.IsolationLevel;
import org.intelligentsia.keystone.kernel.event.ArtifactContextDestroyedEvent;
import org.intelligentsia.keystone.kernel.event.ArtifactContextInitializedEvent;
import org.xeustechnologies.jcl.CompositeProxyClassLoader;
import org.xeustechnologies.jcl.DelegateProxyClassLoader;
import org.xeustechnologies.jcl.JarClassLoader;

/**
 * DefaultArtifactServer implements {@link ArtifactServer}.
 * 
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class DefaultArtifactServer extends AbstractKernelServer implements ArtifactServer {

	/**
	 * {@link ArtifactsService} instance.
	 * 
	 * @uml.property name="artifactsService"
	 * @uml.associationEnd
	 */
	private ArtifactsService artifactsService;
	/**
	 * Parent {@link JarClassLoader} instance.
	 * 
	 * @uml.property name="parent"
	 */
	private final JarClassLoader parent;
	/**
	 * {@link CompositeProxyClassLoader} instance.
	 * 
	 * @uml.property name="compositeProxyClassLoader"
	 * @uml.associationEnd
	 */
	private final CompositeProxyClassLoader compositeProxyClassLoader;

	/**
	 * Map of {@link ArtifactIdentifier} and {@link ArtifactContext}.
	 */
	private final Map<ArtifactIdentifier, ArtifactContext> artifacts = new HashMap<ArtifactIdentifier, ArtifactContext>();

	/**
	 * Build a new instance of DefaultArtifactServer.java.
	 * 
	 * @param artifactsService
	 *            artifacts Service instance
	 * @throws NullPointerException
	 *             if artifactsService is null
	 */
	public DefaultArtifactServer() {
		super();
		this.parent = JarClassLoaderFactory.initialize();
		compositeProxyClassLoader = new CompositeProxyClassLoader();
		compositeProxyClassLoader.setOrder(15);
		parent.addLoader(compositeProxyClassLoader);
	}

	/**
	 * Initialize internal {@link ArtifactsService}.
	 * 
	 * @see org.intelligentsia.keystone.kernel.core.AbstractKernelServer#onInitialize()
	 */
	@Override
	protected void onInitialize() {
		if (kernel.getRepositoryServer() == null) {
			throw new KeystoneRuntimeException("Repository Server cannot be null");
		}
		this.artifactsService = new DefaultArtifactsService(kernel.getRepositoryServer());
	}

	/**
	 * Unload all artifact.
	 * 
	 * @see org.intelligentsia.keystone.kernel.core.AbstractKernelServer#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		for (final ArtifactIdentifier artifactIdentifier : artifacts.keySet()) {
			try {
				unload(artifactIdentifier);
			} catch (final Throwable e) {
				kernel.dmesg("Error when unloading '%s': %s.", artifactIdentifier, e.getMessage());
			}
		}
		artifacts.clear();
		artifactsService = null;
	}

	/**
	 * @see org.intelligentsia.keystone.kernel.ArtifactServer#contains(org.intelligentsia
	 *      .keystone.api.artifacts.ArtifactIdentifier)
	 */
	@Override
	public boolean contains(final ArtifactIdentifier artifactIdentifier) throws NullPointerException {
		if (artifactIdentifier == null) {
			throw new NullPointerException("artifactIdentifier");
		}
		return artifacts.containsKey(artifactIdentifier);
	}

	/**
	 * @see org.intelligentsia.keystone.kernel.ArtifactServer#find(org.intelligentsia
	 *      .keystone.api.artifacts.ArtifactIdentifier)
	 */
	@Override
	public ArtifactContext find(final ArtifactIdentifier artifactIdentifier) throws KeystoneRuntimeException, NullPointerException {
		if (artifactIdentifier == null) {
			throw new NullPointerException("artifactIdentifier");
		}
		return artifacts.get(artifactIdentifier);
	}

	/**
	 * @see org.intelligentsia.keystone.kernel.ArtifactServer#load(org.intelligentsia
	 *      .keystone.api.artifacts.ArtifactIdentifier,
	 *      org.intelligentsia.keystone.kernel.IsolationLevel)
	 */
	@Override
	public ArtifactContext load(final ArtifactIdentifier artifactIdentifier, final IsolationLevel isolationLevel) throws KeystoneRuntimeException, NullPointerException {
		if (artifactIdentifier == null) {
			throw new NullPointerException("artifactIdentifier");
		}
		if (isolationLevel == null) {
			throw new NullPointerException("isolationLevel");
		}
		if (isDestroying()) {
			throw new KeystoneRuntimeException("Cannot load artifact when destroying server");
		}
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
		artifacts.put(artifactIdentifier, result);
		// raise event
		kernel.getEventBus().publish(new ArtifactContextInitializedEvent(result));
		return result;
	}

	/**
	 * @see org.intelligentsia.keystone.kernel.ArtifactServer#unload(org.intelligentsia
	 *      .keystone.api.artifacts.ArtifactIdentifier)
	 */
	@Override
	public void unload(final ArtifactIdentifier artifactIdentifier) throws KeystoneRuntimeException, NullPointerException {
		// TODO implements unload
		if (!isDestroying()) {
			// now we could change behavior with lifecycle and not raising event
			kernel.getEventBus().publish(new ArtifactContextDestroyedEvent(null));
		}
		throw new KeystoneRuntimeException("not yet implemented");
	}

	/**
	 * Build a new instance of JarClassLoader with only local loader activated.
	 * <p>
	 * All instance have a common parent {@link JarClassLoader} instance. If
	 * {@link IsolationLevel#NONE} then newly classloader is added by
	 * composition to parent.
	 * </p>
	 * 
	 * @param isolationLevel
	 *            isolation level
	 * @return a new instance of JarClassLoader.
	 */
	public JarClassLoader newInstanceOfJarClassLoader(final IsolationLevel isolationLevel) {
		final JarClassLoader classLoader = JarClassLoaderFactory.initialize(new JarClassLoader());
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

	@Override
	public Iterator<ArtifactIdentifier> iterator() {
		return artifacts.keySet().iterator();
	}

	/**
	 * @return class loader instance used as parent.
	 * @uml.property name="parent"
	 */
	public JarClassLoader getParent() {
		return parent;
	}
}
