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
package org.intelligentsia.keystone.kernel.init;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.intelligentsia.keystone.kernel.ArtifactServer;
import org.intelligentsia.keystone.kernel.EventBusServer;
import org.intelligentsia.keystone.kernel.Kernel;
import org.intelligentsia.keystone.kernel.KernelServer;
import org.intelligentsia.keystone.kernel.KernelServer.State;
import org.intelligentsia.keystone.kernel.RepositoryServer;
import org.intelligentsia.keystone.kernel.ServiceServer;
import org.intelligentsia.keystone.kernel.core.BaseKernel;
import org.junit.Test;

/**
 * KernelBuilderTest.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class KernelBuilderTest {

	@Test
	public void checkDefaultKernelServiceBuilder() {
		final Kernel kernel = new KernelBuilder().build();
		assertNotNull(kernel);
		assertNotNull(kernel.getEventBus());
		assertNotNull(kernel.getRepositoryServer());
		assertNotNull(kernel.getArtifactServer());
		assertNotNull(kernel.getServiceServer());
	}

	@Test
	public void checkDefaultKernelServiceRegistration() {
		final Kernel kernel = new KernelBuilder().build();
		final Iterator<KernelServer> iterator = ((BaseKernel) kernel).iterator();
		assertTrue(iterator.hasNext());
		assertNotNull(iterator.next());
		assertTrue(iterator.hasNext());
		assertNotNull(iterator.next());
		assertTrue(iterator.hasNext());
		assertNotNull(iterator.next());
		assertTrue(iterator.hasNext());
		assertNotNull(iterator.next());
	}

	@Test
	public void checkDefaultKernelServiceRegistrationOrder() {
		final Kernel kernel = new KernelBuilder().build();
		final Iterator<KernelServer> iterator = ((BaseKernel) kernel).iterator();
		assertTrue(EventBusServer.class.isAssignableFrom(iterator.next().getClass()));
		assertTrue(RepositoryServer.class.isAssignableFrom(iterator.next().getClass()));
		assertTrue(ArtifactServer.class.isAssignableFrom(iterator.next().getClass()));
		assertTrue(ServiceServer.class.isAssignableFrom(iterator.next().getClass()));
	}

	@Test
	public void checkKernelInitialization() {
		final KernelHolder holder = new KernelHolder();
		holder.setKernel(new KernelBuilder().build(new Runnable() {
			@Override
			public void run() {
				assertEquals(State.READY, holder.getKernel().getEventBus().getState());
				assertEquals(State.READY, holder.getKernel().getRepositoryServer().getState());
				assertEquals(State.READY, holder.getKernel().getArtifactServer().getState());
				assertEquals(State.READY, holder.getKernel().getServiceServer().getState());
			}
		}));
		Launchers.launchAndWait(holder.getKernel());
	}

}
