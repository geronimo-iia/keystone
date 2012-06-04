package org.intelligentsia.keystone.api.artifacts.pom;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.intelligentsia.keystone.api.artifacts.ResourceDoesNotExistException;
import org.intelligentsia.keystone.api.artifacts.TransferFailedException;
import org.intelligentsia.keystone.api.artifacts.repository.FileRepository;
import org.intelligentsia.keystone.api.artifacts.repository.Repository;

import com.fasterxml.jackson.xml.XmlMapper;

/**
 * POM and Jackson Test.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */

public class PomJacksonTest extends TestCase {

	private FileRepository repository;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		final File root = new File("src/repository");
		repository = new FileRepository(new Repository("test-file", root.toURI().toString()));
	}

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
