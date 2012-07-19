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
package org.intelligentsia.keystone.kernel.core.artifact;

import org.xeustechnologies.jcl.JarClassLoader;

/**
 * JarClassLoaderFactory expose methods to initialize {@link JarClassLoader}.
 * 
 * <p>
 * All JarClassLoader instance have (state, priority order lower first):
 * </p>
 * <ul>
 * <li>SystemLoader: enabled, 50</li>
 * <li>ThreadLoader: enabled, 40</li>
 * <li>ParentLoader: enabled, 30</li>
 * <li>CurrentLoader: enabled, 20</li>
 * <li>LocalLoader: enabled, 10</li>
 * <li>OsgiBootLoader: disabled, 0</li>
 * </ul>
 * 
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public enum JarClassLoaderFactory {
	;

	/**
	 * Initialize internal priority of proxy loader.
	 * 
	 * @return initialized classLoader
	 */
	public static JarClassLoader initialize() {
		return initialize(new JarClassLoader());
	}

	/**
	 * Initialize internal priority of proxy loader.
	 * 
	 * @param classLoader
	 *            classLoader to initialize
	 * @return initialized classLoader
	 */
	public static JarClassLoader initialize(final JarClassLoader classLoader) {
		classLoader.getSystemLoader().setOrder(50);
		classLoader.getThreadLoader().setOrder(40);
		classLoader.getParentLoader().setOrder(30);
		classLoader.getCurrentLoader().setOrder(20);
		classLoader.getLocalLoader().setOrder(10);
		classLoader.getOsgiBootLoader().setOrder(0);
		classLoader.getOsgiBootLoader().setEnabled(false);
		return classLoader;
	}

}
