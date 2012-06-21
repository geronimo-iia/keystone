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

import org.xeustechnologies.jcl.JarClassLoader;
import org.xeustechnologies.jcl.JclObjectFactory;
import org.xeustechnologies.jcl.context.DefaultContextLoader;
import org.xeustechnologies.jcl.context.JclContext;

/**
 * 
 * JclStudy.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class JclStudy {

	/**
	 * @param args
	 */
	public static void main(final String[] args) {

		final JarClassLoader jcl = new JarClassLoader();
		// Loading classes from different sources
		jcl.add("target/classes/");

		jcl.getSystemLoader().setOrder(1); // Look in system class loader first
		jcl.getLocalLoader().setOrder(2); // if not found look in local class
											// loader
		jcl.getParentLoader().setOrder(3); // if not found look in parent class
											// loader
		jcl.getThreadLoader().setOrder(4); // if not found look in thread
											// context class loader
		jcl.getCurrentLoader().setOrder(5); // if not found look in current
											// class loader

		// Create default factory
		final JclObjectFactory factory = JclObjectFactory.getInstance();

		// Create object of loaded class
		final Object obj = factory.create(jcl, "org.intelligentsia.keystone.api.artifacts.pom.Pom");
		System.err.println(obj);

		final DefaultContextLoader context = new DefaultContextLoader(jcl);
		context.loadContext();

		// Now “jcl” can be accessed from anywhere in the application as
		// follows.
		final JarClassLoader jarClassLoader = JclContext.get(); // returns the
																// Default
		// JCL instance
		if (jarClassLoader == null) {
			throw new IllegalStateException("No jcl with Default JCL instance");
		}

		// unload default context
		context.unloadContext();
	}

}
