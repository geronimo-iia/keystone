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
import org.intelligentsia.keystone.kernel.service.ServiceProvider;
import org.intelligentsia.keystone.kernel.service.ServiceRegistryKey;
import org.intelligentsia.utilities.Preconditions;

/**
 * {@link DefaultServiceServer} extends {@link AbstractKernelServer} and
 * implements {@link ServiceServer}.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class DefaultServiceServer extends AbstractKernelServer implements ServiceServer {

	/**
	 * Registry instance.
	 */
	private final Map<Class<? extends Service>, ServiceProvider> registry = new ConcurrentHashMap<Class<? extends Service>, ServiceProvider>();

	/**
	 * Build a new instance of DefaultServiceServer.java.
	 */
	public DefaultServiceServer() {
		super("service-server");
	}

	@Override
	protected void onInitialize() throws KeystoneRuntimeException {
	}

	@Override
	protected void onDestroy() {
		for (final ServiceProvider serviceProvider : registry.values()) {
			for (final ArtifactIdentifier artifactIdentifier : serviceProvider.keys()) {
				final ServiceRegistryKey key = serviceProvider.get(artifactIdentifier);
				if ((key != null) && (key.getService() != null)) {
					key.getService().destroy();
				}
			}
			((DefaultServiceProvider) serviceProvider).clear();
		}
		registry.clear();
	}

	@Override
	public Iterator<Class<? extends Service>> iterator() {
		return registry.keySet().iterator();
	}

	@Override
	public void register(final ArtifactContext artifactContext, final Class<Service> serviceClassName, final Service service) throws KeystoneRuntimeException, NullPointerException {
		if (isDestroying()) {
			throw new KeystoneRuntimeException("cannot register new service instance when destroying service");
		}
		Preconditions.checkNotNull(artifactContext, "artifactContext");
		Preconditions.checkNotNull(serviceClassName, "serviceClassName");
		Preconditions.checkNotNull(service, "service");
		// get registry entry.
		final DefaultServiceProvider serviceProvider = (DefaultServiceProvider) find(serviceClassName);
		// check if ever registered
		if (serviceProvider.contains(artifactContext.getArtifactIdentifier())) {
			throw new KeystoneRuntimeException(serviceClassName + " is ever registered with " + artifactContext.getArtifactIdentifier().toString());
		}
		// initialize
		service.initialize(getKernelContext());
		// add
		serviceProvider.put(new ServiceRegistryKey(artifactContext.getArtifactIdentifier(), service));
		// raise event
		kernel.getEventBus().publish(new ServiceRegistryChangeEvent(artifactContext.getArtifactIdentifier(), serviceClassName, ServiceRegistryChangeEvent.State.REGISTERED));
	}

	@Override
	public void unregister(final ArtifactContext artifactContext, final Class<Service> serviceClassName) throws KeystoneRuntimeException, NullPointerException {
		Preconditions.checkNotNull(artifactContext, "artifactContext");
		Preconditions.checkNotNull(serviceClassName, "serviceClassName");
		// get registry
		final DefaultServiceProvider serviceProvider = (DefaultServiceProvider) find(serviceClassName);
		final ServiceRegistryKey key = serviceProvider.remove(artifactContext.getArtifactIdentifier());
		// destroy
		if ((key != null) && (key.getService() != null)) {
			key.getService().destroy();
			// raise event
			if (!isDestroying()) {
				kernel.getEventBus().publish(new ServiceRegistryChangeEvent(artifactContext.getArtifactIdentifier(), serviceClassName, ServiceRegistryChangeEvent.State.UNREGISTERED));
			}
		}
	}

	@Override
	public ServiceProvider find(final Class<? extends Service> service) throws KeystoneRuntimeException {
		ServiceProvider serviceProvider = registry.get(service);
		if (serviceProvider == null) {
			serviceProvider = new DefaultServiceProvider();
			registry.put(service, serviceProvider);
		}
		return serviceProvider;
	}

	/**
	 * Utility to destroy a specific service.
	 * 
	 * @param serviceProvider
	 * @param artifactIdentifier
	 */
	protected void destroy(final DefaultServiceProvider serviceProvider, final ArtifactIdentifier artifactIdentifier) {
		final ServiceRegistryKey serviceRegistryKey = serviceProvider.get(artifactIdentifier);
		if (serviceRegistryKey != null) {
			serviceRegistryKey.getService().destroy();
		}
	}

	/**
	 * DefaultServiceProvider implements {@link ServiceProvider}: track all
	 * registration of same service name with different artifact identifier.
	 * 
	 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
	 */
	private class DefaultServiceProvider implements ServiceProvider {
		private final Map<ArtifactIdentifier, ServiceRegistryKey> entries = new ConcurrentHashMap<ArtifactIdentifier, ServiceRegistryKey>(4);

		public DefaultServiceProvider() {
			super();
		}

		@Override
		public Iterable<ArtifactIdentifier> keys() {
			return entries.keySet();
		}

		@Override
		public boolean contains(final ArtifactIdentifier key) {
			return entries.containsKey(key);
		}

		@Override
		public ServiceRegistryKey get(final ArtifactIdentifier key) {
			return entries.get(key);
		}

		public ServiceRegistryKey put(final ServiceRegistryKey serviceRegistryKey) {
			return entries.put(serviceRegistryKey.getArtifactIdentifier(), serviceRegistryKey);
		}

		public ServiceRegistryKey remove(final ArtifactIdentifier artifactIdentifier) {
			return entries.remove(artifactIdentifier);
		}

		public void clear() {
			entries.clear();
		}

		@Override
		public Iterator<ServiceRegistryKey> iterator() {
			return entries.values().iterator();
		}

	}

}
