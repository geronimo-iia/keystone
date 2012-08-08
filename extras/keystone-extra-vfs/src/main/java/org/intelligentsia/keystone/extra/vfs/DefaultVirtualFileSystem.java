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

import org.intelligentsia.keystone.api.Preconditions;
import org.intelligentsia.keystone.api.StringUtils;
import org.intelligentsia.keystone.kernel.ArtifactContext;
import org.intelligentsia.keystone.kernel.Kernel;
import org.intelligentsia.keystone.kernel.KernelContext;

/**
 * DefaultVirtualFileSystem implements {@link VirtualFileSystem}.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class DefaultVirtualFileSystem implements VirtualFileSystem {

	private final ArtifactContext artifactContext;

	/**
	 * Build a new instance of DefaultVirtualFileSystem.java.
	 */
	public DefaultVirtualFileSystem(ArtifactContext artifactContext, Kernel kernel) throws NullPointerException {
		super();
		this.artifactContext = Preconditions.checkNotNull(artifactContext, "artifactContext");
	}

	/**
	 * @see org.intelligentsia.keystone.kernel.Service#getName()
	 */
	@Override
	public String getName() {
		return StringUtils.format("virtual-file-system-service (%s)", artifactContext.getArtifactIdentifier());
	}

	/**
	 * @see org.intelligentsia.keystone.kernel.Service#initialize(org.intelligentsia.keystone.kernel.KernelContext)
	 */
	@Override
	public void initialize(KernelContext context) {
	}

	/**
	 * @see org.intelligentsia.keystone.kernel.Service#destroy()
	 */
	@Override
	public void destroy() {
	}

}
