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

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * KernelExecutor declare methods to excute some task at kernel level.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface KernelExecutor {

	void execute(Runnable command);

	void shutdown();

	boolean awaitTermination(long awaitTerminationTimeout, TimeUnit awaitTerminationTimeUnit) throws InterruptedException;

	List<Runnable> shutdownNow();

	//Submits a value-returning task for execution and returns a Future representing the pending results of the task
	//Upon completion, this task may be taken or polled.
	<V> Future<V> submit(Callable<V> task);
	// Submits a Runnable task for execution and returns a Future representing
	// that task.
	//result the result to return upon successful completion
	<V> Future<V> submit(Runnable task, V result);

}
