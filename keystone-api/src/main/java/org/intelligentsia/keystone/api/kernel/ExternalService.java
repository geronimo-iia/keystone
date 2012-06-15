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
package org.intelligentsia.keystone.api.kernel;

/**
 * 
 * ExternalService.
 * 
 * 
 * <p>
 * From design pattern:<br />
 * An external service is a component that uses the microkernel for implementing
 * its own view of the underlying application domain. As already mentioned, a
 * view denotes a layer of abstraction built on top of the atomic services
 * provided by the microkernel. Different external servers implement different
 * policies for specific application domains.
 * </p>
 * <p>
 * External service expose their functionality by exporting interfaces in the
 * same way as the microkernel itself does. Each of these external service runs
 * in a separate process. It receives service requests from client applications
 * using the communication facilities provided by the microkernel, interprets
 * these requests, executes the appropriate services and returns results to its
 * clients. The implementation of services relies on microkernel mechanisms, so
 * external servers need to access the microkernel’s programming interfaces.
 * </p>
 * *
 * <p>
 * Responsibility
 * </p>
 * <ul>
 * <li>Provides programming interfaces for its clients.</li>
 * </ul>
 * 
 * <p>
 * Responsibility
 * </p>
 * <ul>
 * <li>Microkernel</li>
 * </ul>
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface ExternalService extends Service {

}
