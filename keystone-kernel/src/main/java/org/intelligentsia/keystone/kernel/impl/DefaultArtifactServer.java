/**
 * 
 */
package org.intelligentsia.keystone.kernel.impl;

import java.net.MalformedURLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.intelligentsia.keystone.api.artifacts.ArtifactIdentifier;
import org.intelligentsia.keystone.api.artifacts.ArtifactsService;
import org.intelligentsia.keystone.api.artifacts.KeystoneRuntimeException;
import org.intelligentsia.keystone.api.artifacts.Resource;
import org.intelligentsia.keystone.kernel.ArtifactContext;
import org.intelligentsia.keystone.kernel.ArtifactServer;
import org.intelligentsia.keystone.kernel.IsolationLevel;
import org.xeustechnologies.jcl.CompositeProxyClassLoader;
import org.xeustechnologies.jcl.DelegateProxyClassLoader;
import org.xeustechnologies.jcl.JarClassLoader;

/**
 * @author geronimo
 *
 */
public class DefaultArtifactServer implements ArtifactServer {

	/**
	 * ArtifactsService instance.
	 * 
	 * @uml.property name="artifactsService"
	 * @uml.associationEnd
	 */
	private final ArtifactsService artifactsService;
	/**
	 * Parent JarClassLoader instance.
	 * 
	 * @uml.property name="parent"
	 */
	private final JarClassLoader parent;
	/**
	 * CompositeProxyClassLoader instance.
	 * 
	 * @uml.property name="compositeProxyClassLoader"
	 * @uml.associationEnd
	 */
	private final CompositeProxyClassLoader compositeProxyClassLoader;

	

	/**
	 * Map of ArtifactIdentifier and ArtifactContext.
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
	public DefaultArtifactServer(final ArtifactsService artifactsService) throws NullPointerException {
		super();
		if (artifactsService == null) {
			throw new NullPointerException("artifactsService is null");
		}
		this.artifactsService = artifactsService;
		this.parent = JarClassLoaderFactory.initialize();
		compositeProxyClassLoader = new CompositeProxyClassLoader();
		compositeProxyClassLoader.setOrder(15);
		parent.addLoader(compositeProxyClassLoader);
	}


	/* (non-Javadoc)
	 * @see org.intelligentsia.keystone.kernel.ArtifactServer#contains(org.intelligentsia.keystone.api.artifacts.ArtifactIdentifier)
	 */
	@Override
	public boolean contains(ArtifactIdentifier artifactIdentifier)
			throws NullPointerException {
		// TODO Auto-generated method stub
		return artifacts.containsKey(artifactIdentifier);
	}

	/* (non-Javadoc)
	 * @see org.intelligentsia.keystone.kernel.ArtifactServer#find(org.intelligentsia.keystone.api.artifacts.ArtifactIdentifier)
	 */
	@Override
	public ArtifactContext find(ArtifactIdentifier artifactIdentifier)
			throws KeystoneRuntimeException, NullPointerException {
		// TODO Auto-generated method stub
		return artifacts.get(artifactIdentifier);
	}

	/* (non-Javadoc)
	 * @see org.intelligentsia.keystone.kernel.ArtifactServer#load(org.intelligentsia.keystone.api.artifacts.ArtifactIdentifier, org.intelligentsia.keystone.kernel.IsolationLevel)
	 */
	@Override
	public ArtifactContext load(ArtifactIdentifier artifactIdentifier,
			IsolationLevel isolationLevel) throws KeystoneRuntimeException,
			NullPointerException {
		// TODO check if not ever loaded
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
		// TODO add event
		return result;
	}

	/* (non-Javadoc)
	 * @see org.intelligentsia.keystone.kernel.ArtifactServer#unload(org.intelligentsia.keystone.api.artifacts.ArtifactIdentifier)
	 */
	@Override
	public void unload(ArtifactIdentifier artifactIdentifier)
			throws KeystoneRuntimeException, NullPointerException {
		// TODO Auto-generated method stub

	}

	

	/**
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


	@Override
	public Iterator<ArtifactIdentifier> iterator() {
		// TODO Auto-generated method stub
		return artifacts.keySet().iterator();
	}
	
	
}
