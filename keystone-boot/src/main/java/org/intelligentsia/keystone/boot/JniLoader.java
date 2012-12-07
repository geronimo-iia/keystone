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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;

/**
 * JniLoader.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class JniLoader {

	private static Map<String, File> nativeLibraries = new ConcurrentHashMap<String, File>(5);

	/**
	 * Returns the absolute path name of a native library. The VM invokes this
	 * method to locate the native libraries that belong to classes loaded with
	 * this class loader. If this method returns <tt>null</tt>, the VM searches
	 * the library along the path specified as the "<tt>java.library.path</tt>"
	 * property. </p>
	 * 
	 * @param directory
	 *            directory location of libraries
	 * @param libname
	 *            The library name
	 * 
	 * @return The absolute path of the native library
	 */
	public static String findLibrary(final File directory, final String libname) {
		final String systemLibName = System.mapLibraryName(libname);
		File lib = new File(directory, systemLibName);
		if (!lib.exists()) {
			Console.VERBOSE("Library '" + libname + "' has not be found in embedded folder ('" + lib.getAbsolutePath() + "')");
			lib = findLibraryInClassPath(libname, systemLibName);
			if (lib == null || !lib.exists()) {
				Console.WARNING("Library '" + libname + "' has not be found.");
				return null;
			}
		}
		Console.VERBOSE("Library '" + libname + "' found ('" + lib.getAbsolutePath() + "')");
		return lib.getAbsolutePath();
	}

	/**
	 * If libname exists in classpath, copy this in a temp file.
	 * 
	 * @param libname
	 *            The library name
	 * @param systemLibName
	 *            The library system name
	 * @return a {@link File} or null.
	 */
	private static File findLibraryInClassPath(final String libname, final String systemLibName) {
		File result = nativeLibraries.get(libname);
		if (result == null) {
			// try to find in current class loader
			InputStream inputStream = null;
			OutputStream outputStream = null;
			try {
				inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(systemLibName);
				if (inputStream != null) {
					Console.VERBOSE("Copy native library : " + systemLibName);
					// copy to temp file
					result = File.createTempFile("keystone", libname);
					outputStream = new FileOutputStream(result);
					final byte[] buf = new byte[4096 * 4];
					int len = 0;
					while ((len = inputStream.read(buf)) > 0) {
						outputStream.write(buf, 0, len);
					}
				}
			} catch (IOException e) {
				Console.VERBOSE("Error when copy native library : " + systemLibName, e);
				result = null;
			} finally {
				ExtractionManager.close(inputStream);
				ExtractionManager.close(outputStream);
			}
			if (result != null) {
				nativeLibraries.put(libname, result);
			}
		}
		return result;
	}

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
		final String libPath = findLibrary(directory, libname);
		if (libPath == null) {
			Console.WARNING("Library '" + libname + "' has not be found ('" + libPath + "')");
			return Boolean.FALSE;
		}
		try {
			Runtime.getRuntime().load(libPath);
		} catch (final UnsatisfiedLinkError e) {
			Console.WARNING("Library '" + libname + "' not found ('" + libPath + "') or her dependencies not ever loaded", e);
			return Boolean.FALSE;
		} catch (final Throwable e) {
			Console.WARNING("Loading Library '" + libname + "'", e);
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}
}
