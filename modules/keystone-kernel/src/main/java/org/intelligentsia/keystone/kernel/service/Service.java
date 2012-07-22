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
package org.intelligentsia.keystone.kernel.service;

import org.intelligentsia.keystone.kernel.KernelContext;

/**
 * Service declare methods to manage a 'service' in our system.
 * 
 * <p>
 * A service —also known as a subsystem, extends the functionality provided by
 * the microkernel. It represents a separate component that offers additional
 * functionality.
 * </p>
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public interface Service {

	/**
	 * @return a friendly user name.
	 */
	public String getName();

	/**
	 * Initialize this service instance with specified kernel context.
	 * 
	 * @param context
	 */
	public void initialize(KernelContext context);

	/**
	 * Destroy this service instance.
	 * 
	 */
	public void destroy();

}
