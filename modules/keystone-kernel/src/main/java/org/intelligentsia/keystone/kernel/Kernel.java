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
package org.intelligentsia.keystone.kernel;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * Kernel interface which declare core methods.
 * <p>
 * Microkernel Responsibility:
 * </p>
 * <ul>
 * <li>Provides core mechanisms</li>
 * <li>Offers communication facilities</li>
 * <li>Encapsulates system dependencies</li>
 * <li>Manages and controls resources</li>
 * </ul>
 * 
 * <p>
 * “Perfection is not achieved when there is nothing left to add, but when there
 * is nothing left to take away”<br />
 * --Antoine de St. Exupery
 * </p>
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface Kernel extends Runnable {
	/**
	 * Kernel State.
	 * 
	 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
	 */
	public enum State {
		SOL, READY, EOL
	}

	/**
	 * @return a {@link RepositoryServer} instance.
	 */
	public RepositoryServer repositoryServer();

	/**
	 * @return a {@link ArtifactServer} instance.
	 */
	public ArtifactServer artifactServer();

	/**
	 * @return a {@link EventBusServer} instance.
	 */
	public EventBusServer eventBus();

	/**
	 * @return a {@link ServiceServer} instance.
	 */
	public ServiceServer serviceServer();

	/**
	 * Display a message in kernel message log.
	 * 
	 * @param message
	 *            message templaye
	 * @param args
	 *            arguments to substitute of the '%s' placeholders
	 */
	public void dmesg(final String message, final Object... args);

	/**
	 * @return Kernel {@link State}.
	 */
	public State state();

	/**
	 * Submits a value-returning task for execution.
	 * 
	 * @param task
	 *            the task
	 * @return a {@link Future} representing the pending results of the task
	 */
	public <V> Future<V> submit(Callable<V> task);

	/**
	 * Submits a Runnable task for execution.
	 * 
	 * @param task
	 *            the task
	 * @param result
	 *            the result to return upon successful completion
	 * @return a {@link Future} representing that task.
	 */
	public <V> Future<V> submit(Runnable task, V result);
}
