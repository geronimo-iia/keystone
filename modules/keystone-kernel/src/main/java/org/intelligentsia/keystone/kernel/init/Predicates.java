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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.intelligentsia.keystone.kernel.Kernel;

/**
 * 
 * Predicates defines common {@link Predicate} implementation.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public enum Predicates {
	;

	/**
	 * @return a {@link Predicate} instance which evaluate is always
	 *         {@link Boolean#FALSE}.
	 */
	public static Predicate falsePredicate() {
		return new Predicate() {

			@Override
			public boolean evaluate(final Kernel kernel) {
				return false;
			}
		};
	}

	/**
	 * @return a {@link Predicate} instance which evaluate to
	 *         {@link Boolean#TRUE} when "quit" is type on {@link System#in}.
	 */
	public static Predicate quitPredicate() {
		return new Predicate() {

			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

			@Override
			public boolean evaluate(final Kernel kernel) {
				String line = "";
				try {
					line = bufferedReader.readLine();
				} catch (final IOException e) {
				}
				return (line.equals("quit"));
			}
		};
	}

}
