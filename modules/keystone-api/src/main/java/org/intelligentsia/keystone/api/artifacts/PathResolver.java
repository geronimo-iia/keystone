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

import java.util.StringTokenizer;

/**
 * Path PathResolver.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public final class PathResolver {
	private static final transient String DOT = ".";
	private static final transient String POM = ".pom";
	private static final transient String SEPARATOR = "/";
	private static final transient String MAVEN_METADATA_XML = "maven-metadata.xml";

	/**
	 * @param identifier
	 * @return base path for specified artifact.
	 */
	public final static String resolve(final ArtifactIdentifier identifier) {
		return PathResolver.getBase(identifier, true).toString();
	}

	/**
	 * @param identifier
	 * @return metadata path for specified artifact.
	 */
	public final static String resolveMetadata(final ArtifactIdentifier identifier) {
		return PathResolver.getBase(identifier, false).append(MAVEN_METADATA_XML).toString();
	}

	/**
	 * @param identifier
	 * @return pom path for specified artifact.
	 */
	public final static String resolvePom(final ArtifactIdentifier identifier) {
		return PathResolver.getBase(identifier, true).append(identifier.getArtifactId()).append(POM).toString();
	}

	/**
	 * @param identifier
	 *            artifact identifier
	 * @param addVersion
	 *            if tru add version level
	 * @return a StringBuilder instance of root.
	 */
	public static StringBuilder getBase(final ArtifactIdentifier identifier, final boolean addVersion) {
		final StringBuilder builder = new StringBuilder();
		// tokenize groupId
		final StringTokenizer tokenizer = new StringTokenizer(identifier.getGroupId(), DOT);
		while (tokenizer.hasMoreTokens()) {
			builder.append(tokenizer.nextToken()).append(SEPARATOR);
		}
		// artifact id
		if ((identifier.getArtifactId() != null) && !"".equals(identifier.getArtifactId())) {
			builder.append(identifier.getArtifactId()).append(SEPARATOR);
		}
		// version
		if (addVersion && (identifier.getVersion() != null) && !"".equals(identifier.getVersion())) {
			builder.append(identifier.getVersion()).append(SEPARATOR);
		}
		return builder;
	}
}