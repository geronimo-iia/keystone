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
package org.intelligentsia.keystone.boot;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import junit.framework.Assert;
import org.junit.Test;

/**
 * 
 * SimpleVersionTest implements test case for SimpleVersion.
 * 
 * @author geronimo
 * 
 */
public class SimpleVersionTest {

	@Test
	public void checkParseVersion() {
		assertEquals(new SimpleVersion(1, null), SimpleVersion.parse("1"));
		assertEquals(new SimpleVersion(1, null), SimpleVersion.parse("1-test"));
		assertEquals(new SimpleVersion(1, 2), SimpleVersion.parse("1.2"));
		assertEquals(new SimpleVersion(1, 2), SimpleVersion.parse("1.2-test"));
	}

	@Test
	public void checkIsBackwardCompatible() {
		SimpleVersion version = new SimpleVersion(1, 1);
		assertTrue(version.isBackwardCompatible(new SimpleVersion(1, null)));
		assertFalse(version.isBackwardCompatible(new SimpleVersion(0, 3)));
		assertFalse(version.isBackwardCompatible(new SimpleVersion(1, 3)));
		assertFalse(version.isBackwardCompatible(new SimpleVersion(1, 9)));
		assertFalse(version.isBackwardCompatible(new SimpleVersion(2,null)));

		version = new SimpleVersion(1, null);
		assertTrue(version.isBackwardCompatible(new SimpleVersion(1, null)));
		assertTrue(version.isBackwardCompatible(new SimpleVersion(1, 1)));
	}


	@Test
	public void checkVersionPropertyNotNull() {
		Assert.assertNotNull(SimpleVersion.getCurrentJavaVirtualMachineSpecificationVersion());
	}


	@Test
	public void checkCompatibility() {
		SimpleVersion current = SimpleVersion.parse(SimpleVersion.getCurrentJavaVirtualMachineSpecificationVersion());

		SimpleVersion upper = new SimpleVersion(current.getMajor(), current.getMedium() + 1);
		Assert.assertFalse(SimpleVersion.isCompatible(upper.toString()));
		upper = new SimpleVersion(current.getMajor() + 1 , current.getMedium() );
		Assert.assertFalse(SimpleVersion.isCompatible(upper.toString()));

		// equals version
		Assert.assertTrue(SimpleVersion.isCompatible(SimpleVersion.getCurrentJavaVirtualMachineSpecificationVersion()));
		Assert.assertTrue(SimpleVersion.isCompatible(current.toString()));
	}


	@Test
	public void checkInCompatibilityWhenLower() {
		SimpleVersion current = SimpleVersion.parse(SimpleVersion.getCurrentJavaVirtualMachineSpecificationVersion());
		SimpleVersion lower = null;
		if (current.getMedium() == 0) {
			lower =  new SimpleVersion(current.getMajor() - 1, current.getMedium() );
		} else {
			lower =  new SimpleVersion(current.getMajor(), current.getMedium() - 1);
		}

		Assert.assertTrue(SimpleVersion.isCompatible(lower.toString()));
		Assert.assertFalse(SimpleVersion.isCompatible("0.9"));
	}
}
