/**
 * 
 */
package org.intelligentsia.keystone.api.artifacts;

import java.io.Serializable;
import java.util.StringTokenizer;

/**
 * ArtifactIdentifier manage the 2B3...
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class ArtifactIdentifier implements Serializable {

	private static final long serialVersionUID = 4702119966025396690L;
	private final String groupId;
	private final String artifactId;
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

	public String getGroupId() {
		return groupId;
	}

	public String getArtifactId() {
		return artifactId;
	}

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

	public static final ArtifactIdentifier parse(final String value) {
		final StringTokenizer tokenizer = new StringTokenizer(value, ":");
		final String g = tokenizer.nextToken();
		final String a = tokenizer.nextToken();
		final String v = tokenizer.hasMoreElements() ? tokenizer.nextToken() : null;
		return new ArtifactIdentifier(g, a, v);
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

}
