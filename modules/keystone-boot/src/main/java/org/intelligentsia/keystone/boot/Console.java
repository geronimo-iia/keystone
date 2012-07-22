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

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Console class offer an very simple way to write some log in system stream or
 * inside a specific file. I did not use Logger in JDK, in order to have minimal
 * dependencies and extra configuration. This class is not for log framework
 * replacement, so just uses this in BootStrap.
 * 
 * By default, this class 'log' in INFO and WARNING mode, VERBOSE is deactivated
 * and should be only used for debugging.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class Console {

	private static Boolean info = Boolean.TRUE;
	private static Boolean verbose = Boolean.FALSE;

	public static void setInfo(final Boolean info) {
		Console.info = info;
	}

	public static void setVerbose(final Boolean verbose) {
		Console.verbose = verbose;
	}

	/**
	 * Set output in specified log file, append if ever exists.
	 * 
	 * @param logFile
	 *            file path.
	 */
	public static void setLogFile(final File logFile) {
		if (logFile != null) {
			// create parent tree
			if (!logFile.getParentFile().exists()) {
				if (!logFile.getParentFile().mkdirs()) {
					// NO redirect
					WARNING("Unable to create path for log file " + logFile.getPath());
					return;
				}
			}
			BufferedOutputStream log = null;
			try {
				log = new BufferedOutputStream(new FileOutputStream(logFile, true));
				System.setOut(new PrintStream(log, true));
				System.setErr(new PrintStream(log, true));
			} catch (final FileNotFoundException ex) {
			}
		}
	}

	/**
	 * Basic Utility to 'log' in VERBOSE mode.
	 * 
	 * @param message
	 */
	public static void VERBOSE(final String message) {
		Console.VERBOSE(message, null);
	}

	/**
	 * Basic Utility to 'log' in VERBOSE mode.
	 * 
	 * @param message
	 * @param throwable
	 */
	public static void VERBOSE(final String message, final Throwable throwable) {
		if (Console.verbose) {
			System.out.println(Console.getGmtDate(new Date()) + ", BootStrap: " + message);
			if (throwable != null) {
				throwable.printStackTrace(System.err);
			}
		}
	}

	/**
	 * Basic Utility to 'log' in WARNING mode.
	 * 
	 * @param message
	 * @param throwable
	 */
	public static void WARNING(final String message, final Throwable throwable) {
		System.err.println(Console.getGmtDate(new Date()) + ", BootStrap Warning: " + message);
		if (throwable != null) {
			throwable.printStackTrace(System.err);
		}
	}

	/**
	 * Basic Utility to 'log' in WARNING mode.
	 * 
	 * @param message
	 */
	public static void WARNING(final String message) {
		Console.WARNING(message, null);
	}

	/**
	 * Basic Utility to 'log' in INFO mode.
	 * 
	 * @param message
	 */
	public static void INFO(final String message) {
		if (Console.info) {
			System.out.println(Console.getGmtDate(new Date()) + ", BootStrap Info: " + message);
		}
	}

	/**
	 * @param when
	 * @return a formatted date which follow a 'GMT format' (we adds millisecond
	 *         'S')
	 */
	private static String getGmtDate(final Date when) {
		final SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss S", Locale.US);
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		return (dateFormat.format(when) + " GMT");
	}
}
