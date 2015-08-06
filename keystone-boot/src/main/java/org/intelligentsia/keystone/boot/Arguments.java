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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Arguments class is an utility to deal with Arguments line stuff.
 *
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class Arguments {

    /**
     * Utility to load arguments from command line, and extract any option
     * starting with '--'. - load --name as <name, Boolean.TRUE> - load
     * --name=value as <name, value> - other arguments are set as <name, name>
     *
     * @param argv
     * @return a decoded map instance
     */
    public static Map<String, String> loadArguments(final String[] argv) {
        final Map<String, String> result = new HashMap<>();
        if (argv != null) {
            for (int i = 0; i < argv.length; i++) {
                final String option = argv[i];
                if (option.startsWith("--")) {
                    final int equalPos = option.indexOf('=');
                    final String paramName = option.substring(2, equalPos == -1 ? option.length() : equalPos);
                    if (equalPos != -1) {
                        result.put(paramName, option.substring(equalPos + 1));
                    } else {
                        result.put(paramName, Boolean.TRUE.toString());
                    }
                } else {
                    result.put(option, option);
                }
            }
        }
        return result;
    }

    /**
     * Utility to convert a map of argument as argument from command line,
     * according this pattern '--name=value'.
     *
     * @param arguments
     * @return an array of string
     */
    public static String[] argumentToArray(final Map<String, String> arguments) {
        final List<String> result = new ArrayList<>();
        for (final Map.Entry<String, String> entry : arguments.entrySet()) {
            if (entry.getKey().equals(entry.getValue())) {
                result.add(entry.getKey());
            } else {
                if (entry.getValue().equals(Boolean.TRUE.toString())) {
                    result.add("--" + entry.getKey());
                } else {
                    result.add("--" + entry.getKey() + "=" + entry.getValue());
                }
            }
        }
        return result.toArray(new String[]{});
    }

    /**
     * Utility to load arguments from command line, and extract any option
     * starting with '--'. - load --name as <name, Boolean.TRUE> - load
     * --name=value as <name, value> - other arguments are set as <name, name>
     * After, load properties found in several location of specified property
     * name instance: - file:{name} - classpath:{name}, -
     * classpath:META-INF/{name} with this priority.
     *
     * @param argv
     * @param name
     *            propertie base name
     * @return a decoded map instance
     * @throws IOException
     *             if an error occurred when reading from one of properties
     *             file.
     */
    public static Map<String, String> loadArguments(final String[] argv, final String name) throws IOException {
        final Map<String, String> arguments = Arguments.loadArguments(argv);
        Arguments.loadPropsFrom("file:" + name, arguments);
        Arguments.loadPropsFrom("META-INF/" + name, arguments);
        Arguments.loadPropsFrom("/" + name, arguments);
        Arguments.loadPropsFrom(name, arguments);
        return arguments;
    }

    /**
     * @param args
     * @param name
     * @param defaultTrue
     * @return a boolean from specified name, or default value. Accepted value
     *         for true are 'true' and 'yes' (case ignored).
     */
    public static Boolean getBooleanArgument(final Map<String, String> args, final String name, final Boolean defaultTrue) {
        final String value = args.get(name);
        if ((defaultTrue != null) && defaultTrue) {
            return (value == null) || (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("yes"));
        } else {
            return (value != null) && (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("yes"));
        }
    }

    /**
     * @param args
     * @param name
     * @param defaultValue
     * @return a string value from specified name or default if value of name is
     *         null
     */
    public static String getStringArgument(final Map<String, String> args, final String name, final String defaultValue) {
        return (args.get(name) == null ? defaultValue : args.get(name));
    }

    /**
     * @param args
     * @param name
     * @param defaultValue
     * @return an integer value from specified name or default value if null or
     *         not a number.
     */
    public static Integer getIntegerArgument(final Map<String, String> args, final String name, final Integer defaultValue) {
        try {
            return Integer.parseInt(Arguments.getStringArgument(args, name, Integer.toString(defaultValue)));
        } catch (final NumberFormatException exception) {
            return defaultValue;
        }
    }

    /**
     * Load propeties from specified stream and set them if the key is not ever
     * in args.
     *
     * @param name
     *            name to load
     * @param args
     *            arguments
     * @throws IOException
     *             if an error occurred when reading from resource name.
     */
    private static void loadPropsFrom(final String name, final Map<String, String> args) throws IOException {
        InputStream is = null;
        try {
            is = BootStrap.class.getClassLoader().getResourceAsStream(name);
            if (is != null) {
                final Properties properties = new Properties();
                properties.load(is);
                for (final Iterator<Object> i = properties.keySet().iterator(); i.hasNext(); ) {
                    final String key = ((String) i.next()).trim();
                    if (!args.containsKey(key)) {
                        args.put(key, properties.getProperty(key));
                    }
                }
                properties.clear();
            }
        } finally {
            // we did not used (BootStrap/delete ) for separate contract
            if (is != null) {
                try {
                    is.close();
                } catch (final IOException exception) {
                    Console.VERBOSE("Closing Error", exception);
                }
            }
        }
    }

}
