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
package org.intelligentsia.keystone.kernel.jcl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.xeustechnologies.jcl.ProxyClassLoader;

/**
 * CompositeProxyClassLoader implement a composite of delegate class loader.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class CompositeProxyClassLoader extends ProxyClassLoader {
	/**
	 * @uml.property name="proxyClassLoaders"
	 */
	private final List<ProxyClassLoader> proxyClassLoaders = new ArrayList<ProxyClassLoader>();

	/**
	 * Build a new instance of CompositeProxyClassLoader.java.
	 */
	public CompositeProxyClassLoader() {
		super();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class loadClass(final String className, final boolean resolveIt) {
		Class result = null;
		final Iterator<ProxyClassLoader> iterator = proxyClassLoaders.iterator();
		while ((result == null) && iterator.hasNext()) {
			result = iterator.next().loadClass(className, resolveIt);
		}
		return result;
	}

	@Override
	public InputStream loadResource(final String name) {
		InputStream result = null;
		final Iterator<ProxyClassLoader> iterator = proxyClassLoaders.iterator();
		while ((result == null) && iterator.hasNext()) {
			result = iterator.next().loadResource(name);
		}
		return result;
	}

	/**
	 * @return
	 * @see java.util.List#isEmpty()
	 */
	public boolean isEmpty() {
		return proxyClassLoaders.isEmpty();
	}

	public boolean contains(final Object o) {
		return proxyClassLoaders.contains(o);
	}

	public boolean add(final ProxyClassLoader e) {
		return proxyClassLoaders.add(e);
	}

	public boolean remove(final ProxyClassLoader o) {
		return proxyClassLoaders.remove(o);
	}

	public boolean addAll(final Collection<? extends ProxyClassLoader> c) {
		return proxyClassLoaders.addAll(c);
	}

	/**
	 * @return
	 * @uml.property name="proxyClassLoaders"
	 */
	public List<ProxyClassLoader> getProxyClassLoaders() {
		return proxyClassLoaders;
	}
}
