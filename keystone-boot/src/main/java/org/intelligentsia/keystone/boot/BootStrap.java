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

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLDecoder;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.jar.Attributes;

/**
 * BootStrap class.
 * <p>
 * DONT FORGET: LIMIT THIS CLASS TO BOOTSTRAP AND NOTHING MORE!! According on
 * this first sentence, i have added a system for extracting inner resources,
 * and option for restarting system... *_*
 * </p>
 * <p>
 * BASIC Structure: ${home}/lib all libraries contained in single jar file
 * (under META-INF/lib and/or /lib folder of this single jar)
 * </p>
 * <p>
 * if ${home} is not writable, system will use jvm.temp.dir\{} as home folder.
 * In this case, update is not supported yet.
 * </p>
 * 
 * <p>
 * Option that you could set on command line with a "--name=value" or in a
 * properties files:
 * </p>
 * <p>
 * Delegated main class
 * </p>
 * <ul>
 * <li>Main-Class=java main class, mandatory</li>
 * </ul>
 * 
 * <p>
 * Locale directory management:
 * </p>
 * <ul>
 * <li>BootStrap.explodeDirectory=if specified use this directory to explode
 * innerjar libraries. Use home per default.</li>
 * <li>BootStrap.cleanUpLib=true|false (default true) clean up local 'lib' file
 * system on startup</li>
 * <li>BootStrap.cleanUpBeforeShutdown=true|false (default false) clean up all
 * file when system shutdown.</li>
 * </ul>
 * 
 * <p>
 * Log information
 * </p>
 * <ul>
 * <li>BootStrap.verbose=true|false (default false) activate 'verbose' mode</li>
 * <li>BootStrap.info=true|false (default true) activate 'info' mode</li>
 * <li>BootStrap.logFile=log file of bootstrap (default is none)</li>
 * </ul>
 * 
 * <p>
 * Class Path management
 * </p>
 * <ul>
 * <li>BootStrap.extraLibrariesFolderPath = folder path of external libraries ,
 * in order to include them on classpath. Even if keystone is used to pack all
 * dependencies in a single archive, many project needs adding extra
 * dependencies on their classpath as specific database driver etc...</li>
 * <li>BootStrap.includeJavaHomeLib=true|false (default false) include java home
 * libraries</li>
 * <li>BootStrap.includeSystemClassLoader=true|false (default true) include
 * system class loader</li>
 * </ul>
 * <p>
 * Configuration priority (hight to less):
 * </p>
 * <ul>
 * <li>from command line with --....</li>
 * <li>from file:keystone.properties</li>
 * <li>from classpath:keystone.properties</li>
 * <li>from classpath:META-INF/keystone.properties</li>
 * </ul>
 * <p>
 * BootStrap set two system properties:
 * </p>
 * <ul>
 * <li>BootStrap.location=full path of the booted jar</li>
 * <li>BootStrap.home= home directory</li>
 * <li>BootStrap.keystone.version=keystone version used/li>
 * <li>BootStrap.project.version=inner project version</li>
 * </ul>
 * 
 * <p>
 * Delegated main class can manage restart of system by throw a KeystonException
 * with "restart" or "clean" operation string".
 * </p>
 * <p>
 * Main class should be in this case:
 * </p>
 * <code>
 * public static int main(final String[] args) {
 * 		throw new KeystoneException(KeystoneException.Operation.RESTART);
 * }
 * </code>
 * <p>
 * An update process can set system properties "BootStrap.location" with the new
 * jar and restart on it
 * </p>
 * 
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public final class BootStrap {

	/**
	 * HOME_VAR:String
	 */
	private static final String HOME_VAR = "#[home]";

	/**
	 * Main methods.
	 * 
	 * @param args
	 * @throws IOException
	 *             if something is wrong when reading properties files.
	 */
	public static void main(final String[] args) throws IOException {
		// load startup properties
		final Map<String, String> arguments = Arguments.loadArguments(args, "keystone.properties");
		// initialize data member according arguments
		final Boolean consoleLocationNeedHome = BootStrap.initializeConsoleLevel(arguments);
		if (!consoleLocationNeedHome) {
			// early initialization
			BootStrap.initializeConsoleLogLocation(arguments, null);
		}
		Console.VERBOSE("Arguments " + arguments);
		ExtractionManager.initialize(arguments);

		// code location
		final String location = BootStrap.getCodeSourceLocation();
		if ((location == null) || "".equals(location)) {
			Console.WARNING("Cannot Find Code Source location");
			return;
		}
		// HOME LOCATION
		final File home;
		try {
			home = BootStrap.getHomeDirectory(arguments, location);
		} catch (final IllegalStateException e) {
			Console.WARNING(e.getMessage(), e);
			return;
		}
		Console.INFO("Home Directory=" + home.getPath());
		// if log path is relative to home directory
		if (consoleLocationNeedHome) {
			BootStrap.initializeConsoleLogLocation(arguments, home);
		}

		// load main class
		final String mainClassName = Arguments.getStringArgument(arguments, Attributes.Name.MAIN_CLASS.toString(), null);
		// check main class
		if (mainClassName == null) {
			Console.WARNING("No Main-Class Found");
			return;
		}
		Console.INFO("Main-Class=" + mainClassName);

		// explode inner jar
		if (!ExtractionManager.explode(location, home)) {
			return;
		}

		// computing classPath
		List<URL> urls = null;
		try {
			urls = BootStrap.computeClassPath(home, arguments);
		} catch (final IllegalStateException ise) {
			return;
		}

		// Instantiate classloader
		final ClassLoader classloader = new URLClassLoader(urls.toArray(new URL[urls.size()]), Arguments.getBooleanArgument(arguments, "BootStrap.includeSystemClassLoader", Boolean.FALSE) ? ClassLoader.getSystemClassLoader() : ClassLoader
				.getSystemClassLoader().getParent()) {

			@Override
			protected String findLibrary(final String libname) {
				String libPath = JniLoader.findLibrary(new File(home, "lib"), libname);
				if (libPath == null) {
					//Console.VERBOSE("Library '" + libname + "' has not be found in embedded libraries folder.");
					libPath = JniLoader.findLibraryInClassPath(libname, new File(home, "lib-natives"));
					if (libPath == null) {
						//Console.VERBOSE("Library '" + libname + "' has not be found in classpath.");
						return super.findLibrary(libname);
					}
				}
				return libPath;
			}
		};

		// Set environment
		System.getProperties().put("BootStrap.location", location);
		System.getProperties().put("BootStrap.home", home.getPath());
		System.getProperties().put("BootStrap.keystone.version", Arguments.getStringArgument(arguments, "BootStrap.keystone.version", "unknown"));
		System.getProperties().put("BootStrap.project.version", Arguments.getStringArgument(arguments, "BootStrap.project.version", "unknown"));

		// invoke main method, with original argument
		BootStrap.invokeMain(classloader, mainClassName, args, home);

		if (Arguments.getBooleanArgument(arguments, "BootStrap.cleanUpBeforeShutdown", Boolean.FALSE)) {
			ExtractionManager.cleanUp(home, Boolean.TRUE);
		}
		// stop
		Console.INFO("Exit");
		// do not do this, because this could cause termination of threaded
		// application (like swing,..)
		// System.exit(0);
	}

	/**
	 * Initialize console level.
	 * 
	 * @param arguments
	 * @return true if log initialization need home directory.
	 */
	private static Boolean initializeConsoleLevel(final Map<String, String> arguments) {
		Boolean needHome = Boolean.FALSE;
		// Initialize console
		final String logfileName = Arguments.getStringArgument(arguments, "BootStrap.logFile", null);
		if (logfileName != null) {
			needHome = logfileName.indexOf(BootStrap.HOME_VAR) >= 0;
		}
		Console.setInfo(Arguments.getBooleanArgument(arguments, "BootStrap.info", Boolean.TRUE));
		Console.setVerbose(Arguments.getBooleanArgument(arguments, "BootStrap.verbose", Boolean.FALSE));
		return needHome;
	}

	/**
	 * Initialize console log file.
	 * 
	 * @param arguments
	 * @param home
	 */
	private static void initializeConsoleLogLocation(final Map<String, String> arguments, final File home) {
		final String logfileName = Arguments.getStringArgument(arguments, "BootStrap.logFile", null);
		if (logfileName != null) {
			File log = null;
			final int index = logfileName.indexOf(BootStrap.HOME_VAR);
			if (index >= 0) {
				log = new File(home, logfileName.substring(BootStrap.HOME_VAR.length()));
			} else {
				log = new File(logfileName);
			}
			Console.setLogFile(log);
		}
	}

	/**
	 * Compute classpath.
	 * 
	 * @param home
	 * @param arguments
	 * @return a list of url to include in class path.
	 * @throws IllegalStateException
	 *             if an error occurs and should halt boot process.
	 */
	private static List<URL> computeClassPath(final File home, final Map<String, String> arguments) throws IllegalStateException {

		final Boolean includeJavaHomeLib = Arguments.getBooleanArgument(arguments, "BootStrap.includeJavaHomeLib", Boolean.TRUE);
		final String javaHome = System.getProperty("java.home", null);

		final List<URL> urls = new ArrayList<URL>();
		// add java home
		if (includeJavaHomeLib) {
			Console.VERBOSE("Including Java Home Libraries");
			try {
				if (javaHome != null) {
					urls.addAll(BootStrap.computeFromDirectory(new File(javaHome, "lib")));
				} else {
					Console.WARNING("Java Home property is not set");
				}
			} catch (final MalformedURLException ex) {
				Console.WARNING("error when including JavaHomeLib :" + ex.getMessage());
				throw new IllegalStateException("error when including JavaHomeLib", ex);
			}
		}
		// add ${HOME}/lib
		try {
			urls.addAll(BootStrap.computeFromDirectory(new File(home, "lib")));
		} catch (final MalformedURLException ex) {
			Console.WARNING("error when including './lib' :" + ex.getMessage());
			throw new IllegalStateException("error when including './lib'", ex);
		}

		// adding external libraries Folder
		final String extraLibrariesFolderPath = Arguments.getStringArgument(arguments, "BootStrap.extraLibrariesFolderPath", null);
		if (extraLibrariesFolderPath != null) {
			try {
				File extra = new File(extraLibrariesFolderPath);
				if (!extra.exists()) {
					extra = new File(home, extraLibrariesFolderPath);
				}
				urls.addAll(BootStrap.computeFromDirectory(extra));
			} catch (final MalformedURLException e) {
				Console.WARNING("error when including '" + extraLibrariesFolderPath + "' :" + e.getMessage());
				throw new IllegalStateException("error when including '" + extraLibrariesFolderPath + "'", e);
			}
		}

		Console.VERBOSE("ClassPath: " + urls.toString());
		return urls;
	}

	/**
	 * Invoke main class inside the specific class loader.
	 * 
	 * @param classloader
	 *            class loader to use
	 * @param mainClassName
	 *            main class name
	 * @param arguments
	 *            arguments
	 * @param home
	 *            home directory
	 * @throws SecurityException
	 */
	private static void invokeMain(final ClassLoader classloader, final String mainClassName, final String[] arguments, final File home) throws SecurityException {
		// load main class
		Class<?> mainClass = null;
		try {
			/**
			 * Well-behaved Java packages work relative to the context
			 * classloader others don't (like commons-logging). Set the context
			 * classloader in case any classloaders delegate to it. Otherwise it
			 * would default to the sun.misc.Launcher$AppClassLoader which is
			 * used to launch the jar application, and attempts to load through
			 * it would fail if that code is encapsulated inside the one-jar.
			 */
			Thread.currentThread().setContextClassLoader(classloader);
			mainClass = classloader.loadClass(mainClassName);
		} catch (final ClassNotFoundException ex) {
			Console.WARNING("class '" + mainClassName + "' not found: " + ex.getMessage());
		}
		if (mainClass != null) {
			Method main = null;
			try {
				// args.getClass()
				main = mainClass.getMethod("main", new Class<?>[] { String[].class });
			} catch (final NoSuchMethodException ex) {
				Console.WARNING("class '" + mainClassName + "' did not have a method 'main': " + ex.getMessage());
			} catch (final SecurityException ex) {
				Console.WARNING("class '" + mainClassName + "', can not access to 'main' method  : " + ex.getMessage());
			}
			if (main != null) {
				main.setAccessible(true);
				Console.VERBOSE("Entering main");
				try {
					main.invoke(null, new Object[] { arguments });
					Console.VERBOSE("Exiting main");
				} catch (final KeystoneException e) {
					// here we can add a reboot option
					switch (e.getOperation()) {
					case CLEAN:
						Console.VERBOSE("Cleanning before shutdown");
						ExtractionManager.cleanUp(home);
						break;
					case RESTART:
						Console.VERBOSE("Restarting");
						BootStrap.restart(new Runnable() {
							public void run() {
								ExtractionManager.cleanUp(home, Boolean.TRUE);
							}
						});
						break;
					case NONE:
						Console.WARNING("KeystoneException without operation: " + e.getMessage(), e);
						break;
					default:
						Console.WARNING("KeystoneException operation not supported '" + e.getOperation() + "': " + e.getMessage(), e);
						break;
					}
				} catch (final IllegalAccessException ex) {
					// should not occurs
					Console.WARNING("class '" + mainClassName + "', can not access to 'main' method  : " + ex.getMessage(), ex);
				} catch (final IllegalArgumentException ex) {
					Console.WARNING("class '" + mainClassName + "', problem with argument of 'main' method  : " + ex.getMessage(), ex);
				} catch (final InvocationTargetException ex) {
					Console.WARNING("class '" + mainClassName + "', exception occur when invoking 'main' method  : " + ex.getMessage(), ex);
				} catch (final Throwable ex) {
					Console.WARNING("class '" + mainClassName + "', exception occur : " + ex.getMessage(), ex);
				}
			} else {
				Console.WARNING("The main() method in class '" + mainClassName + "' not found.");
			}
		}
	}

	/**
	 * Compute a list of jar file found in specified path in a recursive way.
	 * 
	 * @param path
	 *            directory path
	 * @return a list of URL found in specified directory.
	 * @throws MalformedURLException
	 */
	private static List<URL> computeFromDirectory(final File directory) throws MalformedURLException {
		final List<URL> urls = new ArrayList<URL>();
		if (directory.exists() && directory.isDirectory()) {
			for (final File child : directory.listFiles()) {
				if (child.isDirectory()) {
					urls.addAll(BootStrap.computeFromDirectory(new File(child.getPath())));
				} else if (child.getName().endsWith(".jar")) {
					urls.add(child.toURI().toURL());
				}
			}
		}
		return urls;
	}

	/**
	 * @return the code source location.
	 */
	private static String getCodeSourceLocation() {
		String root = null;
		final ProtectionDomain protectionDomain = BootStrap.class.getProtectionDomain();
		final CodeSource codeSource = protectionDomain.getCodeSource();
		final URL rootUrl = codeSource.getLocation();
		try {
			root = URLDecoder.decode(rootUrl.getFile(), "UTF-8");
		} catch (final UnsupportedEncodingException ex) {
			Console.WARNING("Unable to decode '" + rootUrl.getFile() + "' " + ex.getMessage(), ex);
		}
		return root;
	}

	/**
	 * Get home directory.
	 * 
	 * @param arguments
	 *            arguments
	 * @param location
	 *            code location
	 * @return home directory.
	 * @throws IllegalStateException
	 *             if error occurs
	 */
	private static File getHomeDirectory(final Map<String, String> arguments, final String location) throws IllegalStateException {
		// return explodeDirectory or location.getParent() or null
		final String path = Arguments.getStringArgument(arguments, "BootStrap.explodeDirectory", location != null ? new File(location).getParentFile().getAbsolutePath() : null);
		try {
			File home = path != null ? new File(path) : ExtractionManager.createTempDir();
			// check write access
			if (!home.canWrite()) {
				Console.WARNING("Home Directory is not Writable, using a temp directory.");
				try {
					home = ExtractionManager.createTempDir();
				} catch (final IOException e) {
					throw new IllegalStateException("Unable to create home temp directory.", e);
				}
			}
			// check if home is a directory
			if (!home.isDirectory()) {
				throw new IllegalStateException(home.getPath() + " is not a directory");
			}
			return home;
		} catch (final IOException e) {
			throw new IllegalStateException("Unable to create home temp directory.", e);
		}
	}

	/**
	 * Restart all system using value of system property "BootStrap.location".
	 * This property can be updated by delegated Main class, after an update for
	 * example.
	 * 
	 * @param runnable
	 *            runnable class before restarting.
	 */
	public static void restart(final Runnable runnable) {
		Console.VERBOSE("(Restart with '" + System.getProperty("BootStrap.location") + "'");
		Restarter.restartWith(runnable, "-jar ", System.getProperty("BootStrap.location"));
	}

}
