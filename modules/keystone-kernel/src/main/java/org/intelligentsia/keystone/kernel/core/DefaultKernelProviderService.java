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
package org.intelligentsia.keystone.kernel.core;

import org.intelligentsia.keystone.api.Preconditions;
import org.intelligentsia.keystone.api.artifacts.KeystoneRuntimeException;
import org.intelligentsia.keystone.kernel.ArtifactContext;
import org.intelligentsia.keystone.kernel.IsolationLevel;
import org.intelligentsia.keystone.kernel.Kernel;
import org.intelligentsia.keystone.kernel.KernelContext;
import org.intelligentsia.keystone.kernel.KernelProviderService;

/**
 * DefaultKernelProviderService implements {@link KernelProviderService} that
 * check only if {@link ArtifactContext} is not isloated from thie
 * {@link Kernel} instance.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class DefaultKernelProviderService implements KernelProviderService {

	private final Kernel kernel;

	/**
	 * Build a new instance of BaseKernel.java.
	 * 
	 * @param kernel
	 *            {@link Kernel} instance
	 * @throws NullPointerException
	 *             if kernel is null
	 */
	public DefaultKernelProviderService(final Kernel kernel) throws NullPointerException {
		super();
		this.kernel = Preconditions.checkNotNull(kernel, "kernel");
	}

	@Override
	public Kernel getKernel(final ArtifactContext artifactContext) throws KeystoneRuntimeException {
		// just check Isolation Level
		if (!IsolationLevel.NONE.equals(artifactContext.getIsolationLevel())) {
			throw new KeystoneRuntimeException("Not allowed");
		}
		return kernel;
	}

	@Override
	public String getName() {
		return "kernel-provider-service";
	}

	@Override
	public void initialize(final KernelContext context) {
		// nothing to do
	}

	@Override
	public void destroy() {
		// nothing to do
	}

}