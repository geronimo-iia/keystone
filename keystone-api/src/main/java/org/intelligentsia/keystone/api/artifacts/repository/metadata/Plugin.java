/**
 * 
 */
package org.intelligentsia.keystone.api.artifacts.repository.metadata;

/**
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class Plugin {
	/** Display name for the plugin. */
	private String name;
	/** The plugin invocation prefix (i.e. eclipse for eclipse:eclipse). */
	private String prefix;
	/** The plugin artifactId. */
	private String artifactId;

	/**
	 * Build a new instance of <code>Plugin</code>
	 */
	public Plugin() {
		super();
	}

	/**
	 * Build a new instance of <code>Plugin</code>
	 * 
	 * @param name
	 * @param prefix
	 * @param artifactId
	 */
	public Plugin(final String name, final String prefix, final String artifactId) {
		super();
		this.name = name;
		this.prefix = prefix;
		this.artifactId = artifactId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getPrefix() {
		return this.prefix;
	}

	public void setPrefix(final String prefix) {
		this.prefix = prefix;
	}

	public String getArtifactId() {
		return this.artifactId;
	}

	public void setArtifactId(final String artifactId) {
		this.artifactId = artifactId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((this.artifactId == null) ? 0 : this.artifactId.hashCode());
		result = (prime * result) + ((this.name == null) ? 0 : this.name.hashCode());
		result = (prime * result) + ((this.prefix == null) ? 0 : this.prefix.hashCode());
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
		final Plugin other = (Plugin) obj;
		if (this.artifactId == null) {
			if (other.artifactId != null) {
				return false;
			}
		} else if (!this.artifactId.equals(other.artifactId)) {
			return false;
		}
		if (this.name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!this.name.equals(other.name)) {
			return false;
		}
		if (this.prefix == null) {
			if (other.prefix != null) {
				return false;
			}
		} else if (!this.prefix.equals(other.prefix)) {
			return false;
		}
		return true;
	}

}
