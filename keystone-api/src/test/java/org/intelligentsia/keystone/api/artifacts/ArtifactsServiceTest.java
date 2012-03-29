/**
 * 
 */
package org.intelligentsia.keystone.api.artifacts;

import java.io.File;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.intelligentsia.keystone.api.artifacts.ArtifactIdentifier;
import org.intelligentsia.keystone.api.artifacts.ArtifactsService;
import org.intelligentsia.keystone.api.artifacts.DefaultArtifactsService;
import org.intelligentsia.keystone.api.artifacts.Resource;
import org.intelligentsia.keystone.api.artifacts.ResourceDoesNotExistException;
import org.intelligentsia.keystone.api.artifacts.TransferFailedException;
import org.intelligentsia.keystone.api.artifacts.repository.FileRepository;
import org.intelligentsia.keystone.api.artifacts.repository.Repository;
import org.intelligentsia.keystone.api.artifacts.repository.metadata.Metadata;

/**
 * Artifacts Service Test.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class ArtifactsServiceTest extends TestCase {

	private FileRepository repository;
	private ArtifactsService service;

	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		File root = new File("src/repository");
		repository = new FileRepository(new Repository("test-file", root.toURI().toString()));
		service = new DefaultArtifactsService(repository);
	}

	public void testMetadataExist() {
		Assert.assertNotNull(repository);
		Assert.assertNotNull(service);
		ArtifactIdentifier keystoneApi = new ArtifactIdentifier("org.intelligents-ia.keystone:keystone-api");
		Metadata metadata = service.getMetadata(keystoneApi);
		Assert.assertNotNull(metadata);
		Assert.assertEquals("org.intelligents-ia.keystone", metadata.getGroupId());
		Assert.assertEquals("keystone-api", metadata.getArtifactId());
	}

	public void testMetadataNotExist() {
		Assert.assertNotNull(repository);
		Assert.assertNotNull(service);
		ArtifactIdentifier keystoneApi = new ArtifactIdentifier("org.intelligents-ia.keystone:keystonenotexists");
		try {
			service.getMetadata(keystoneApi);
			Assert.fail("metadata should not exists for org.intelligents-ia.keystone:keystonenotexists");
		} catch (ResourceDoesNotExistException e) {
		}
	}

	public void testMetadataInvalid() {
		Assert.assertNotNull(repository);
		Assert.assertNotNull(service);
		ArtifactIdentifier keystoneApi = new ArtifactIdentifier("org.intelligents-ia.keystone:keystoneinvalid");
		try {
			service.getMetadata(keystoneApi);
			Assert.fail("metadata must be invalid for org.intelligents-ia.keystone:keystoneinvalid");
		} catch (TransferFailedException e) {
		}
	}

	public void testResolve() {
		Assert.assertNotNull(repository);
		Assert.assertNotNull(service);
		ArtifactIdentifier keystoneApi = new ArtifactIdentifier("org.intelligents-ia.keystone:keystone-api:1.3");
		ArtifactIdentifier id = service.resolve("org.intelligents-ia.keystone", "keystone-api", false);
		Assert.assertNotNull(id);
		Assert.assertEquals(keystoneApi, id);
		id = service.resolve("org.intelligents-ia.keystone", "keystone-api", true);
		Assert.assertNotNull(id);
		Assert.assertEquals(keystoneApi, id);
	}

	public void testGetResource() {
		Assert.assertNotNull(repository);
		Assert.assertNotNull(service);
		ArtifactIdentifier keystoneApi = new ArtifactIdentifier("org.intelligents-ia.keystone:keystone-api");
		Resource resource = service.get(keystoneApi);
		Assert.assertNotNull(resource);
		Assert.assertNotNull(resource.getArtefactIdentifier());
		Assert.assertNotNull(resource.getName());
		Assert.assertEquals("org/intelligents-ia/keystone/keystone-api/1.3/keystone-api-1.3.jar", resource.getName());
		Assert.assertNotNull(resource.getLocalFile());
		Assert.assertTrue(resource.getLocalFile().exists());
	}
}
