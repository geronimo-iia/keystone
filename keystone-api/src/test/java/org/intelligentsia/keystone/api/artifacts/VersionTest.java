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
package org.intelligentsia.keystone.api.artifacts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * 
 * VersionTest implement test case for {@link Version}.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class VersionTest {

	@Test
	public void checkParseVersion() {
		assertEquals(new Version(1), Version.parse("1"));
		assertEquals(new Version(1, "test"), Version.parse("1-test"));
		assertEquals(new Version(1, 2), Version.parse("1.2"));
		assertEquals(new Version(1, 2, "test"), Version.parse("1.2-test"));
		assertEquals(new Version(1, 2, 3), Version.parse("1.2.3"));
		assertEquals(new Version(1, 2, 3, "test"), Version.parse("1.2.3-test"));

	}

	@Test
	public void checkFormatVersion() {
		assertEquals("1", Version.format(new Version(1)));
		assertEquals("1-test", Version.format(new Version(1, "test")));
		assertEquals("1.2", Version.format(new Version(1, 2)));
		assertEquals("1.2-test", Version.format(new Version(1, 2, "test")));
		assertEquals("1.2.3", Version.format(new Version(1, 2, 3)));
		assertEquals("1.2.3-test", Version.format(new Version(1, 2, 3, "test")));
	}

	@Test
	public void checkConstructorVersion() {
		Version version = new Version(1);
		assertEquals(new Integer(1), version.getMajor());
		assertNull(version.getMedium());
		assertNull(version.getMinor());
		assertNull(version.getClassifier());

		version = new Version(1, "test");
		assertEquals(new Integer(1), version.getMajor());
		assertNull(version.getMedium());
		assertNull(version.getMinor());
		assertEquals("test", version.getClassifier());

		version = new Version(1, 2);
		assertEquals(new Integer(1), version.getMajor());
		assertEquals(new Integer(2), version.getMedium());
		assertNull(version.getMinor());
		assertNull(version.getClassifier());

		version = new Version(1, 2, "test");
		assertEquals(new Integer(1), version.getMajor());
		assertEquals(new Integer(2), version.getMedium());
		assertNull(version.getMinor());
		assertEquals("test", version.getClassifier());

		version = new Version(1, 2, 3);
		assertEquals(new Integer(1), version.getMajor());
		assertEquals(new Integer(2), version.getMedium());
		assertEquals(new Integer(3), version.getMinor());
		assertNull(version.getClassifier());

		version = new Version(1, 2, 3, "test");
		assertEquals(new Integer(1), version.getMajor());
		assertEquals(new Integer(2), version.getMedium());
		assertEquals(new Integer(3), version.getMinor());
		assertEquals("test", version.getClassifier());
	}

	@Test
	public void checkEqualsVersion() {
		assertTrue(new Version(1).equals(new Version(1)));
		assertTrue(!new Version(1).equals(new Version(2)));
		assertTrue(new Version(1, 2).equals(new Version(1, 2)));
		assertTrue(!new Version(1, 2).equals(new Version(1, 2, 3)));
		assertTrue(!new Version(1, 2).equals(new Version(2, 1)));
		assertTrue(new Version(1, 2, 3).equals(new Version(1, 2, 3)));
		assertTrue(new Version(1, 2, 3, "test").equals(new Version(1, 2, 3, "test")));
	}

	@Test
	public void checkCompareVersionOnMajor() {
		Version version = new Version(1);
		assertEquals(new Integer(0), (Integer) version.compareTo(new Version(1)));
		version = new Version(1);
		assertEquals(new Integer(-1), (Integer) version.compareTo(new Version(3)));
		version = new Version(1, 3);
		assertEquals(new Integer(-1), (Integer) version.compareTo(new Version(3, 1)));
		version = new Version(1, 3, 4);
		assertEquals(new Integer(-1), (Integer) version.compareTo(new Version(3, 1, 0)));
		version = new Version(3);
		assertEquals(new Integer(1), (Integer) version.compareTo(new Version(1)));
	}

	@Test
	public void checkCompareVersionOnMedium() {
		Version version = new Version(1, 1);
		assertEquals(new Integer(0), (Integer) version.compareTo(new Version(1, 1)));
		version = new Version(1, 1);
		assertEquals(new Integer(-1), (Integer) version.compareTo(new Version(1, 3)));
		version = new Version(1, 3);
		assertEquals(new Integer(1), (Integer) version.compareTo(new Version(1, 1)));
		version = new Version(1, 3, 4);
		assertEquals(new Integer(1), (Integer) version.compareTo(new Version(1, 3, 0)));
		version = new Version(1, 3, 0);
		assertEquals(new Integer(-1), (Integer) version.compareTo(new Version(1, 3, 4)));
	}

	@Test
	public void checkCompareVersionOnMinor() {
		Version version = new Version(1, 1, 1);
		assertEquals(new Integer(0), (Integer) version.compareTo(new Version(1, 1, 1)));
		version = new Version(1, 1, 1);
		assertEquals(new Integer(-1), (Integer) version.compareTo(new Version(1, 1, 3)));
		version = new Version(1, 1, 3);
		assertEquals(new Integer(1), (Integer) version.compareTo(new Version(1, 1, 1)));
	}
}
