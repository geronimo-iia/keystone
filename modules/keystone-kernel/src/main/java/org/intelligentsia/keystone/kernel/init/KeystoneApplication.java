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
package org.intelligentsia.keystone.kernel.init;

import java.util.concurrent.TimeUnit;

import org.intelligentsia.keystone.api.artifacts.ArtifactIdentifier;
import org.intelligentsia.keystone.kernel.IsolationLevel;

/**
 * KeystoneApplication.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class KeystoneApplication implements Runnable {

	/**
	 * Build a new instance of KeystoneApplication.java.
	 */
	public KeystoneApplication() {
		super();
	}

	public static void main(String[] args) {
		new KeystoneApplication().run();
	}

	@Override
	public void run() {

		// load KernelConfiguration
		final KernelConfiguration configuration = loadKernelConfiguration();
		// build kernel Instance
		KernelBuilder builder = null;
		try {
			builder = new KernelBuilder().setKernelConfiguration(configuration);
		} catch (Throwable e) {
			throw new IllegalArgumentException(e);
		}

		// holder config
		final KernelHolder holder = new KernelHolder();
		holder.setKernel(builder.build(new Runnable() {

			@Override
			public void run() {
				// add artifact
				if (configuration.getArtifactIdentifier() != null) {
					ArtifactIdentifier artifactIdentifier = ArtifactIdentifier.parse(configuration.getArtifactIdentifier());
					holder.getKernel().getArtifactServer().load(artifactIdentifier, IsolationLevel.NONE);
					holder.getKernel().dmesg("loaded artifact '%s'", artifactIdentifier);
				}
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
				}
			}
		}));

		// launch
		Launchers.launchUntil(holder.getKernel(), Predicates.awaitTerminaison(1, TimeUnit.SECONDS));
	}

	/**
	 * Load {@link KernelConfiguration}
	 * 
	 * @return a {@link KernelConfiguration}.
	 */
	protected KernelConfiguration loadKernelConfiguration() {
		// final ObjectMapper mapper = new XmlMapper();
		return new KernelConfiguration();
	}

}
