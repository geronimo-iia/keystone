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
 * {@link KernelServer} declare methods to manage internal kernel 'server'.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface KernelServer {
	/**
	 * 
	 * KernelServer State declaration.
	 * 
	 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
	 * 
	 */
	public enum State {
		/**
		 * Just created instance.
		 */
		CREATED,
		/**
		 * Initialization processing.
		 */
		INITIALIZING,
		/**
		 * Ready to serve.
		 */
		READY,
		/**
		 * Destroy processing.
		 */
		DESTROYING,
		/**
		 * End of life.
		 */
		EOL
	}

	/**
	 * @return author information.
	 */
	public String getAuthor();

	/**
	 * @return description information.
	 */
	public String getDescription();

	/**
	 * Initialize kernel server instance.
	 * 
	 * @param kernel
	 *            internal kernel instance.
	 * @throws KeystoneRuntimeException
	 *             if error occurs
	 */
	public void initialize(Kernel kernel) throws KeystoneRuntimeException;

	/**
	 * Detroy kernel server instance (all resources should be closed).
	 */
	public void destroy();

	/**
	 * @return current State.
	 */
	public State getState();
}
