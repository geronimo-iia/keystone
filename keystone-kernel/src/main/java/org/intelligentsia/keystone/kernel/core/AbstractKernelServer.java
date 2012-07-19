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
package org.intelligentsia.keystone.kernel.core;

import org.intelligentsia.keystone.api.artifacts.KeystoneRuntimeException;
import org.intelligentsia.keystone.kernel.EventPublisher;
import org.intelligentsia.keystone.kernel.Kernel;
import org.intelligentsia.keystone.kernel.KernelContext;
import org.intelligentsia.keystone.kernel.KernelServer;
import org.intelligentsia.keystone.kernel.service.Service;
import org.intelligentsia.keystone.kernel.service.ServiceProvider;

/**
 * {@link AbstractKernelServer} class.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public abstract class AbstractKernelServer implements KernelServer {

	protected String author = "";
	protected String description = "";
	protected Kernel kernel;
	private State state = State.CREATED;

	/**
	 * Build a new instance of AbstractKernelServer.java.
	 */
	public AbstractKernelServer() {
		super();
	}

	/**
	 * Build a new instance of AbstractKernelServer.java.
	 * 
	 * @param author
	 * @param description
	 */
	public AbstractKernelServer(final String author, final String description) {
		super();
		this.author = author;
		this.description = description;
	}

	@Override
	public String getAuthor() {
		return author;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public State getState() {
		return state;
	}

	@Override
	public final void initialize(final Kernel kernel) throws KeystoneRuntimeException {
		state = State.INITIALIZING;
		this.kernel = kernel;
		if (kernel.getEventBus() != null) {
			kernel.getEventBus().subscribe(this);
		}
		onInitialize();
		state = State.READY;
	}

	@Override
	public final void destroy() {
		state = State.DESTROYING;
		this.kernel = null;
		onDestroy();
		if (kernel.getEventBus() != null) {
			kernel.getEventBus().unsubscribe(this);
		}
		state = State.EOL;
	}

	/**
	 * Called on initialize event.
	 * 
	 * @throws KeystoneRuntimeException
	 *             if error occurs
	 */
	protected abstract void onInitialize() throws KeystoneRuntimeException;

	/**
	 * Called on detsory event.
	 */
	protected abstract void onDestroy();

	/**
	 * @return {@link Boolean#TRUE} if this instance is currently in 'destroy'
	 *         process.
	 */
	protected boolean isDestroying() {
		return State.DESTROYING.equals(state);
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " [author=" + author + ", description=" + description + "]";
	}

	/**
	 * @return a new kernel context instance.
	 */
	protected KernelContext getKernelContext() {
		return new KernelContext() {

			private final EventPublisher eventPublisher = new EventPublisher() {

				@Override
				public void publish(final Object event) {
					kernel.getEventBus().publish(event);
				}
			};

			@Override
			public EventPublisher getEventPublisher() {
				return eventPublisher;
			}

			@Override
			public ServiceProvider find(final Class<? extends Service> service) throws KeystoneRuntimeException {
				return kernel.getServiceServer().find(service);
			}

		};
	}
}
