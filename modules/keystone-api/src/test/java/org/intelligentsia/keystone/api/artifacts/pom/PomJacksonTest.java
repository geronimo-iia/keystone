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
package org.intelligentsia.keystone.api.artifacts.pom;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

import org.intelligentsia.keystone.api.artifacts.ResourceDoesNotExistException;
import org.intelligentsia.keystone.api.artifacts.TransferFailedException;
import org.intelligentsia.keystone.api.artifacts.repository.FileRepository;
import org.intelligentsia.keystone.api.artifacts.repository.Repository;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

/**
 * POM and Jackson Test.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */

public class PomJacksonTest {

	private FileRepository repository;

	@Before
	public void setUp() throws Exception {
		final File root = new File("src/repository");
		repository = new FileRepository(new Repository("test-file", root.toURI().toString()));
	}

	@Test
	public void testReadXmlPom() throws JsonParseException, JsonMappingException, ResourceDoesNotExistException, TransferFailedException, IOException {
		final ObjectMapper mapper = new XmlMapper();
		final Pom pom = mapper.readValue(repository.get("org/intelligents-ia/keystone/keystone-api/1.3/keystone-api-1.3.pom"), Pom.class);
		Assert.assertNotNull(pom);
		Assert.assertEquals(null, pom.getGroupId());
		Assert.assertEquals("keystone-api", pom.getArtifactId());
		Assert.assertEquals(null, pom.getVersion());
		Assert.assertEquals("jar", pom.getPackaging());
		Assert.assertEquals("keystone-api", pom.getName());
		Assert.assertNotNull(pom.getParent());
		Assert.assertEquals("org.intelligents-ia.keystone", pom.getParent().getGroupId());
		Assert.assertEquals("keystone", pom.getParent().getArtifactId());
		Assert.assertEquals("1.3", pom.getParent().getVersion());
	}

}
