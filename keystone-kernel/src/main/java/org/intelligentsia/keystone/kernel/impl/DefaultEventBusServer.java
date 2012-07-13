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
