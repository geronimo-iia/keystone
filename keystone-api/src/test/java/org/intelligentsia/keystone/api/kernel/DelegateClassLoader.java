package org.intelligentsia.keystone.api.kernel;

import java.io.InputStream;

import org.xeustechnologies.jcl.AbstractClassLoader;
import org.xeustechnologies.jcl.ProxyClassLoader;

/**
 * 
 * DelegateClassLoader implements a ProxyClassLoader which delegate loading to a
 * specific AbstractClassLoader loader instance..
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class DelegateClassLoader extends ProxyClassLoader {

	private final AbstractClassLoader delegate;

	/**
	 * Build a new instance of DelegateClassLoader.java.
	 * 
	 * @param delegate
	 *            instance of AbstractClassLoader where to delegate
	 * @throws NullPointerException
	 *             if delegate is null
	 */
	public DelegateClassLoader(AbstractClassLoader delegate) throws NullPointerException {
		super();
		if (delegate == null)
			throw new NullPointerException("delegate can't be null");
		this.delegate = delegate;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class loadClass(String className, boolean resolveIt) {
		Class result;
		try {
			result = delegate.loadClass(className, resolveIt);
		} catch (ClassNotFoundException e) {
			return null;
		}
		return result;
	}

	@Override
	public InputStream loadResource(String name) {
		return delegate.getResourceAsStream(name);
	}

	public AbstractClassLoader getDelegate() {
		return delegate;
	}
}
