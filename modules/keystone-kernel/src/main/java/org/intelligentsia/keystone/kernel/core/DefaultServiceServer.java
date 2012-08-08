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

import org.intelligentsia.keystone.api.Preconditions;
import org.intelligentsia.keystone.api.artifacts.ArtifactIdentifier;
import org.intelligentsia.keystone.api.artifacts.KeystoneRuntimeException;
import org.intelligentsia.keystone.kernel.ArtifactContext;
import org.intelligentsia.keystone.kernel.Service;
import org.intelligentsia.keystone.kernel.ServiceProvider;
import org.intelligentsia.keystone.kernel.ServiceRegistryKey;
import org.intelligentsia.keystone.kernel.ServiceServer;
import org.intelligentsia.keystone.kernel.event.ServiceRegistryChangeEvent;

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
	private final Map<Class<? extends Service>, ServiceProvider<?>> registry = new ConcurrentHashMap<Class<? extends Service>, ServiceProvider<?>>();

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
		for (final ServiceProvider<?> serviceProvider : registry.values()) {
			for (final ArtifactIdentifier artifactIdentifier : serviceProvider.keys()) {
				final ServiceRegistryKey<?> key = serviceProvider.get(artifactIdentifier);
				if ((key != null) && (key.getService() != null)) {
					key.getService().destroy();
				}
			}
			((DefaultServiceProvider<?>) serviceProvider).clear();
		}
		registry.clear();
	}

	@Override
	public Iterator<Class<? extends Service>> iterator() {
		return registry.keySet().iterator();
	}

	@Override
	public <S extends Service> void register(final ArtifactContext artifactContext, final Class<S> serviceClassName, final S service) throws KeystoneRuntimeException, NullPointerException {
		if (isDestroying()) {
			throw new KeystoneRuntimeException("cannot register new service instance when destroying service");
		}
		Preconditions.checkNotNull(artifactContext, "artifactContext");
		Preconditions.checkNotNull(serviceClassName, "serviceClassName");
		Preconditions.checkNotNull(service, "service");
		// get registry entry.
		final DefaultServiceProvider<S> serviceProvider = (DefaultServiceProvider<S>) find(serviceClassName);
		// check if ever registered
		if (serviceProvider.contains(artifactContext.getArtifactIdentifier())) {
			throw new KeystoneRuntimeException(serviceClassName + " is ever registered with " + artifactContext.getArtifactIdentifier().toString());
		}
		// initialize
		service.initialize(getKernelContext());
		// add
		serviceProvider.put(new ServiceRegistryKey<S>(artifactContext.getArtifactIdentifier(), service));
		// raise event
		kernel.eventBus().publish(new ServiceRegistryChangeEvent(artifactContext.getArtifactIdentifier(), serviceClassName, ServiceRegistryChangeEvent.State.REGISTERED));
	}

	@Override
	public <S extends Service> void unregister(final ArtifactContext artifactContext, final Class<S> serviceClassName) throws KeystoneRuntimeException, NullPointerException {
		Preconditions.checkNotNull(artifactContext, "artifactContext");
		Preconditions.checkNotNull(serviceClassName, "serviceClassName");
		// get registry
		final DefaultServiceProvider<S> serviceProvider = (DefaultServiceProvider<S>) find(serviceClassName);
		final ServiceRegistryKey<?> key = serviceProvider.remove(artifactContext.getArtifactIdentifier());
		// destroy
		if ((key != null) && (key.getService() != null)) {
			key.getService().destroy();
			// raise event
			if (!isDestroying()) {
				kernel.eventBus().publish(new ServiceRegistryChangeEvent(artifactContext.getArtifactIdentifier(), serviceClassName, ServiceRegistryChangeEvent.State.UNREGISTERED));
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Service> ServiceProvider<T> find(final Class<T> service) throws KeystoneRuntimeException {
		ServiceProvider<T> serviceProvider = (ServiceProvider<T>) registry.get(service);
		if (serviceProvider == null) {
			serviceProvider = new DefaultServiceProvider<T>();
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
	protected void destroy(final DefaultServiceProvider<?> serviceProvider, final ArtifactIdentifier artifactIdentifier) {
		final ServiceRegistryKey<?> serviceRegistryKey = serviceProvider.get(artifactIdentifier);
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
	private class DefaultServiceProvider<T extends Service> implements ServiceProvider<T> {
		private final Map<ArtifactIdentifier, ServiceRegistryKey<T>> entries = new ConcurrentHashMap<ArtifactIdentifier, ServiceRegistryKey<T>>(4);

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
		public ServiceRegistryKey<T> get(final ArtifactIdentifier key) {
			return entries.get(key);
		}

		public ServiceRegistryKey<T> put(final ServiceRegistryKey<T> serviceRegistryKey) {
			return entries.put(serviceRegistryKey.getArtifactIdentifier(), serviceRegistryKey);
		}

		public ServiceRegistryKey<T> remove(final ArtifactIdentifier artifactIdentifier) {
			return entries.remove(artifactIdentifier);
		}

		public void clear() {
			entries.clear();
		}

		@Override
		public boolean isEmpty() {
			return entries.isEmpty();
		}

		@Override
		public Iterator<ServiceRegistryKey<T>> iterator() {
			return entries.values().iterator();
		}

		@Override
		public T getService() {
			return entries.isEmpty() ? null : entries.get(0).getService();
		}

	}

}
