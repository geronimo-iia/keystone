/**
 * 
 */
package org.intelligentsia.keystone.api.artifacts.repository.metadata;

import java.util.ArrayList;
import java.util.List;

/**
 * Metadata class Definition.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class Metadata {
	/**
	 * The groupId that is directory represents, if any.
	 */
	private String groupId;

	/**
	 * The artifactId that is directory represents, if any.
	 */
	private String artifactId;

	/**
	 * The version that is directory represents, if any.
	 */
	private String version;

	/**
	 * Versioning information for the artifact.
	 */
	private Versioning versioning;
	/**
	 * The set of plugin mappings for the group.
	 */
	private List<Plugin> plugins;

	/**
	 * Build a new instance of <code>Metadata</code>
	 */
	public Metadata() {
		super();
	}

	public String getGroupId() {
		return this.groupId;
	}

	public void setGroupId(final String groupId) {
		this.groupId = groupId;
	}

	public String getArtifactId() {
		return this.artifactId;
	}

	public void setArtifactId(final String artifactId) {
		this.artifactId = artifactId;
	}

	public String getVersion() {
		return this.version;
	}

	public void setVersion(final String version) {
		this.version = version;
	}

	public Versioning getVersioning() {
		if (versioning == null) {
			versioning = new Versioning();
		}
		return this.versioning;
	}

	public void setVersioning(final Versioning versioning) {
		this.versioning = versioning;
	}

	public List<Plugin> getPlugins() {
		if (plugins == null) {
			plugins = new ArrayList<Plugin>();
		}
		return this.plugins;
	}

	public List<Plugin> add(final Plugin plugin) {
		getPlugins().add(plugin);
		return plugins;
	}

	public void setPlugins(final List<Plugin> plugins) {
		this.plugins = plugins;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((this.artifactId == null) ? 0 : this.artifactId.hashCode());
		result = (prime * result) + ((this.groupId == null) ? 0 : this.groupId.hashCode());
		result = (prime * result) + ((this.plugins == null) ? 0 : this.plugins.hashCode());
		result = (prime * result) + ((this.version == null) ? 0 : this.version.hashCode());
		result = (prime * result) + ((this.versioning == null) ? 0 : this.versioning.hashCode());
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
		final Metadata other = (Metadata) obj;
		if (this.artifactId == null) {
			if (other.artifactId != null) {
				return false;
			}
		} else if (!this.artifactId.equals(other.artifactId)) {
			return false;
		}
		if (this.groupId == null) {
			if (other.groupId != null) {
				return false;
			}
		} else if (!this.groupId.equals(other.groupId)) {
			return false;
		}
		if (this.plugins == null) {
			if (other.plugins != null) {
				return false;
			}
		} else if (!this.plugins.equals(other.plugins)) {
			return false;
		}
		if (this.version == null) {
			if (other.version != null) {
				return false;
			}
		} else if (!this.version.equals(other.version)) {
			return false;
		}
		if (this.versioning == null) {
			if (other.versioning != null) {
				return false;
			}
		} else if (!this.versioning.equals(other.versioning)) {
			return false;
		}
		return true;
	}

}
