/**
 * 
 */
package org.intelligentsia.keystone.api.artifacts.repository;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * FileRepository JSONTest.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class FileRepositoryTest extends TestCase {

	private File root;
	private FileRepository repository;

	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		root = new File("src/repository");
		repository = new FileRepository(new Repository("test-file", root.toURI().toString()));
	}

	public void testRootAccess() {
		Assert.assertNotNull(repository);
		final File r = repository.get(".");
		Assert.assertNotNull(r);
		Assert.assertEquals(root.toURI().toString(), r.toURI().toString());
	}

	public void testExistFolder() {
		Assert.assertNotNull(repository);
		Assert.assertTrue(repository.exists("org"));
		Assert.assertFalse(repository.exists("com"));
	}

	public void testFolderAccess() {
		Assert.assertNotNull(repository);
		final File r = repository.get("org/intelligents-ia/keystone");
		Assert.assertNotNull(r);
	}

	public void testChildFolderAccess() {
		Assert.assertNotNull(repository);
		final List<File> r = repository.list("org/intelligents-ia/keystone/keystone-api");
		Assert.assertNotNull(r);
		Assert.assertFalse(r.isEmpty());
		Assert.assertEquals(3, r.size());
	}

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
