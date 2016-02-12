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

import java.io.IOException;
import java.util.Map;

import org.junit.Assert;

import org.junit.Test;

/**
 * ArgumentsTest.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class ArgumentsTest {

	private static String[] STRINGS = new String[] { "--name", "--name2=false", "--name3=yes", "--titi=tutu", "single", "-dummy", "-other=value", "--i=123" };

	@Test
	public void testLoadArgument() {
		final Map<String, String> arguments = Arguments.loadArguments(STRINGS);
		Assert.assertNotNull(arguments);
	}

	@Test
	public void loadFromProperties() throws IOException {
		final Map<String, String> arguments = Arguments.loadArguments(STRINGS, "keystone.properties");
		Assert.assertNotNull("Expected 'Main-Class' key", arguments.get("Main-Class"));
		Assert.assertEquals("Expected 'tutu'", arguments.get("Main-Class"), "org.intelligentsia.keystone.boot.Main");
	}

	@Test
	public void testLoadArgumentSingleValue() {
		final Map<String, String> arguments = Arguments.loadArguments(STRINGS);
		Assert.assertNotNull("Expected 'name' key", arguments.get("name"));
		Assert.assertTrue("Expected 'false'", Boolean.parseBoolean(arguments.get("name")));
	}

	@Test
	public void testLoadArgumentKeyValue() {
		final Map<String, String> arguments = Arguments.loadArguments(STRINGS);
		Assert.assertNotNull("Expected 'name' titi", arguments.get("titi"));
		Assert.assertEquals("Expected 'tutu'", arguments.get("titi"), "tutu");
	}

	@Test
	public void testLoadArgumentOther() {
		final Map<String, String> arguments = Arguments.loadArguments(STRINGS);

		Assert.assertNotNull("Expected 'single' key", arguments.get("single"));
		Assert.assertEquals("Expected 'single'", arguments.get("single"), "single");

		Assert.assertNotNull("Expected '-dummy' key", arguments.get("-dummy"));
		Assert.assertNull("Expected no key 'dummy' ", arguments.get("dummy"));
		Assert.assertEquals("Expected '-dummy'", arguments.get("-dummy"), "-dummy");

		Assert.assertNotNull("Expected '-other=value' key", arguments.get("-other=value"));
		Assert.assertNull("Expected no key 'other' ", arguments.get("other"));
		Assert.assertEquals("Expected '-other=value'", arguments.get("-other=value"), "-other=value");

	}

	@Test
	public void testArgumentToArray() {
		final Map<String, String> arguments = Arguments.loadArguments(STRINGS);
		Assert.assertNotNull(arguments);
		final String[] result = Arguments.argumentToArray(arguments);
		Assert.assertNotNull(result);

		Assert.assertTrue("Array must be equals", result.length == STRINGS.length);
		for (int i = 0; i < STRINGS.length; i++) {
			Assert.assertTrue("Result must contains '" + STRINGS[i] + "'", lookup(result, STRINGS[i]));
		}
	}

	private Boolean lookup(final String[] result, final String item) {
		for (final String string : result) {
			if (item.equals(string)) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}

	@Test
	public void testGetBoolean() {
		final Map<String, String> arguments = Arguments.loadArguments(STRINGS);
		Assert.assertTrue(Arguments.getBooleanArgument(arguments, "name", null));

		// default not valued
		Assert.assertFalse(Arguments.getBooleanArgument(arguments, "name-that-not-exist", null));
		Assert.assertTrue(Arguments.getBooleanArgument(arguments, "name-that-not-exist", Boolean.TRUE));
		Assert.assertFalse(Arguments.getBooleanArgument(arguments, "name-that-not-exist", Boolean.FALSE));

		// valued
		Assert.assertFalse(Arguments.getBooleanArgument(arguments, "name2", Boolean.TRUE));
		Assert.assertTrue(Arguments.getBooleanArgument(arguments, "name3", Boolean.FALSE));
	}

	@Test
	public void testGetInteger() {
		final Map<String, String> arguments = Arguments.loadArguments(STRINGS);
		Assert.assertEquals(Integer.valueOf(1), Arguments.getIntegerArgument(arguments, "integer-that-not-exist", 1));
		Assert.assertNotSame(Integer.valueOf(-1), Arguments.getIntegerArgument(arguments, "i", -1));
		Assert.assertEquals(Integer.valueOf(123), Arguments.getIntegerArgument(arguments, "i", 1));
	}

	@Test
	public void testGetString() {
		final Map<String, String> arguments = Arguments.loadArguments(STRINGS);
		Assert.assertNotNull("Expected 'name' titi", arguments.get("titi"));
		Assert.assertEquals("Expected 'tutu'", arguments.get("titi"), "tutu");
	}

}
