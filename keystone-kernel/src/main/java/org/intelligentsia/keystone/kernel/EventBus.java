package org.intelligentsia.keystone.kernel;

/**
 * EventBus declare methods to subscribe, unsubscribe and publish event.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface EventBus {

	/**
	 * Subscribes the specified service to the event bus.
	 * 
	 * @param subscriber
	 *            The service to subscribe to the event bus.
	 */
	public void subscribe(Object subscriber);

	/**
	 * <p>
	 * Removes the specified service from the event bus subscription list. Once
	 * removed, the specified object will no longer receive events posted to the
	 * event bus.
	 * </p>
	 * 
	 * @param subscriber
	 *            The service previous subscribed to the event bus.
	 */
	public void unsubscribe(Object subscriber);

	/**
	 * <p>
	 * Sends a message on the bus which will be propagated to the appropriate
	 * subscribers of the event type. Only subscribers which have elected to
	 * subscribe to the same event type as the supplied event will be notified
	 * of the event.
	 * </p>
	 * <p>
	 * There is no specification given as to how the messages will be delivered,
	 * in terms of synchronous or asynchronous. The only requirement is that all
	 * the event handlers that can issue vetos be called before non-vetoing
	 * handlers. Most implementations will likely deliver messages
	 * asynchronously.
	 * </p>
	 * 
	 * 
	 * @param event
	 *            The event to send out to the subscribers of the same type.
	 */
	public void publish(Object event);
}
