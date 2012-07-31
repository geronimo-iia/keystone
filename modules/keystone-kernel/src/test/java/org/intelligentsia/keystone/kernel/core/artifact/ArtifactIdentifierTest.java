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
package org.intelligentsia.keystone.kernel.core.artifact;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

import java.io.IOException;

import org.intelligentsia.keystone.api.artifacts.ArtifactIdentifier;
import org.junit.Test;

/**
 * 
 * ArtifactIdentifierTest check parse from maven information.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class ArtifactIdentifierTest {

	@Test
	public void checkArtifactIdentifierParseFromMavenInformation() throws IOException {
		final ArtifactIdentifier artifactIdentifier = ArtifactIdentifier.parse(this.getClass().getClassLoader(), "org.intelligents-ia.keystone", "keystone-api");
		assertNotNull(artifactIdentifier);
		assertEquals("org.intelligents-ia.keystone", artifactIdentifier.getGroupId());
		assertEquals("keystone-api", artifactIdentifier.getArtifactId());
		assertNotNull(artifactIdentifier.getVersion());
	}
}
