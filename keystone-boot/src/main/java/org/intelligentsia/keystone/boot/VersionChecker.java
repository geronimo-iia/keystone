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

/**
 * 
 * VersionChecker is an utility to check if JVM currently used his compatible
 * with specified requirements.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public final class VersionChecker {

	/**
	 * @param minimalJvmVersion
	 *            minimal JVM version required
	 * @return true if version of the java virtual machine currently used is
	 *         compatible with the requirement
	 */
	public static boolean isCompatible(final String minimalJvmVersion) {
		SimpleVersion current = null;
		SimpleVersion minimal = null;
		try {
			current = SimpleVersion.parse(getCurrentJavaVirtualMachineSpecificationVersion());
		} catch (NumberFormatException e) {
			Console.WARNING("Unable to parse JVM Version ('" + getCurrentJavaVirtualMachineSpecificationVersion() + "')");
			Console.WARNING("Continue without check your requirement");
			return Boolean.TRUE;
		}
		try {
			minimal = SimpleVersion.parse(minimalJvmVersion);
		} catch (NumberFormatException e) {
			Console.WARNING("Minimal JVM Version parameter is not valid ('" + minimalJvmVersion + "')");
			Console.WARNING("Assuming that current JVM follows your requirement");
			return Boolean.TRUE;
		}
		return current.isBackwardCompatible(minimal);
	}

	/**
	 * @return current Java Virtual Machine Specification Version.
	 */
	public static String getCurrentJavaVirtualMachineSpecificationVersion() {
		return System.getProperty("java.vm.specification.version", "1.8");
	}

}
