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

import org.xeustechnologies.jcl.AbstractClassLoader;
import org.xeustechnologies.jcl.ProxyClassLoader;

/**
 * DelegateProxyClassLoader implements a ProxyClassLoader which delegate loading
 * to a specific AbstractClassLoader loader instance..
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class DelegateProxyClassLoader extends ProxyClassLoader {

	/**
	 * @uml.property name="delegate"
	 */
	private final AbstractClassLoader delegate;

	/**
	 * Build a new instance of DelegateProxyClassLoader.java.
	 * 
	 * @param delegate
	 *            instance of AbstractClassLoader where to delegate
	 * @throws NullPointerException
	 *             if delegate is null
	 */
	public DelegateProxyClassLoader(final AbstractClassLoader delegate) throws NullPointerException {
		super();
		if (delegate == null) {
			throw new NullPointerException("delegate can't be null");
		}
		this.delegate = delegate;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class loadClass(final String className, final boolean resolveIt) {
		Class result;
		try {
			result = delegate.loadClass(className, resolveIt);
		} catch (final ClassNotFoundException e) {
			return null;
		}
		return result;
	}

	@Override
	public InputStream loadResource(final String name) {
		return delegate.getResourceAsStream(name);
	}

	/**
	 * @return
	 * @uml.property name="delegate"
	 */
	public AbstractClassLoader getDelegate() {
		return delegate;
	}
}
