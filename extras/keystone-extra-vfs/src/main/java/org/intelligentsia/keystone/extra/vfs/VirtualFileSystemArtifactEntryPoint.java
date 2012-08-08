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
package org.intelligentsia.keystone.extra.vfs;

import org.intelligentsia.keystone.api.artifacts.KeystoneRuntimeException;
import org.intelligentsia.keystone.kernel.ArtifactContext;
import org.intelligentsia.keystone.kernel.ArtifactEntryPoint;
import org.intelligentsia.keystone.kernel.Kernel;
import org.intelligentsia.keystone.kernel.KernelContext;
import org.intelligentsia.keystone.kernel.KernelProviderService;
import org.intelligentsia.keystone.kernel.ServiceProvider;

/**
 * VirtualFileSystemArtifactEntryPoint register {@link VirtualFileSystem}
 * service.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class VirtualFileSystemArtifactEntryPoint implements ArtifactEntryPoint {

	@Override
	public void onLoad(final ArtifactContext artifactContext, final KernelContext kernelContext) throws KeystoneRuntimeException {
		// locate a ServiceProver for KernelProviderService.
		final ServiceProvider<KernelProviderService> provider = kernelContext.find(KernelProviderService.class);
		if (provider.isEmpty()) {
			throw new KeystoneRuntimeException("No KernelProviderService instance");
		}
		final KernelProviderService kernelProviderService = provider.getService();
		// obtain kernel instance
		final Kernel kernel = kernelProviderService.getKernel(artifactContext);
		// register service
		kernel.serviceServer().register(artifactContext, VirtualFileSystem.class, new DefaultVirtualFileSystem(artifactContext, kernel));
	}

}
