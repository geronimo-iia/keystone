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
