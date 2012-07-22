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
package org.intelligentsia.keystone.api.artifacts;

import java.io.File;

import junit.framework.Assert;

import org.intelligentsia.keystone.api.artifacts.repository.FileRepository;
import org.intelligentsia.keystone.api.artifacts.repository.Repository;
import org.intelligentsia.keystone.api.artifacts.repository.metadata.Metadata;
import org.junit.Before;
import org.junit.Test;

/**
 * Artifacts Service Test.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class ArtifactsServiceTest {

	private FileRepository repository;
	private ArtifactsService service;

	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	@Before
	public void setUp() throws Exception {
		final File root = new File("src/repository");
		repository = new FileRepository(new Repository("test-file", root.toURI().toString()));
		service = new DefaultArtifactsService(repository);
	}

	@Test
	public void testMetadataExist() {
		Assert.assertNotNull(repository);
		Assert.assertNotNull(service);
		final ArtifactIdentifier keystoneApi = new ArtifactIdentifier("org.intelligents-ia.keystone:keystone-api");
		final Metadata metadata = service.getMetadata(keystoneApi);
		Assert.assertNotNull(metadata);
		Assert.assertEquals("org.intelligents-ia.keystone", metadata.getGroupId());
		Assert.assertEquals("keystone-api", metadata.getArtifactId());
	}

	@Test
	public void testMetadataNotExist() {
		Assert.assertNotNull(repository);
		Assert.assertNotNull(service);
		final ArtifactIdentifier keystoneApi = new ArtifactIdentifier("org.intelligents-ia.keystone:keystonenotexists");
		try {
			service.getMetadata(keystoneApi);
			Assert.fail("metadata should not exists for org.intelligents-ia.keystone:keystonenotexists");
		} catch (final ResourceDoesNotExistException e) {
		}
	}

	@Test
	public void testMetadataInvalid() {
		Assert.assertNotNull(repository);
		Assert.assertNotNull(service);
		final ArtifactIdentifier keystoneApi = new ArtifactIdentifier("org.intelligents-ia.keystone:keystoneinvalid");
		try {
			service.getMetadata(keystoneApi);
			Assert.fail("metadata must be invalid for org.intelligents-ia.keystone:keystoneinvalid");
		} catch (final TransferFailedException e) {
		}
	}

	@Test
	public void testResolve() {
		Assert.assertNotNull(repository);
		Assert.assertNotNull(service);
		final ArtifactIdentifier keystoneApi = new ArtifactIdentifier("org.intelligents-ia.keystone:keystone-api:1.3");
		ArtifactIdentifier id = service.resolve("org.intelligents-ia.keystone", "keystone-api", false);
		Assert.assertNotNull(id);
		Assert.assertEquals(keystoneApi, id);
		id = service.resolve("org.intelligents-ia.keystone", "keystone-api", true);
		Assert.assertNotNull(id);
		Assert.assertEquals(keystoneApi, id);
	}

	@Test
	public void testGetResource() {
		Assert.assertNotNull(repository);
		Assert.assertNotNull(service);
		final ArtifactIdentifier keystoneApi = new ArtifactIdentifier("org.intelligents-ia.keystone:keystone-api");
		final Resource resource = service.get(keystoneApi);
		Assert.assertNotNull(resource);
		Assert.assertNotNull(resource.getArtefactIdentifier());
		Assert.assertNotNull(resource.getName());
		Assert.assertEquals("org/intelligents-ia/keystone/keystone-api/1.3/keystone-api-1.3.jar", resource.getName());
		Assert.assertNotNull(resource.getLocalFile());
		Assert.assertTrue(resource.getLocalFile().exists());
	}
}
