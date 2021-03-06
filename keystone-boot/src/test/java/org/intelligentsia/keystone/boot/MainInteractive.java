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
package org.intelligentsia.keystone.boot;

import java.util.Scanner;

/**
 * Little Main class for testing by hand...
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class MainInteractive {

	public static void main(final String[] args) throws Exception {
		// Console.setLogFile(new File("./test.log"));
		System.out.println("In Main: ");

		final Scanner sc = new Scanner(System.in);

		if (sc.nextInt() == 0) {
			System.out.println("Call autoRestart");
			try {
				Restarter.autoRestart(new Runnable() {

					public void run() {
						System.out.println("Run before auto restart");
					}
				});
			} catch (final IllegalStateException e) {
				System.out.println(e.getMessage());
			}
			System.out.println("Call restartWith");
			try {
				Restarter.restartWith(new Runnable() {

					public void run() {
						System.out.println("Run before restart with");
					}
				}, "org.intelligentsia.keystone.boot.Main");
			} catch (final IllegalStateException e) {
				System.out.println(e.getMessage());
			}
		}
		sc.close();
		System.out.println("Exit in main");
	}

}
