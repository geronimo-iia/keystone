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
package org.intelligentsia.keystone.kernel.core.artifact;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.intelligentsia.keystone.api.artifacts.ArtifactIdentifier;
import org.intelligentsia.keystone.api.artifacts.ArtifactsService;
import org.intelligentsia.keystone.api.artifacts.DefaultArtifactsService;
import org.intelligentsia.keystone.api.artifacts.KeystoneRuntimeException;
import org.intelligentsia.keystone.api.artifacts.Resource;
import org.intelligentsia.keystone.kernel.ArtifactContext;
import org.intelligentsia.keystone.kernel.ArtifactEntryPoint;
import org.intelligentsia.keystone.kernel.ArtifactEntryPointLocalizer;
import org.intelligentsia.keystone.kernel.ArtifactServer;
import org.intelligentsia.keystone.kernel.IsolationLevel;
import org.intelligentsia.keystone.kernel.core.AbstractKernelServer;
import org.intelligentsia.keystone.kernel.event.ArtifactContextChangeEvent;
import org.intelligentsia.utilities.Preconditions;
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
	 * Set of {@link ArtifactEntryPointLocalizer} which implement a chain of
	 * responsability.
	 */
	private final Set<ArtifactEntryPointLocalizer> artifactEntryPointLocalizers = new LinkedHashSet<ArtifactEntryPointLocalizer>();

	/**
	 * Build a new instance of DefaultArtifactServer.java.
	 * 
	 * @param artifactsService
	 *            artifacts Service instance
	 * @throws NullPointerException
	 *             if artifactsService is null
	 */
	public DefaultArtifactServer() {
		super("artifact-server");
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
		this.artifactsService = new DefaultArtifactsService(Preconditions.checkNotNull(kernel.getRepositoryServer(),
				"Repository Server cannot be null"));
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
		return artifacts.containsKey(Preconditions.checkNotNull(artifactIdentifier, "artifactIdentifier"));
	}

	/**
	 * @see org.intelligentsia.keystone.kernel.ArtifactServer#find(org.intelligentsia
	 *      .keystone.api.artifacts.ArtifactIdentifier)
	 */
	@Override
	public ArtifactContext find(final ArtifactIdentifier artifactIdentifier) throws KeystoneRuntimeException,
			NullPointerException {
		return artifacts.get(Preconditions.checkNotNull(artifactIdentifier, "artifactIdentifier"));
	}

	@Override
	public Iterator<ArtifactIdentifier> iterator() {
		return artifacts.keySet().iterator();
	}

	/**
	 * @see org.intelligentsia.keystone.kernel.ArtifactServer#load(org.intelligentsia
	 *      .keystone.api.artifacts.ArtifactIdentifier, org.intelligentsia.keystone.kernel.IsolationLevel)
	 */
	@Override
	public ArtifactContext load(final ArtifactIdentifier artifactIdentifier, final IsolationLevel isolationLevel)
			throws KeystoneRuntimeException, NullPointerException {
		if (isDestroying()) {
			throw new KeystoneRuntimeException("Cannot load artifact when destroying server");
		}
		final DefaultArtifactContext result = new DefaultArtifactContext(Preconditions.checkNotNull(artifactIdentifier,
				"artifactIdentifier"));
		Preconditions.checkNotNull(isolationLevel, "isolationLevel");
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
		kernel.getEventBus().publish(
				new ArtifactContextChangeEvent(artifactIdentifier, ArtifactContextChangeEvent.State.INITIALIZED));
		return result;
	}

	/**
	 * @see org.intelligentsia.keystone.kernel.ArtifactServer#unload(org.intelligentsia
	 *      .keystone.api.artifacts.ArtifactIdentifier)
	 */
	@Override
	public void unload(final ArtifactIdentifier artifactIdentifier) throws KeystoneRuntimeException,
			NullPointerException {
		ArtifactContext artifactContext = artifacts.remove(Preconditions.checkNotNull(artifactIdentifier,
				"artifactIdentifier"));
		if (artifactContext != null) {
			// remove loader from composite
			if (artifactContext.getIsolationLevel().equals(IsolationLevel.NONE)) {
				compositeProxyClassLoader.remove(((JarClassLoader) artifactContext.getClassLoader()).getLocalLoader());
			}
			artifactContext = null;
			// launch garbage collector
			kernel.getKernelExecutor().execute(new Runnable() {

				@Override
				public void run() {
					// Get the Java runtime
					Runtime runtime = Runtime.getRuntime();
					// Run the garbage collector
					runtime.gc();
				}
			});
			if (!isDestroying()) {
				// now we could change behavior with life cycle and not raising event
				kernel.getEventBus().publish(
						new ArtifactContextChangeEvent(artifactIdentifier, ArtifactContextChangeEvent.State.DESTROYED));
			}
		}
	}

	@Override
	public boolean addArtifactEntryPointLocalizer(final ArtifactEntryPointLocalizer artifactEntryPointLocalizer)
			throws NullPointerException {
		Preconditions.checkNotNull(artifactEntryPointLocalizer, "artifactEntryPointLocalizer");
		return artifactEntryPointLocalizers.add(artifactEntryPointLocalizer);
	}

	@Override
	public boolean removeArtifactEntryPointLocalizer(final ArtifactEntryPointLocalizer artifactEntryPointLocalizer)
			throws NullPointerException {
		Preconditions.checkNotNull(artifactEntryPointLocalizer, "artifactEntryPointLocalizer");
		return artifactEntryPointLocalizers.remove(artifactEntryPointLocalizer);
	}

	/**
	 * Handle {@link ArtifactContextChangeEvent} event.
	 * 
	 * @param artifactContextChangeEvent
	 */
	public void onArtifactContextChange(final ArtifactContextChangeEvent artifactContextChangeEvent) {
		// not destroying state
		if (!isDestroying() && (artifactContextChangeEvent != null)) {
			// Initialization ?
			if (ArtifactContextChangeEvent.State.INITIALIZED.equals(artifactContextChangeEvent.getState())) {
				final ArtifactContext artifactContext = find(artifactContextChangeEvent.getArtifactIdentifier());
				final ArtifactEntryPoint artifactEntryPoint = localize(artifactContext);
				if (artifactEntryPoint != null) {
					kernel.dmesg("find entry point for %s", artifactContextChangeEvent.getArtifactIdentifier());
					// execute
					kernel.getKernelExecutor().execute(new Runnable() {
						@Override
						public void run() {
							artifactEntryPoint.onLoad(getKernelContext());
						}
					});
				}
			}
		}
	}

	/**
	 * Localize {@link ArtifactEntryPoint} for specified {@link ArtifactContext} .
	 * 
	 * @param artifactContext
	 * @return an {@link ArtifactEntryPoint} instance or null if none is found.
	 */
	private ArtifactEntryPoint localize(final ArtifactContext artifactContext) {
		ArtifactEntryPoint artifactEntryPoint = null;
		if (artifactContext != null) {
			final Iterator<ArtifactEntryPointLocalizer> iterator = artifactEntryPointLocalizers.iterator();
			while ((artifactEntryPoint == null) && iterator.hasNext()) {
				artifactEntryPoint = iterator.next().localize(artifactContext);
			}
		}
		return artifactEntryPoint;
	}

	/**
	 * Build a new instance of JarClassLoader with only local loader activated.
	 * <p>
	 * All instance have a common parent {@link JarClassLoader} instance. If {@link IsolationLevel#NONE} then newly
	 * classloader is added by composition to parent.
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

	/**
	 * @return class loader instance used as parent.
	 * @uml.property name="parent"
	 */
	public JarClassLoader getParent() {
		return parent;
	}
}
