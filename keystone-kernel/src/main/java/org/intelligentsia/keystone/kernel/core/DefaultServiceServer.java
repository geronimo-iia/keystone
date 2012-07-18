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

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.intelligentsia.keystone.api.artifacts.ArtifactIdentifier;
import org.intelligentsia.keystone.api.artifacts.KeystoneRuntimeException;
import org.intelligentsia.keystone.kernel.ArtifactContext;
import org.intelligentsia.keystone.kernel.ServiceServer;
import org.intelligentsia.keystone.kernel.event.ServiceRegistryChangeEvent;
import org.intelligentsia.keystone.kernel.service.Service;

/**
 * {@link DefaultServiceServer} extends {@link AbstractKernelServer} and
 * implements {@link ServiceServer}.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class DefaultServiceServer extends AbstractKernelServer implements ServiceServer {

	private final Map<Class<Service>, ServiceRegistry> registry = new ConcurrentHashMap<Class<Service>, DefaultServiceServer.ServiceRegistry>();

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
		for (final ServiceRegistry serviceRegistry : registry.values()) {
			for (final ArtifactIdentifier artifactIdentifier : serviceRegistry) {
				destroy(serviceRegistry, artifactIdentifier);
			}
			serviceRegistry.clear();
		}
		registry.clear();
	}

	@Override
	public Iterator<Class<Service>> iterator() {
		return registry.keySet().iterator();
	}

	@Override
	public void register(final ArtifactContext artifactContext, final Class<Service> serviceClassName, final Service service) throws KeystoneRuntimeException, NullPointerException {
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
		// get registry entry.
		final ServiceRegistry serviceRegistry = getServiceRegistry(serviceClassName);
		// check if ever registered
		if (serviceRegistry.contains(artifactContext.getArtifactIdentifier())) {
			throw new KeystoneRuntimeException(serviceClassName + " is ever registered with " + artifactContext.getArtifactIdentifier().toString());
		}
		// initialize
		service.initialize(getKernelContext());
		// add
		serviceRegistry.put(artifactContext.getArtifactIdentifier(), service);
		// raise event
		kernel.getEventBus().publish(new ServiceRegistryChangeEvent(artifactContext.getArtifactIdentifier(), serviceClassName, ServiceRegistryChangeEvent.State.REGISTERED));
	}

	@Override
	public void unregister(final ArtifactContext artifactContext, final Class<Service> serviceClassName) throws KeystoneRuntimeException, NullPointerException {
		if (artifactContext == null) {
			throw new NullPointerException("artifactContext");
		}
		if (serviceClassName == null) {
			throw new NullPointerException("serviceClassName");
		}
		// get registry
		destroy(getServiceRegistry(serviceClassName), artifactContext.getArtifactIdentifier());
		// raise event
		if (!isDestroying()) {
			kernel.getEventBus().publish(new ServiceRegistryChangeEvent(artifactContext.getArtifactIdentifier(), serviceClassName, ServiceRegistryChangeEvent.State.UNREGISTERED));
		}
	}

	/**
	 * Utility: return associated {@link ServiceRegistry} instance with
	 * specified service Class Name
	 * 
	 * @param serviceClassName
	 * @return {@link ServiceRegistry} instance or a new one if not exists.
	 */
	protected ServiceRegistry getServiceRegistry(final Class<Service> serviceClassName) {
		ServiceRegistry result = registry.get(serviceClassName);
		if (result == null) {
			result = new ServiceRegistry();
			registry.put(serviceClassName, result);
		}
		return result;
	}

	/**
	 * Utility to destroy a specific service.
	 * 
	 * @param serviceRegistry
	 * @param artifactIdentifier
	 */
	protected void destroy(final ServiceRegistry serviceRegistry, final ArtifactIdentifier artifactIdentifier) {
		final Service service = serviceRegistry.get(artifactIdentifier);
		if (service != null) {
			service.destroy();
		}
	}

	/**
	 * ServiceRegistry track all resgistration of same service name with
	 * different artifact identifier.
	 * 
	 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
	 */
	private class ServiceRegistry implements Iterable<ArtifactIdentifier> {

		final Map<ArtifactIdentifier, Service> entries = new ConcurrentHashMap<ArtifactIdentifier, Service>(4);

		/**
		 * Build a new instance of DefaultServiceServer.java.
		 */
		public ServiceRegistry() {
			super();
		}

		public boolean contains(final ArtifactIdentifier key) {
			return entries.containsKey(key);
		}

		public Service get(final ArtifactIdentifier key) {
			return entries.get(key);
		}

		public Service put(final ArtifactIdentifier key, final Service value) {
			return entries.put(key, value);
		}

		public void clear() {
			entries.clear();
		}

		@Override
		public Iterator<ArtifactIdentifier> iterator() {
			return entries.keySet().iterator();
		}

	}

}
