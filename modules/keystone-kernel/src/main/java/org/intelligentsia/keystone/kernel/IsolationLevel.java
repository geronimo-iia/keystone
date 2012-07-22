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
package org.intelligentsia.keystone.kernel;

/**
 * IsolationLevel enumeration define supported isolation level :
 * <ul>
 * <li>NONE: This artifact will be loaded in Kernel classloader</li>
 * <li>ISOLATED: This artifact will be loaded in a dedicated class loader.
 * Kernel classloader will be his parent.</li>
 * </ul>
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public enum IsolationLevel {
	/**
	 * @uml.property name="nONE"
	 * @uml.associationEnd
	 */
	NONE,
	/**
	 * @uml.property name="iSOLATED"
	 * @uml.associationEnd
	 */
	ISOLATED;
}