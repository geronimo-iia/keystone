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

import junit.framework.Assert;

import org.junit.Test;

/**
 * PathResolver test case.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class PathResolverTest {
	@Test
	public void testGAV() {
		Assert.assertEquals("g/a/1/", PathResolver.resolve(new ArtifactIdentifier("g", "a", "1")));
	}

	@Test
	public void testGA() {
		Assert.assertEquals("g/a/", PathResolver.resolve(new ArtifactIdentifier("g", "a", null)));
	}

	@Test
	public void testG() {
		Assert.assertEquals("g/", PathResolver.resolve(new ArtifactIdentifier("g", null, null)));
	}

	@Test
	public void testGroupTree() {
		Assert.assertEquals("g/a/b/c/d/a/1/", PathResolver.resolve(new ArtifactIdentifier("g.a.b.c.d", "a", "1")));
	}

	@Test
	public void testMetadata() {
		Assert.assertEquals("g/a/b/c/d/a/maven-metadata.xml", PathResolver.resolveMetadata(new ArtifactIdentifier("g.a.b.c.d", "a", "1")));
	}

	@Test
	public void testMetadataWithoutVersion() {
		Assert.assertEquals("g/a/b/c/d/a/maven-metadata.xml", PathResolver.resolveMetadata(new ArtifactIdentifier("g.a.b.c.d", "a", null)));
	}

	@Test
	public void testPom() {
		Assert.assertEquals("g/a/b/c/d/a/1/a.pom", PathResolver.resolvePom(new ArtifactIdentifier("g.a.b.c.d", "a", "1")));
	}
}
