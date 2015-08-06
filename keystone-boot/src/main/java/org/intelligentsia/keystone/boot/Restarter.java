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
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Restarter Utility which can run on WINDOWS, LINUX and MAC operating system.
 *
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 *
 */
public class Restarter {
    /**
     * Sun property pointing the main class and its arguments. Might not be
     * defined on non Hotspot VM implementations.
     */
    private static final String SUN_JAVA_COMMAND = "sun.java.command";

    /**
     * Restart the current Java application
     *
     * @param runBeforeRestart
     *            some custom code to be run before restarting
     * @throws IllegalStateException
     *             if cannot find 'start command'
     */
    public static void autoRestart(final Runnable runBeforeRestart) throws IllegalStateException {
        if (System.getProperty(SUN_JAVA_COMMAND) != null) {
            final List<String> parameters = new ArrayList<String>();
            final String[] mainCommand = System.getProperty(SUN_JAVA_COMMAND).split(" ");
            // program main is a jar
            if (mainCommand[0].endsWith(".jar")) {
                // if it's a jar, add -jar mainJar
                parameters.add("-jar " + new File(mainCommand[0]).getPath());
            } else {
                // else it's a .class, add the classpath and mainClass
                parameters.add(mainCommand[0]);
            }
            // finally add program arguments
            for (int i = 1; i < mainCommand.length; i++) {
                parameters.add(mainCommand[i]);
            }
            restartWith(runBeforeRestart, parameters.toArray(new String[0]));
            // never reach
            return;
        }
        throw new IllegalStateException("Unable to Autorestart");
    }

    /**
     * Restart the specified Java application
     *
     * @param runBeforeRestart
     *            some custom code to be run before restarting
     * @param parameters
     *            parameter to restart application like "-jar application.jar"
     *
     * @throws IOException
     */
    public static void restartWith(final Runnable runBeforeRestart, final String... parameters) {
        // build command line
        final List<String> commands = new ArrayList<String>();
        commands.add(getJavaCommand());
        commands.addAll(getJVMArgument());
        commands.addAll(Arrays.asList(parameters));
        final StringBuilder builder = new StringBuilder();
        for (final String command : commands) {
            builder.append(command).append(' ');
        }
        final String cmd = builder.toString();

        // execute the command in a shutdown hook, to be sure that all the
        // resources have been disposed before restarting the application
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                try {
                    Runtime.getRuntime().exec(cmd);
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }
        });

        // execute some custom code before restarting
        if (runBeforeRestart != null) {
            runBeforeRestart.run();
        }
        // exit
        System.exit(0);
    }

    /**
     * @return the java command according operating system.
     */
    private static String getJavaCommand() {
        if (OSDetector.isMac()) {
            // is it working ?
            return "open";
        }
        // locate binary
        String path = System.getProperty("sun.boot.library.path");
        if ((path == null) || "".endsWith(path)) {
            path = System.getProperty("java.home") + File.separator + "bin";
        }
        // first check if 'javaw' is available, and java in second choice.
        final File root = new File(path);
        for (final String name : Arrays.asList((OSDetector.isLinux() ? "javaw" : "javaw.exe"), (OSDetector.isLinux() ? "java" : "java.exe"))) {
            final File java = new File(root, name);
            if (java.exists()) {
                return java.getAbsolutePath();
            }
        }
        // hope it can work
        return "java";
    }

    /**
     * Returns the input arguments passed to the Java virtual machine and set
     * some default.
     *
     * @return a list of String arguments.
     */
    private static List<String> getJVMArgument() {
        final List<String> lst = ManagementFactory.getRuntimeMXBean().getInputArguments();
        final ArrayList<String> jvmArguments = new ArrayList<String>();
        boolean xmxset = false;
        boolean xmsset = false;
        boolean maxPermSize = false;
        boolean useconc = false;
        boolean minheap = false;
        boolean maxheap = false;
        final boolean classpath = false;
        for (final String h : lst) {
            // if it's the agent argument : we ignore it otherwise the
            // address of the old application and the new one will be in
            // conflict
            if (!h.contains("-agentlib")) {
                if (h.contains("Xmx")) {
                    xmxset = true;
                    // check max memory
                    if (Runtime.getRuntime().maxMemory() < 533000000) {
                        jvmArguments.add("-Xmx512m");
                        continue;
                    }
                } else if (h.contains("Xms")) {
                    xmsset = true;
                } else if (h.contains("XX:+useconc")) {
                    useconc = true;
                } else if (h.contains("minheapfree")) {
                    minheap = true;
                } else if (h.contains("maxheapfree")) {
                    maxheap = true;
                } else if (h.contains("-XX:MaxPermSize")) {
                    maxPermSize = true;
                }
                jvmArguments.add(h);
            }
        }
        // set default value
        if (!xmsset) {
            jvmArguments.add("-Xms64m");
        }
        if (!xmxset) {
            jvmArguments.add("-Xmx512m");
        }
        if (!maxPermSize) {
            jvmArguments.add("-XX:MaxPermSize=128M");
        }
        if (!useconc) {
            jvmArguments.add("-XX:+UseConcMarkSweepGC");
        }
        if (!minheap) {
            jvmArguments.add("-XX:MinHeapFreeRatio=0");
        }
        if (!maxheap) {
            jvmArguments.add("-XX:MaxHeapFreeRatio=0");
        }
        if (!classpath) {
            final String cp = System.getProperty("java.class.path");
            if ((cp != null) && !"".equals(cp)) {
                jvmArguments.add("-cp \"" + System.getProperty("java.class.path") + "\"");
            }
        }
        return jvmArguments;
    }

}
