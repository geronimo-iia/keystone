/**
 * 
 */
package org.intelligentsia.keystone.kernel.impl;

import org.intelligentsia.keystone.kernel.EventBusServer;

import com.adamtaft.eventbus.BasicEventBus;
import com.adamtaft.eventbus.EventBus;

/**
 * @author geronimo
 *
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
		this.eventBus=new BasicEventBus();
	}

	/* (non-Javadoc)
	 * @see org.intelligentsia.keystone.kernel.EventBusServer#subscribe(java.lang.Object)
	 */
	@Override
	public void subscribe(Object subscriber) {
		// TODO Auto-generated method stub
		eventBus.subscribe(subscriber);

	}

	/* (non-Javadoc)
	 * @see org.intelligentsia.keystone.kernel.EventBusServer#unsubscribe(java.lang.Object)
	 */
	@Override
	public void unsubscribe(Object subscriber) {
		// TODO Auto-generated method stub
		eventBus.unsubscribe(subscriber);
	}

	/* (non-Javadoc)
	 * @see org.intelligentsia.keystone.kernel.EventBusServer#publish(java.lang.Object)
	 */
	@Override
	public void publish(Object event) {
		// TODO Auto-generated method stub
		eventBus.publish(event);
	}

}
