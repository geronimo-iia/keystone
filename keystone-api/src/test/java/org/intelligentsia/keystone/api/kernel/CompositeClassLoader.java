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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.xeustechnologies.jcl.ProxyClassLoader;

/**
 * 
 * CompositeClassLoader implement a composite of delegate class loader.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class CompositeClassLoader extends ProxyClassLoader {
	private final List<DelegateClassLoader> delegateClassLoaders = new ArrayList<DelegateClassLoader>();

	/**
	 * Build a new instance of CompositeClassLoader.java.
	 */
	public CompositeClassLoader() {
		super();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class loadClass(String className, boolean resolveIt) {
		Class result = null;
		Iterator<DelegateClassLoader> iterator = delegateClassLoaders.iterator();
		while (result == null && iterator.hasNext()) {
			result = iterator.next().loadClass(className, resolveIt);
		}
		return result;
	}

	@Override
	public InputStream loadResource(String name) {
		InputStream result = null;
		Iterator<DelegateClassLoader> iterator = delegateClassLoaders.iterator();
		while (result == null && iterator.hasNext()) {
			result = iterator.next().loadResource(name);
		}
		return result;
	}

	/**
	 * @return
	 * @see java.util.List#isEmpty()
	 */
	public boolean isEmpty() {
		return delegateClassLoaders.isEmpty();
	}

	/**
	 * @param o
	 * @return
	 * @see java.util.List#contains(java.lang.Object)
	 */
	public boolean contains(Object o) {
		return delegateClassLoaders.contains(o);
	}

	/**
	 * @param e
	 * @return
	 * @see java.util.List#add(java.lang.Object)
	 */
	public boolean add(DelegateClassLoader e) {
		return delegateClassLoaders.add(e);
	}

	/**
	 * @param o
	 * @return
	 * @see java.util.List#remove(java.lang.Object)
	 */
	public boolean remove(Object o) {
		return delegateClassLoaders.remove(o);
	}

	/**
	 * @param c
	 * @return
	 * @see java.util.List#addAll(java.util.Collection)
	 */
	public boolean addAll(Collection<? extends DelegateClassLoader> c) {
		return delegateClassLoaders.addAll(c);
	}

	public List<DelegateClassLoader> getDelegateClassLoaders() {
		return delegateClassLoaders;
	}
}
