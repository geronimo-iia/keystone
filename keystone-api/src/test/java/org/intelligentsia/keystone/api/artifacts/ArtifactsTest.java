package org.intelligentsia.keystone.api.artifacts;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * Artifacts test case.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class ArtifactsTest extends TestCase {

	public void testEquals() {
		final ArtifactIdentifier identifier = new ArtifactIdentifier("g1", "a1", "v1");
		final ArtifactIdentifier identifier2 = new ArtifactIdentifier("g1:a1:v1");
		Assert.assertEquals(identifier, identifier2);
	}
}
