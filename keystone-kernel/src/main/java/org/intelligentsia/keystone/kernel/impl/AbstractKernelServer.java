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
package org.intelligentsia.keystone.kernel.impl;

import org.intelligentsia.keystone.kernel.Kernel;
import org.intelligentsia.keystone.kernel.KernelServer;

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
	public final void initialize(final Kernel kernel) {
		state = State.INITIALIZING;
		this.kernel = kernel;
		onInitialize();
		state = State.READY;
	}

	@Override
	public final void destroy() {
		state = State.DESTROYING;
		this.kernel = null;
		onDestroy();
		state = State.EOL;
	}

	/**
	 * Called on initialize event.
	 */
	protected abstract void onInitialize();

	/**
	 * Called on detsory event.
	 */
	protected abstract void onDestroy();

	/**
	 * Log a message in kernel message.
	 * 
	 * @param message
	 * @param args
	 */
	protected void log(final String message, final Object... args) {

	}

	/**
	 * Log a message in kernel message.
	 * 
	 * @param throwable
	 * @param message
	 * @param args
	 */
	protected void log(final Throwable throwable, final String message, final Object... args) {

	}

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

}
