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

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipException;

/**
 * ExtractionManager class group all mechanism for extract and clean up system.
 *
 * Extract all things located in jar under:
 * <ul>
 * <li>META-INF/lib</li>
 * <li>lib</li>
 * </ul>
 * To {home}/lib folder
 *
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class ExtractionManager {

    private static Boolean cleanUpLib = Boolean.TRUE;
    private static Boolean hookAdded = Boolean.FALSE;

    /**
     * Initialize clean up directive.
     *
     * @param arguments
     */
    public static void initialize(final Map<String, String> arguments) {
        ExtractionManager.cleanUpLib = Arguments.getBooleanArgument(arguments, "BootStrap.cleanUpLib", Boolean.TRUE);
    }

    /**
     * Explode libraries and make a clean before if necessary.
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
                // the good place
                ExtractionManager.explode(new File(home, "lib"), "META-INF/lib/", location, Boolean.FALSE);
                // the old bad place
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
    public static void cleanUp(final File home, final Boolean force) {
        if (ExtractionManager.cleanUpLib || force) {
            Console.VERBOSE("Clean up lib");
            final File lib = new File(home, "lib");
            if (lib.exists()) {
                if (!ExtractionManager.delete(lib)) {
                    Console.WARNING("Unable to Clean up lib folder");
                }
            }
        }
    }

    /**
     * Add a shutdown hook to clean up at end.
     * @param home
     */
    public static void cleanUpHook(final File home) {
        if (!hookAdded) {
            Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                public void run() {
                    final File lib = new File(home, "lib");
                    if (lib.exists()) {
                        ExtractionManager.delete(lib);
                    }
                }
            }));
            hookAdded = Boolean.TRUE;
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
                    delete(child);
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
        final File check = new File(jarPath);
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
                            explodeEntry(targetName, localFile, jar, name);
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
                try {
                    inputStream = jar.getInputStream(jar.getEntry(name));
                    if (inputStream == null) {
                        throw new IOException("Unable to access resource '" + name + "'");
                    }
                    Files.copy(inputStream, Paths.get(localFile.toURI()), StandardCopyOption.REPLACE_EXISTING);
                } catch (final IOException exception) {
                    Console.VERBOSE("Error when exploding Entry: " + name, exception);
                    delete(localFile);
                } finally {
                    close(inputStream);
                }
            }
        }
    }

}