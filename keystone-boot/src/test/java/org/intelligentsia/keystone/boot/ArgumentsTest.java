/**
 * 
 */
package org.intelligentsia.keystone.boot;

import java.util.Map;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * ArgumentsTest.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class ArgumentsTest extends TestCase {

	private static String[] STRINGS = new String[] { "--name", "--name2=false", "--name3=yes", "--titi=tutu", "single", "-dummy", "-other=value", "--i=123" };

	public void testLoadArgument() {
		Map<String, String> arguments = Arguments.loadArguments(STRINGS);
		Assert.assertNotNull(arguments);
	}

	public void testLoadArgumentSingleValue() {
		Map<String, String> arguments = Arguments.loadArguments(STRINGS);
		Assert.assertNotNull("Expected 'name' key", arguments.get("name"));
		Assert.assertTrue("Expected 'false'", Boolean.parseBoolean(arguments.get("name")));
	}

	public void testLoadArgumentKeyValue() {
		Map<String, String> arguments = Arguments.loadArguments(STRINGS);
		Assert.assertNotNull("Expected 'name' titi", arguments.get("titi"));
		Assert.assertEquals("Expected 'tutu'", arguments.get("titi"), "tutu");
	}

	public void testLoadArgumentOther() {
		Map<String, String> arguments = Arguments.loadArguments(STRINGS);

		Assert.assertNotNull("Expected 'single' key", arguments.get("single"));
		Assert.assertEquals("Expected 'single'", arguments.get("single"), "single");

		Assert.assertNotNull("Expected '-dummy' key", arguments.get("-dummy"));
		Assert.assertNull("Expected no key 'dummy' ", arguments.get("dummy"));
		Assert.assertEquals("Expected '-dummy'", arguments.get("-dummy"), "-dummy");

		Assert.assertNotNull("Expected '-other=value' key", arguments.get("-other=value"));
		Assert.assertNull("Expected no key 'other' ", arguments.get("other"));
		Assert.assertEquals("Expected '-other=value'", arguments.get("-other=value"), "-other=value");

	}

	public void testArgumentToArray() {
		Map<String, String> arguments = Arguments.loadArguments(STRINGS);
		Assert.assertNotNull(arguments);
		String[] result = Arguments.argumentToArray(arguments);
		Assert.assertNotNull(result);

		Assert.assertTrue("Array must be equals", result.length == STRINGS.length);
		for (int i = 0; i < STRINGS.length; i++) {
			Assert.assertTrue("Result must contains '" + STRINGS[i] + "'", lookup(result, STRINGS[i]));
		}
	}

	private Boolean lookup(String[] result, String item) {
		for (String string : result) {
			if (item.equals(string)) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}

	public void testGetBoolean() {
		Map<String, String> arguments = Arguments.loadArguments(STRINGS);
		Assert.assertTrue(Arguments.getBooleanArgument(arguments, "name", null));

		// default not valued
		Assert.assertFalse(Arguments.getBooleanArgument(arguments, "name-that-not-exist", null));
		Assert.assertTrue(Arguments.getBooleanArgument(arguments, "name-that-not-exist", Boolean.TRUE));
		Assert.assertFalse(Arguments.getBooleanArgument(arguments, "name-that-not-exist", Boolean.FALSE));

		// valued
		Assert.assertFalse(Arguments.getBooleanArgument(arguments, "name2", Boolean.TRUE));
		Assert.assertTrue(Arguments.getBooleanArgument(arguments, "name3", Boolean.FALSE));
	}

	public void testGetInteger() {
		Map<String, String> arguments = Arguments.loadArguments(STRINGS);
		Assert.assertEquals(Integer.valueOf(1), Arguments.getIntegerArgument(arguments, "integer-that-not-exist", 1));
		Assert.assertNotSame(Integer.valueOf(-1), Arguments.getIntegerArgument(arguments, "i", -1));
		Assert.assertEquals(Integer.valueOf(123), Arguments.getIntegerArgument(arguments, "i", 1));
	}

	public void testGetString() {
		Map<String, String> arguments = Arguments.loadArguments(STRINGS);
		Assert.assertNotNull("Expected 'name' titi", arguments.get("titi"));
		Assert.assertEquals("Expected 'tutu'", arguments.get("titi"), "tutu");
	}

}
