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
/**
 * 
 */
package org.intelligentsia.keystone.kernel.impl;

import org.intelligentsia.keystone.kernel.EventBusServer;

import com.adamtaft.eventbus.BasicEventBus;
import com.adamtaft.eventbus.EventBus;

/**
 * DefaultEventBusServer implements {@link EventBusServer} by delegating to an
 * {@link EventBus} instance.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class DefaultEventBusServer implements EventBusServer {

	/**
	 * Internal underlying implementation of event bus.
	 */
	private EventBus eventBus;

	/**
	 * 
	 */
	public DefaultEventBusServer() {
		super();
		this.eventBus = new BasicEventBus();
	}

	/**
	 * @see org.intelligentsia.keystone.kernel.EventBusServer#subscribe(java.lang.Object)
	 */
	@Override
	public void subscribe(Object subscriber) {
		eventBus.subscribe(subscriber);

	}

	/**
	 * @see org.intelligentsia.keystone.kernel.EventBusServer#unsubscribe(java.lang.Object)
	 */
	@Override
	public void unsubscribe(Object subscriber) {
		eventBus.unsubscribe(subscriber);
	}

	/**
	 * @see org.intelligentsia.keystone.kernel.EventBusServer#publish(java.lang.Object)
	 */
	@Override
	public void publish(Object event) {
		eventBus.publish(event);
	}

}
