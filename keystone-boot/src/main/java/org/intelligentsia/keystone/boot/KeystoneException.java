/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
/**
 *
 */
package org.intelligentsia.keystone.boot;

/**
 * KeystoneException used to drive which process is done after returning main.
 *
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 *
 */
public class KeystoneException extends RuntimeException {

    /**
     * serialVersionUID:long
     */
    private static final long serialVersionUID = 5526337594737942947L;
    private final Operation operation;

    /**
     * Build a new instance of KeystoneException.
     */
    public KeystoneException() {
        super();
        operation = Operation.NONE;
    }

    /**
     * Build a new instance of KeystoneException.
     *
     * @param operation
     */
    public KeystoneException(final Operation operation) {
        super();
        this.operation = operation;
    }

    /**
     * Build a new instance of KeystoneException.
     *
     * @param message
     * @param cause
     */
    public KeystoneException(final Operation operation, final String message, final Throwable cause) {
        super(message, cause);
        this.operation = operation;
    }

    /**
     * Build a new instance of KeystoneException.
     *
     * @param message
     */
    public KeystoneException(final Operation operation, final String message) {
        super(message);
        this.operation = operation;
    }

    /**
     * Build a new instance of KeystoneException.
     *
     * @param cause
     */
    public KeystoneException(final Operation operation, final Throwable cause) {
        super(cause);
        this.operation = operation;
    }

    /**
     * @return the operation
     */
    public final Operation getOperation() {
        return operation;
    }

    /**
     * Operation that can be processed by bootstrap.
     *
     * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
     *
     */
    public enum Operation {
        NONE, CLEAN, RESTART
    }

}
