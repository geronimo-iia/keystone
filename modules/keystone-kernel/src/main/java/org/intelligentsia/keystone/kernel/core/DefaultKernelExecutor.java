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
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * DefaultKernelExecutor implements {@link KernelExecutor} y delegating to a
 * {@link PausableThreadPoolExecutor} instance .
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class DefaultKernelExecutor implements KernelExecutor {
	/**
	 * Inner {@link PausableThreadPoolExecutor} instance.
	 */
	private final PausableThreadPoolExecutor executorService;
	/**
	 * Inner {@link BlockingQueue} list of await task.
	 */
	private final BlockingQueue<Future<?>> awaitQueue;
	/** lock to await termination */
	private final ReentrantLock lock;
	/** {@link Condition} to await termination */
	final Condition awaitTerminationCondition;;

	/**
	 * Build a new instance of DefaultKernelExecutor.java.
	 */
	public DefaultKernelExecutor() {
		this(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS);
	}

	/**
	 * Build a new instance of DefaultKernelExecutor.java.
	 * 
	 * @param corePoolSize
	 * @param maximumPoolSize
	 * @param keepAliveTime
	 * @param unit
	 * @throws NullPointerException
	 */
	public DefaultKernelExecutor(final int corePoolSize, final int maximumPoolSize, final long keepAliveTime, final TimeUnit unit) throws NullPointerException {
		super();
		this.executorService = new PausableThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, new SynchronousQueue<Runnable>(), new RejectedExecutionHandler() {

			@Override
			public void rejectedExecution(final Runnable r, final ThreadPoolExecutor executor) {
				// remove Runnable from await queue
				awaitQueue.remove(r);
				// throw exception
				throw new RejectedExecutionException("Kernel is shutdown");
			}
		});
		this.awaitQueue = new LinkedBlockingQueue<Future<?>>();
		lock = new ReentrantLock();
		awaitTerminationCondition = lock.newCondition();
	}

	@Override
	public <V> Future<V> submit(final Callable<V> task) throws RejectedExecutionException {
		final KernelFuture<V> f = new KernelFuture<V>(task);
		executorService.execute(f);
		return f;
	}

	@Override
	public <V> Future<V> submit(final Runnable task, final V result) throws RejectedExecutionException {
		final KernelFuture<V> f = new KernelFuture<V>(task, result);
		executorService.execute(f);
		return f;
	}

	@Override
	public void shutdown() {
		executorService.shutdown();
	}

	@Override
	public boolean awaitTermination() {
		return awaitQueue.isEmpty();
	}

	@Override
	public boolean awaitTermination(final long timeout, final TimeUnit unit) throws InterruptedException {
		long nanos = unit.toNanos(timeout);
		lock.lock();
		try {
			do {
				if (awaitQueue.isEmpty()) {
					return true;
				}
				if (nanos <= 0) {
					return false;
				}
				nanos = awaitTerminationCondition.awaitNanos(nanos);
			} while (true);
		} finally {
			lock.unlock();
		}
	}

	@Override
	public List<Runnable> shutdownNow() {
		return executorService.shutdownNow();
	}

	@Override
	public void pause() {
		executorService.pause();
	}

	@Override
	public void resume() {
		executorService.resume();
	}

	/**
	 * 
	 * KernelFuture add task to await queue.
	 * 
	 * @param <V>
	 * 
	 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
	 */
	private class KernelFuture<V> extends FutureTask<V> {

		KernelFuture(final Callable<V> callable) {
			super(callable);
			awaitQueue.add(this);
		}

		KernelFuture(final Runnable runnable, final V result) {
			super(runnable, result);
			awaitQueue.add(this);
		}

	}

	/**
	 * 
	 * PausableThreadPoolExecutor.
	 * 
	 * @see http://docs.oracle.com/javase/6/docs/api/java/util/concurrent/
	 *      ThreadPoolExecutor.html
	 * 
	 *      Remove task from List "awaitQueue" after execution.
	 * 
	 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
	 * 
	 */
	class PausableThreadPoolExecutor extends ThreadPoolExecutor {
		private boolean isPaused;
		private final ReentrantLock pauseLock = new ReentrantLock();
		private final Condition unpaused = pauseLock.newCondition();

		public PausableThreadPoolExecutor(final int corePoolSize, final int maximumPoolSize, final long keepAliveTime, final TimeUnit unit, final BlockingQueue<Runnable> workQueue, final RejectedExecutionHandler handler) {
			super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
		}

		public PausableThreadPoolExecutor(final int corePoolSize, final int maximumPoolSize, final long keepAliveTime, final TimeUnit unit, final BlockingQueue<Runnable> workQueue, final ThreadFactory threadFactory,
				final RejectedExecutionHandler handler) {
			super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
		}

		public PausableThreadPoolExecutor(final int corePoolSize, final int maximumPoolSize, final long keepAliveTime, final TimeUnit unit, final BlockingQueue<Runnable> workQueue, final ThreadFactory threadFactory) {
			super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
		}

		public PausableThreadPoolExecutor(final int corePoolSize, final int maximumPoolSize, final long keepAliveTime, final TimeUnit unit, final BlockingQueue<Runnable> workQueue) {
			super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
		}

		@Override
		public boolean awaitTermination(final long timeout, final TimeUnit unit) throws InterruptedException {
			return super.awaitTermination(timeout, unit);
		}

		@Override
		protected void beforeExecute(final Thread t, final Runnable r) {
			super.beforeExecute(t, r);
			pauseLock.lock();
			try {
				while (isPaused) {
					unpaused.await();
				}
			} catch (final InterruptedException ie) {
				t.interrupt();
			} finally {
				pauseLock.unlock();
			}
		}

		@Override
		protected void afterExecute(final Runnable r, final Throwable t) {
			super.afterExecute(r, t);
			awaitQueue.remove(r);
		}

		public void pause() {
			pauseLock.lock();
			try {
				isPaused = true;
			} finally {
				pauseLock.unlock();
			}
		}

		public void resume() {
			pauseLock.lock();
			try {
				isPaused = false;
				unpaused.signalAll();
			} finally {
				pauseLock.unlock();
			}
		}
	}

}
