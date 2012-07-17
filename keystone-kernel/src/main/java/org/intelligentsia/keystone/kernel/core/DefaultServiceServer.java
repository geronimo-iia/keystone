/**
 * 
 */
package org.intelligentsia.keystone.kernel.core;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.intelligentsia.keystone.api.artifacts.ArtifactIdentifier;
import org.intelligentsia.keystone.api.artifacts.KeystoneRuntimeException;
import org.intelligentsia.keystone.kernel.ArtifactContext;
import org.intelligentsia.keystone.kernel.ServiceServer;
import org.intelligentsia.keystone.kernel.service.Service;

/**
 * {@link DefaultServiceServer} extends {@link AbstractKernelServer} and
 * implements {@link ServiceServer}.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class DefaultServiceServer extends AbstractKernelServer implements ServiceServer {

	private Map<String, ServiceRegistry> registry = new HashMap<String, DefaultServiceServer.ServiceRegistry>();

	/**
	 * Build a new instance of DefaultServiceServer.java.
	 */
	public DefaultServiceServer() {
		super();
	}

	@Override
	protected void onInitialize() throws KeystoneRuntimeException {
	}

	@Override
	protected void onDestroy() {

		registry.clear();
	}

	/**
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<Service> iterator() {
		return null;
	}

	@Override
	public void register(ArtifactContext artifactContext, Class<Service> serviceClassName, Service service) throws KeystoneRuntimeException, NullPointerException {
		if (isDestroying()) {
			throw new KeystoneRuntimeException("cannot register new service instance when destroying service");
		}
		if (artifactContext == null) {
			throw new NullPointerException("artifactContext");
		}
		if (serviceClassName == null) {
			throw new NullPointerException("serviceClassName");
		}
		if (service == null) {
			throw new NullPointerException("service");
		}
		// add registry entry.
		getServiceRegistry(serviceClassName);
	}

	@Override
	public void unregister(ArtifactContext artifactContext, Class<Service> serviceClassName) throws KeystoneRuntimeException {
	}

	
	protected ServiceRegistry getServiceRegistry(Class<Service> serviceClassName) {
		ServiceRegistry result = registry.get(serviceClassName.getName());
		if (result==null) {
			result = new ServiceRegistry(); 
			registry.put(serviceClassName.getName(), result);
		}
		return result;
	}
	/**
	 * ServiceRegistry track all resgistration of same service name with
	 * different artifact identifier.
	 * 
	 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
	 */
	private class ServiceRegistry {

		Map<ArtifactIdentifier, Service> entries = new HashMap<ArtifactIdentifier, Service>(4);

		public ServiceRegistry() {
			super();
		}

	}

}
