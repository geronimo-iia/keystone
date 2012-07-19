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

import org.intelligentsia.keystone.kernel.KernelContext;
import org.intelligentsia.keystone.kernel.event.ArtifactContextChangeEvent;
import org.intelligentsia.keystone.kernel.service.Service;

// may we move this in another jar or integrate this functionnality to a basic ArtifacServer with an ArtifactHandler 
public class ArtifactEntryPointService implements Service {

	// may we add Kernel instance
	public ArtifactEntryPointService() {
		super();
	}

	@Override
	public String getName() {
		return "ArtifactEntryPoint";
	}

	@Override
	public void initialize(KernelContext context) {
	}

	@Override
	public void destroy() {
	}

	public void onArtifactContextChange(ArtifactContextChangeEvent artifactContextChangeEvent) {

	}
}
