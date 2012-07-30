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

import org.intelligentsia.keystone.api.artifacts.KeystoneRuntimeException;

/**
 * 
 * ArtifactEntryPoint. Implement this interface to allow a class to act as a
 * artifact entry point.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public interface ArtifactEntryPoint {

	/**
	 * The entry point method, called automatically by loading an artifact that
	 * declares an implementing class as an entry point.
	 * 
	 * @param artifactContext
	 *            {@link ArtifactContext} instance
	 * @param kernelContext
	 *            {@link KernelContext} instance
	 * @throws KeystoneRuntimeException
	 *             iof an error occurs
	 */
	public void onLoad(ArtifactContext artifactContext, KernelContext kernelContext) throws KeystoneRuntimeException;
}
