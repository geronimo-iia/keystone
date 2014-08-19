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
package org.intelligentsia.keystone.sample;

import java.io.File;
import java.util.Map;
import java.util.Scanner;

import org.intelligentsia.keystone.boot.Arguments;
import org.intelligentsia.keystone.boot.Console;
import org.intelligentsia.keystone.boot.KeystoneException;

/**
 * KeystoneUpdaterSample class is our updater sample.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class KeystoneUpdaterSample {

    public static void main(final String[] args) throws Exception {
        final Map<String, String> arguments = Arguments.loadArguments(args);
        if (Arguments.getBooleanArgument(arguments, "BootStrap.restarted", Boolean.FALSE)) {
            Console.setLogFile(new File("./echo.log"));
            Console.INFO("Sample project Restarted with " + arguments);
            Console.INFO("Now exiting");
            return;
        }
        // some information in console
        Console.INFO("Coucou, Welcome on Sample project " + arguments);
        Console.INFO("Enter 0 to restart, 1 to clean, 2 to end");
        // wait key
        final Scanner scanner = new Scanner(System.in);
        int code = scanner.nextInt();
        if (code == 0) {
            Console.INFO("Waiting 5 secondes before restart");
            Thread.sleep(5000);
            Console.INFO("Restarting");
            throw new KeystoneException(KeystoneException.Operation.RESTART);
        } else if (code == 1) {
            Console.INFO("Waiting 5 secondes before clean");
            Thread.sleep(5000);
            throw new KeystoneException(KeystoneException.Operation.CLEAN);
        }
        return;
    }
}
