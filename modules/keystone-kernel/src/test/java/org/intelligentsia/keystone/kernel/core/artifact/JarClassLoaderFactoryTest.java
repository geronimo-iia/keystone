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

import static junit.framework.Assert.*;

import org.junit.Test;
import org.xeustechnologies.jcl.JarClassLoader;

/**
 * 
 * JarClassLoaderFactoryTest.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class JarClassLoaderFactoryTest {

	@Test
	public void checkOrder() {
		JarClassLoader classLoader = JarClassLoaderFactory.initialize();

		assertEquals(50, classLoader.getSystemLoader().getOrder());
		assertEquals(40, classLoader.getThreadLoader().getOrder());
		assertEquals(30, classLoader.getParentLoader().getOrder());
		assertEquals(20, classLoader.getCurrentLoader().getOrder());
		assertEquals(10, classLoader.getLocalLoader().getOrder());
		assertEquals(0, classLoader.getOsgiBootLoader().getOrder());
		assertTrue(!classLoader.getOsgiBootLoader().isEnabled());
	}
}
