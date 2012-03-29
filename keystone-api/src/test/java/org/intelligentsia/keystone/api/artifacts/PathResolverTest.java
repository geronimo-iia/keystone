/**
 * 
 */
package org.intelligentsia.keystone.api.artifacts;

import org.intelligentsia.keystone.api.artifacts.ArtifactIdentifier;
import org.intelligentsia.keystone.api.artifacts.PathResolver;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * PathResolver test case.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class PathResolverTest extends TestCase {

	public void testGAV() {
		Assert.assertEquals("g/a/1/", PathResolver.resolve(new ArtifactIdentifier("g", "a", "1")));
	}

	public void testGA() {
		Assert.assertEquals("g/a/", PathResolver.resolve(new ArtifactIdentifier("g", "a", null)));
	}

	public void testG() {
		Assert.assertEquals("g/", PathResolver.resolve(new ArtifactIdentifier("g", null, null)));
	}

	public void testGroupTree() {
		Assert.assertEquals("g/a/b/c/d/a/1/", PathResolver.resolve(new ArtifactIdentifier("g.a.b.c.d", "a", "1")));
	}

	public void testMetadata() {
		Assert.assertEquals("g/a/b/c/d/a/maven-metadata.xml",
				PathResolver.resolveMetadata(new ArtifactIdentifier("g.a.b.c.d", "a", "1")));
	}

	public void testMetadataWithoutVersion() {
		Assert.assertEquals("g/a/b/c/d/a/maven-metadata.xml",
				PathResolver.resolveMetadata(new ArtifactIdentifier("g.a.b.c.d", "a", null)));
	}

	public void testPom() {
		Assert.assertEquals("g/a/b/c/d/a/1/a.pom",
				PathResolver.resolvePom(new ArtifactIdentifier("g.a.b.c.d", "a", "1")));
	}
}
