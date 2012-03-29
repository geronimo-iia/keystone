/**
 * 
 */
package org.intelligentsia.keystone.boot;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipException;

/**
 * ExtractionManager class group all mechanism for extract and clean up system.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class ExtractionManager {

	private static Boolean cleanUpLib = Boolean.TRUE;

	/**
	 * Initialize clean up directive.
	 * 
	 * @param arguments
	 */
	public static void initialize(final Map<String, String> arguments) {
		ExtractionManager.cleanUpLib = Arguments.getBooleanArgument(arguments, "BootStrap.cleanUpLib", Boolean.TRUE);
	}

	/**
	 * Explode.
	 * 
	 * @param location
	 *            inner jar location
	 * @param home
	 *            home directory to explode
	 * @return false if an error occur, true otherwise.
	 */
	public static boolean explode(final String location, final File home) {
		ExtractionManager.cleanUp(home);
		// Explode inner jar
		try {
			if (location != null) {
				// WARN here it's "lib/" for all OS (we're looking inside a jar,
				// not on file system).
				ExtractionManager.explode(new File(home, "lib"), "lib/", location, Boolean.FALSE);
			} else {
				Console.WARNING("Error when exploding : Location is null");
			}
		} catch (final Throwable t) {
			Console.WARNING("Error when exploding : " + t.getMessage(), t);
			return false;
		}
		return true;
	}

	/**
	 * Utility to clean up home.
	 * 
	 * @param home
	 *            directory to clean
	 */
	public static void cleanUp(final File home) {
		cleanUp(home, Boolean.FALSE);
	}

	/**
	 * Utility to clean up home.
	 * 
	 * @param home
	 *            directory to clean
	 * @param force
	 *            if true try to clean all sub directory.
	 */
	public static void cleanUp(File home, Boolean force) {
		if (ExtractionManager.cleanUpLib || force) {
			Console.VERBOSE("Clean up lib");
			File lib = new File(home, "lib");
			if (lib.exists()) {
				if (!ExtractionManager.delete(lib)) {
					Console.WARNING("Unable to Clean up lib");
				}
			}
		}
	}

	/**
	 * Create a new temporary directory. Use something like
	 * {@link #recursiveDelete(File)} to clean this directory up since it isn't
	 * deleted automatically
	 * 
	 * @return the new directory
	 * @throws IOException
	 *             if there is an error creating the temporary directory
	 */
	public static File createTempDir() throws IOException {
		final File sysTempDir = new File(System.getProperty("java.io.tmpdir"));
		File newTempDir;
		final int maxAttempts = 9;
		int attemptCount = 0;
		do {
			attemptCount++;
			if (attemptCount > maxAttempts) {
				throw new IOException("The highly improbable has occurred! Failed to create a unique temporary directory after " + maxAttempts + " attempts.");
			}
			String dirName = UUID.randomUUID().toString();
			newTempDir = new File(sysTempDir, dirName);
		} while (newTempDir.exists());

		if (newTempDir.mkdirs()) {
			return newTempDir;
		} else {
			throw new IOException("Failed to create temp dir named " + newTempDir.getAbsolutePath());
		}
	}

	/**
	 * Utility to close in silence.
	 * 
	 * @param closeable
	 *            instance to close
	 */
	public static void close(final Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (final IOException exception) {
				Console.VERBOSE("Closing Error", exception);
			}
		}
	}

	/**
	 * Utility to delete file (directory or single file)
	 * 
	 * @param from
	 *            Deletes the file or directory denoted by this abstract
	 *            pathname.
	 * @return true if and only if the file or directory is successfully
	 *         deleted; false otherwise
	 */
	public static boolean delete(final File from) {
		if ((from != null) && from.exists()) {
			if (from.isDirectory()) {
				for (final File child : from.listFiles()) {
					ExtractionManager.delete(child);
				}
			}
			return from.delete();
		}
		return false;
	}

	/**
	 * Explode specified jar into home.
	 * 
	 * @param home
	 * @param fromPath
	 * @param jarPath
	 * @throws IOException
	 * @throws ZipException
	 */
	private static void explode(final File home, final String fromPath, final String jarPath, final Boolean override) throws IOException, ZipException {
		// check if jarPath if a jar file. With local test case, it's a folder.
		File check = new File(jarPath);
		if (check.exists() && !check.isDirectory()) {
			final JarFile jar = new JarFile(URLDecoder.decode(jarPath, "UTF-8"));
			for (final JarEntry entry : Collections.list(jar.entries())) {
				final String name = entry.getName();
				// Console.VERBOSE("Looking for: " + name);
				if (name.startsWith(fromPath)) {
					Console.VERBOSE("Exploding: " + name);
					final String targetName = name.substring(fromPath.length()).trim();
					if (!"".equals(targetName)) // not empty
					{
						final File localFile = new File(home, targetName);
						if (override && localFile.exists()) {
							localFile.delete();
						}
						if (!localFile.exists()) {
							ExtractionManager.explodeEntry(targetName, localFile, jar, name);
						}
					}
				}
			}
		}
	}

	/**
	 * ExtractionManager a specific entry.
	 * 
	 * @param targetName
	 * @param localFile
	 * @param jar
	 * @param name
	 * @throws IOException
	 */
	private static void explodeEntry(final String targetName, final File localFile, final JarFile jar, final String name) throws IOException {
		// create a directory
		if (targetName.endsWith("/")) {
			if (!localFile.mkdirs()) {
				throw new IOException("Unable to create directory '" + localFile.getPath() + "'");
			}
		} else {
			// create local file if not exits
			if (!localFile.exists()) {
				final File parent = localFile.getParentFile();
				if ((parent != null) && !parent.exists()) {
					if (!parent.mkdirs()) {
						throw new IOException("Parent File Of '" + localFile.getPath() + "' could not be created");
					}
				}
				if (!localFile.createNewFile()) {
					throw new IOException("Unable to create file " + localFile.getPath());
				}
				InputStream inputStream = null;
				OutputStream outputStream = null;
				try {
					inputStream = jar.getInputStream(jar.getEntry(name));
					if (inputStream == null) {
						throw new IOException("Unable to access resource '" + name + "'");
					}
					outputStream = new FileOutputStream(localFile);
					final byte[] buf = new byte[4096 * 4];
					int len = 0;
					while ((len = inputStream.read(buf)) > 0) {
						outputStream.write(buf, 0, len);
					}
				} catch (final IOException exception) {
					Console.VERBOSE("Error when exploding Entry: " + name, exception);
					ExtractionManager.delete(localFile);
				} finally {
					ExtractionManager.close(inputStream);
					ExtractionManager.close(outputStream);
				}
			}
		}
	}

}