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

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Properties;
import java.util.StringTokenizer;

import org.intelligentsia.keystone.api.StringUtils;

/**
 * ArtifactIdentifier manage the 2B3...
 * 
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class ArtifactIdentifier implements Serializable, Comparable<ArtifactIdentifier> {

	private static final long serialVersionUID = 4702119966025396690L;
	/**
	 * @uml.property name="groupId"
	 */
	private final String groupId;
	/**
	 * @uml.property name="artifactId"
	 */
	private final String artifactId;
	/**
	 * @uml.property name="version"
	 */
	private final String version;

	/**
	 * Build a new instance of <code>ArtifactIdentifier</code>
	 * 
	 * @param groupId
	 * @param artifactId
	 * @param version
	 */
	public ArtifactIdentifier(final String groupId, final String artifactId, final String version) {
		super();
		this.groupId = groupId;
		this.artifactId = artifactId;
		this.version = version;
	}

	public ArtifactIdentifier(final ArtifactIdentifier identifier) {
		groupId = identifier.groupId;
		artifactId = identifier.artifactId;
		version = identifier.version;
	}

	public ArtifactIdentifier(final String artefact) {
		this(ArtifactIdentifier.parse(artefact));
	}

	/**
	 * @return
	 * @uml.property name="groupId"
	 */
	public String getGroupId() {
		return groupId;
	}

	/**
	 * @return
	 * @uml.property name="artifactId"
	 */
	public String getArtifactId() {
		return artifactId;
	}

	/**
	 * @return
	 * @uml.property name="version"
	 */
	public String getVersion() {
		return version;
	}

	public Boolean isSnapshot() {
		return version.endsWith("SNAPSHOT");
	}

	@Override
	public String toString() {
		return groupId + ":" + artifactId + ":" + version;
	}

	/**
	 * Parse specified string.
	 * 
	 * @param value
	 * @return an ArtifactIdentifier instance.
	 */
	public static final ArtifactIdentifier parse(final String value) {
		final StringTokenizer tokenizer = new StringTokenizer(value, ":");
		final String g = tokenizer.nextToken();
		final String a = tokenizer.nextToken();
		final String v = tokenizer.hasMoreElements() ? tokenizer.nextToken() : null;
		return new ArtifactIdentifier(g, a, v);
	}

	/**
	 * Parse maven information from file
	 * 'META-INF/maven/groupId/artifactId/pom.properties'.
	 * 
	 * @param classLoader
	 * @param groupId
	 * @param artifactId
	 * @return {@link ArtifactIdentifier} instance.
	 * @throws KeystoneRuntimeException
	 *             if an error occurs
	 */
	public static final ArtifactIdentifier parse(final ClassLoader classLoader, final String groupId, final String artifactId) throws KeystoneRuntimeException {
		final Properties props = new Properties();
		final InputStream is = classLoader.getResourceAsStream("META-INF/maven/" + groupId + "/" + artifactId + "/pom.properties");
		if (is != null) {
			try {
				props.load(is);
			} catch (final IOException e) {
				throw new KeystoneRuntimeException(StringUtils.format("Cannot load information for %s:%s", groupId, artifactId), e);
			}
			return new ArtifactIdentifier(props.getProperty("groupId"), props.getProperty("artifactId"), props.getProperty("version"));
		}
		throw new KeystoneRuntimeException(StringUtils.format("Cannot load information for %s:%s", groupId, artifactId));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((artifactId == null) ? 0 : artifactId.hashCode());
		result = (prime * result) + ((groupId == null) ? 0 : groupId.hashCode());
		result = (prime * result) + ((version == null) ? 0 : version.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final ArtifactIdentifier other = (ArtifactIdentifier) obj;
		if (artifactId == null) {
			if (other.artifactId != null) {
				return false;
			}
		} else if (!artifactId.equals(other.artifactId)) {
			return false;
		}
		if (groupId == null) {
			if (other.groupId != null) {
				return false;
			}
		} else if (!groupId.equals(other.groupId)) {
			return false;
		}
		if (version == null) {
			if (other.version != null) {
				return false;
			}
		} else if (!version.equals(other.version)) {
			return false;
		}
		return true;
	}

	/**
	 * Compare this instance with other.
	 * 
	 * @throws IllegalArgumentException
	 *             if group and artifact are not the same or one of both have no
	 *             version.
	 */
	@Override
	public int compareTo(final ArtifactIdentifier other) {
		if (!sameAs(other)) {
			throw new IllegalArgumentException("this instance is not same as other");
		}
		if ((version == null) || (other.version == null)) {
			throw new IllegalArgumentException("No version information");
		}
		return Version.parse(version).compareTo(Version.parse(other.version));
	}

	/**
	 * @param other
	 * @return true if this instance and other have the same group and artifact
	 *         identifier.
	 */
	public boolean sameAs(final ArtifactIdentifier other) {
		return groupId.equals(other.groupId) && artifactId.equals(other.artifactId);
	}
}
