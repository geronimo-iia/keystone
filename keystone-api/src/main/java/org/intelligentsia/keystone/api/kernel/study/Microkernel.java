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
package org.intelligentsia.keystone.api.kernel.study;

import java.util.HashMap;
import java.util.Map;

/**
 * Microkernel. “Perfection is not achieved when there is nothing left to add,
 * but when there is nothing left to take away” --Antoine de St. Exupery
 * <p>
 * Responsibility<br />
 * Provides core mechanisms.<br />
 * Offers communication facilities.<br />
 * Encapsulates system dependencies.<br />
 * Manages and controls resources.
 * </p>
 * <p>
 * Collaborators<br>
 * Internal Server
 * </p>
 * 
 * @author Jerome Guibert
 */
public class Microkernel {

	private Map<String, InternalServer> internalServers = new HashMap<String, InternalServer>();

	public void initCommunication(Adapter adapter) {
		findReceiver();
	}

	private void findReceiver() {
		// TODO Auto-generated method stub

	}

	public void initReceiver() {

	}

	public void executeMechanisme() {

	}

	public void callInternalServer(String name) {
		InternalServer internalServer = internalServers.get(name);
		internalServer.receiveRequest();
		internalServer.executeService();
	}

	/**
	 * Using a typed call.
	 * 
	 * @param <T>
	 * @param object
	 * @return
	 */
	public <T extends InternalServer> T call(Object object) {
		return null;
	}
}
