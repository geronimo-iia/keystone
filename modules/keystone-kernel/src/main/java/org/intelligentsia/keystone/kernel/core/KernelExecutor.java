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

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * KernelExecutor declare methods to excute some task at kernel level.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface KernelExecutor {

	/**
	 * Submits a value-returning task for execution.
	 * 
	 * @param task
	 *            the task
	 * @return a {@link Future} representing the pending results of the task
	 * @throws RejectedExecutionException
	 *             if {@link KernelExecutor} is currently shutting down.
	 */
	public <V> Future<V> submit(Callable<V> task) throws RejectedExecutionException;

	/**
	 * Submits a Runnable task for execution.
	 * 
	 * @param task
	 *            the task
	 * @param result
	 *            the result to return upon successful completion
	 * @return a {@link Future} representing that task.
	 * @throws RejectedExecutionException
	 *             if {@link KernelExecutor} is currently shutting down.
	 */
	public <V> Future<V> submit(Runnable task, V result) throws RejectedExecutionException;

	/**
	 * Block any futur task to be submitted.
	 */
	public void shutdown();

	/**
	 * Blocks until all tasks have completed execution.
	 * 
	 * @param timeout
	 *            the maximum time to wait
	 * @param unit
	 *            the time unit of the timeout argument
	 * @return <tt>true</tt> if this executor terminated and <tt>false</tt> if
	 *         the timeout elapsed before termination
	 * @throws InterruptedException
	 *             if interrupted while waiting
	 */
	public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException;

	/**
	 * @return true if all previously submitted task are ended.
	 */
	public boolean awaitTermination();

	/**
	 * Attempts to stop all actively executing tasks, halts the processing of
	 * waiting tasks, and returns a list of the tasks that were awaiting
	 * execution.
	 * 
	 * @return list of tasks that never commenced execution
	 */
	public List<Runnable> shutdownNow();

	/**
	 * Pause all waiting task.
	 */
	public void pause();

	/**
	 * Resume all waiting task.
	 */
	public void resume();
}
