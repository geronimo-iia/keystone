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
package org.intelligentsia.keystone.api.artifacts.repository;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

/**
 * FileRepository JSONTest.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class FileRepositoryTest {

	private File root;
	private FileRepository repository;

	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	@Before
	public void setUp() throws Exception {
		root = new File("src/repository");
		repository = new FileRepository(new Repository("test-file", root.toURI().toString()));
	}

	@Test
	public void testRootAccess() {
		Assert.assertNotNull(repository);
		final File r = repository.get(".");
		Assert.assertNotNull(r);
		Assert.assertEquals(root.toURI().toString(), r.toURI().toString());
	}

	@Test
	public void testExistFolder() {
		Assert.assertNotNull(repository);
		Assert.assertTrue(repository.exists("org"));
		Assert.assertFalse(repository.exists("com"));
	}

	@Test
	public void testFolderAccess() {
		Assert.assertNotNull(repository);
		final File r = repository.get("org/intelligents-ia/keystone");
		Assert.assertNotNull(r);
	}

	@Test
	public void testChildFolderAccess() {
		Assert.assertNotNull(repository);
		final List<File> r = repository.list("org/intelligents-ia/keystone/keystone-api");
		Assert.assertNotNull(r);
		Assert.assertFalse(r.isEmpty());
		Assert.assertEquals(3, r.size());
	}

	@Test
	public void testPutAndDeleteFile() throws URISyntaxException, IllegalArgumentException, IOException {
		final File source = new File(getClass().getResource("/test-put.txt").toURI());
		Assert.assertNotNull(source);
		repository.put("test.txt", source);
		repository.put("test2/second.txt", source);
		Assert.assertTrue(repository.exists("test.txt"));
		Assert.assertTrue(repository.exists("test2/second.txt"));
		repository.delete("test.txt");
		repository.delete("test2");
		Assert.assertFalse(repository.exists("test.txt"));
		Assert.assertFalse(repository.exists("test2/second.txt"));

	}
}
