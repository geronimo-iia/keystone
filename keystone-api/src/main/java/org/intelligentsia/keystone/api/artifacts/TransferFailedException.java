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
/**
 * 
 */
package org.intelligentsia.keystone.api.artifacts;

/**
 * Transfer Failed Exception.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class TransferFailedException extends KeystoneRuntimeException {

	private static final long serialVersionUID = -2783547055285702711L;

	/**
	 * Build a new instance of <code>TransferFailedException</code>
	 */
	public TransferFailedException() {
		super();
	}

	/**
	 * Build a new instance of <code>TransferFailedException</code>
	 * 
	 * @param message
	 * @param cause
	 */
	public TransferFailedException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Build a new instance of <code>TransferFailedException</code>
	 * 
	 * @param message
	 */
	public TransferFailedException(final String message) {
		super(message);
	}

	/**
	 * Build a new instance of <code>TransferFailedException</code>
	 * 
	 * @param cause
	 */
	public TransferFailedException(final Throwable cause) {
		super(cause);
	}

}
