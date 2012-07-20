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

import org.intelligentsia.keystone.kernel.Kernel;

/**
 * KernelHolder is an utility class to build Kernel Process.
 * 
 * <pre>
 * final KernelHolder holder = new KernelHolder();
 * holder.setKernel(new KernelBuilder().build(new Runnable() {
 * 	@Override 
 * 	public void run() {
 * 		// do something with kernel
 *         holder.getKernel().getEventBus() 
 *     }
 * }));
 * Launchers.launchAndWait(holder.getKernel());
 * </pre>
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public final class KernelHolder {
	/** internal {@link Kernel} instance. */
	private Kernel kernel;

	/**
	 * Build a new instance of KernelHolder.java.
	 */
	public KernelHolder() {
		super();
	}

	/**
	 * @return the kernel
	 */
	public final Kernel getKernel() {
		return kernel;
	}

	/**
	 * @param kernel
	 *            the kernel to set
	 */
	public final void setKernel(final Kernel kernel) {
		this.kernel = kernel;
	}
}