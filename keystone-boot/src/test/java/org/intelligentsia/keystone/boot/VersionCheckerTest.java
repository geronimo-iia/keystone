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

import junit.framework.Assert;

import org.junit.Test;


/**
 * VersionCheckerTest define test case for VersionChecker. 
 *
 *@author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class VersionCheckerTest {


	@Test
	public void checkVersionPropertyNotNull() {
		Assert.assertNotNull(VersionChecker.getCurrentJavaVirtualMachineSpecificationVersion());
	}


	@Test
	public void checkCompatibility() {
		SimpleVersion current = SimpleVersion.parse(VersionChecker.getCurrentJavaVirtualMachineSpecificationVersion());
		
		SimpleVersion upper = new SimpleVersion(current.getMajor(), current.getMedium() + 1);
		Assert.assertFalse(VersionChecker.isCompatible(upper.toString()));
		upper = new SimpleVersion(current.getMajor() + 1 , current.getMedium() );
		Assert.assertFalse(VersionChecker.isCompatible(upper.toString()));
		
		// equals version
		Assert.assertTrue(VersionChecker.isCompatible(VersionChecker.getCurrentJavaVirtualMachineSpecificationVersion()));
		Assert.assertTrue(VersionChecker.isCompatible(current.toString()));
	}
	
	
	@Test
	public void checkInCompatibilityWhenLower() {
		SimpleVersion current = SimpleVersion.parse(VersionChecker.getCurrentJavaVirtualMachineSpecificationVersion());
		SimpleVersion lower = null;
		if (current.getMedium() == 0) {
			lower =  new SimpleVersion(current.getMajor() - 1, current.getMedium() );
		} else {
			lower =  new SimpleVersion(current.getMajor(), current.getMedium() - 1);
		}
		
		Assert.assertTrue(VersionChecker.isCompatible(lower.toString()));
		Assert.assertFalse(VersionChecker.isCompatible("0.9"));
	}
}
