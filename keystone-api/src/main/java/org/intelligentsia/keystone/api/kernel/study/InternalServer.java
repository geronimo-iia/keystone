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
package org.intelligentsia.keystone.api.kernel.study;

/**
 * <p>
 * An internal server—also known as a subsystem extends the functionality provided by the microkernel. It represents a separate component
 * that offers additional functionality. The microkernel invokes the functionality of internal servers via service requests. Internal
 * servers can therefore encapsulate some dependencies on the underlying hardware or software system. For example, device drivers that
 * support speciﬁc graphics cards are good candidates for internal servers.
 * </p>
 * <p>
 * One of the design goals should be to keep the microkernel as small as possible to reduce memory requirements. Another goal is to provide
 * mechanisms that execute quickly, to reduce service execution time. Additional and more complex services are therefore implemented by
 * internal servers that the microkernel activates or loads only when necessary. You can consider internal servers as extensions of the
 * microkernel. Note that internal servers are only accessible by the microkernel component.
 * </p>
 * <p>
 * Responsibility<br />
 * Implements additional services<br />
 * Encapsulates some system specifics.<br />
 * </p>
 * <p>
 * Collaborators<br />
 * Microkernel
 * </p>
 * 
 * @author jguibert
 */
public class InternalServer implements LifeCycle {
    private Microkernel kernel = null;
    private boolean unavailable;
    
    public Microkernel getKernel() {
        return kernel;
    }
    
    public void setKernel(Microkernel kernel) {
        this.kernel = kernel;
    }
    
    public void receiveRequest() {
        
    }
    
    public void executeService() {
        
    }
    
    public void activate() {
        
    }
    
    public void dispose() {
        // TODO Auto-generated method stub
        
    }
    
    public void initialize() {
        // TODO Auto-generated method stub
        
    }
    
    public boolean isUnavailable() {
        return unavailable;
    }
    
}
