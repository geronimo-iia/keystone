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

import org.intelligentsia.keystone.api.artifacts.KeystoneRuntimeException;
import org.intelligentsia.keystone.kernel.Kernel;

/**
 * Launchers define many kind of launcher. Called y a "main" method this class
 * expose some facility.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public enum Launchers {
	;

	/**
	 * Pause main thread until any thread interrupt our thread.
	 * 
	 * @param kernel
	 *            kernel to launch
	 */
	public static Launcher launchAndWait(final Kernel kernel) {
		return launchUntil(kernel, new Predicate() {
			@Override
			public boolean evaluate(final Kernel kernel) {
				return false;
			}
		});
	}

	/**
	 * Pause main thread until predicate evaluation return <code>true</code>.
	 * 
	 * @param kernel
	 *            kernel to launch
	 * @param predicate
	 *            terminaison predicate
	 */
	public static Launcher launchUntil(final Kernel kernel, final Predicate predicate) {
		return launchUntil(kernel, predicate, 1000);
	}

	/**
	 * Pause main thread until predicate evaluation return <code>true</code>.
	 * 
	 * @param kernel
	 *            kernel to launch
	 * @param predicate
	 *            terminaison predicate
	 * @param millis
	 *            time to sleep between evaluation in millisecondes
	 */
	public static Launcher launchUntil(final Kernel kernel, final Predicate predicate, final long millis) {
		return new Launcher() {
			@Override
			public void launch(final Kernel kernel) throws KeystoneRuntimeException {
				try {
					do {
						Thread.sleep(millis);
					} while (!predicate.evaluate(kernel));
				} catch (final InterruptedException e) {
				}
			}
		};

	}
}
