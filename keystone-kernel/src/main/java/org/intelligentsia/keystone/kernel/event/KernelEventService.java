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
package org.intelligentsia.keystone.kernel.event;

import org.intelligentsia.keystone.kernel.Service;

import com.adamtaft.eventbus.EventHandler;
import com.adamtaft.eventbus.VetoException;
 

/**
 * KernelEventService.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface KernelEventService extends Service {

	/**
	 * Subscribes the specified service to the event bus. A subscribed service
	 * will be notified of any published events on the methods annotated with
	 * the {@link EventHandler} annotation.
	 * <p>
	 * Each event handler method should take a single parameter indicating the
	 * type of event it wishes to receive. When events are published on the bus,
	 * only subscribers who have an EventHandler method with a matching
	 * parameter of the same type as the published event will receive the event
	 * notification from the bus.
	 * 
	 * @param subscriber
	 *            The service to subscribe to the event bus.
	 */
	public void subscribe(Service subscriber);

	/**
	 * Removes the specified service from the event bus subscription list. Once
	 * removed, the specified object will no longer receive events posted to the
	 * event bus.
	 * 
	 * @param subscriber
	 *            The service previous subscribed to the event bus.
	 */
	public void unsubscribe(Service subscriber);
	
	/**
	 * Sends a message on the bus which will be propagated to the appropriate
	 * subscribers of the event type. Only subscribers which have elected to
	 * subscribe to the same event type as the supplied event will be notified
	 * of the event.
	 * <p>
	 * Events can be vetoed, indicating that the event should not propagate to
	 * the subscribers that don't have a veto. The subscriber can veto by
	 * setting the {@link EventHandler#canVeto()} return to true and by throwing
	 * a {@link VetoException}.
	 * <p>
	 * There is no specification given as to how the messages will be delivered,
	 * in terms of synchronous or asynchronous. The only requirement is that all
	 * the event handlers that can issue vetos be called before non-vetoing
	 * handlers. Most implementations will likely deliver messages
	 * asynchronously.
	 * 
	 * @param event
	 *            The event to send out to the subscribers of the same type.
	 */
	public void publish(Object event);
}
