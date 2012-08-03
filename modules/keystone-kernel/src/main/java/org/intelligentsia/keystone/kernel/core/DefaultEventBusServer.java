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
package org.intelligentsia.keystone.kernel.core;

import java.util.concurrent.TimeUnit;

import org.intelligentsia.keystone.kernel.EventBusServer;

import com.adamtaft.eventbus.BasicEventBus;
import com.adamtaft.eventbus.EventBus;

/**
 * DefaultEventBusServer implements {@link EventBusServer} by delegating to an
 * {@link EventBus} instance.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class DefaultEventBusServer extends AbstractKernelServer implements EventBusServer {

	/**
	 * Internal underlying implementation of event bus ({@link BasicEventBus}).
	 */
	private final BasicEventBus eventBus;

	/**
	 * Build a new instance of DefaultEventBusServer.java with a
	 * {@link BasicEventBus} underlaying instance.
	 */
	public DefaultEventBusServer() {
		super("event-bus-server");
		this.eventBus = new BasicEventBus();
	}

	@Override
	protected void onInitialize() {
		// nothing to do
	}

	/**
	 * Wait 10 second before ending.
	 * 
	 * @see org.intelligentsia.keystone.kernel.core.AbstractKernelServer#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		try {
			eventBus.shutdown(10, TimeUnit.SECONDS);
		} catch (final InterruptedException e) {
			if (eventBus.hasPendingEvents()) {
				kernel.dmesg("Some event will be not processed.");
			}
		}
	}

	@Override
	public void subscribe(final Object subscriber) {
		eventBus.subscribe(subscriber);
	}

	@Override
	public void unsubscribe(final Object subscriber) {
		eventBus.unsubscribe(subscriber);
	}

	@Override
	public void publish(final Object event) {
		if (!isDestroying()) {
			eventBus.publish(event);
		}
	}

	@Override
	public boolean hasPendingEvents() {
		return eventBus.hasPendingEvents();
	}
}
