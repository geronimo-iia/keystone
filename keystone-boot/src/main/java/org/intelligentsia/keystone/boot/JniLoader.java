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
package org.intelligentsia.keystone.boot;

import java.io.File;
import java.util.StringTokenizer;

/**
 * JniLoader.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class JniLoader {

	/**
	 * Load specified libraries.
	 * 
	 * @param directory
	 *            directory location of libraries
	 * @param libnames
	 *            libraries name comma separated
	 * @return true if all libraries has been successfully loaded.
	 */
	public static Boolean loadLibraries(final File directory, final String libnames) {
		final StringTokenizer tokenizer = new StringTokenizer(libnames, ",");
		Boolean result = Boolean.TRUE;
		while (tokenizer.hasMoreTokens() && result) {
			result = loadLibrary(directory, tokenizer.nextToken());
		}
		return result;
	}

	/**
	 * Load specified library.
	 * 
	 * @param directory
	 *            directory location of libraries
	 * @param libname
	 *            library name
	 * @return true if library has been successfully loaded.
	 */
	public static Boolean loadLibrary(final File directory, final String libname) {
		final String systemLibName = System.mapLibraryName(libname);
		final File lib = new File(directory, systemLibName);
		if (!lib.exists()) {
			Console.WARNING("Library '" + libname + "' has not be found ('" + lib.getAbsolutePath() + "')");
			return Boolean.FALSE;
		}
		try {
			Runtime.getRuntime().load(lib.getAbsolutePath());
		} catch (final UnsatisfiedLinkError e) {
			Console.WARNING("Library '" + libname + "' not found ('" + lib.getAbsolutePath() + "') or her dependencies not ever loaded", e);
			return Boolean.FALSE;
		} catch (final Throwable e) {
			Console.WARNING("Loading Library '" + libname + "'", e);
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}
}
