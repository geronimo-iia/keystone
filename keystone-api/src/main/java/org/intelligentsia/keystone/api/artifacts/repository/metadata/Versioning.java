/**
 * 
 */
package org.intelligentsia.keystone.api.artifacts.repository.metadata;

import java.util.ArrayList;

/**
 * Versioning class Definition.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class Versioning {
	/**
	 * What the latest version in the directory is, including snapshots.
	 */
	private String latest;

	/**
	 * What the latest version in the directory is, of the releases.
	 */
	private String release;

	/**
	 * The current snapshot data in use for this version.
	 */
	private Snapshot snapshot;

	/**
	 * Versions available for the artifact.
	 */
	private java.util.List<String> versions;

	/**
	 * When the metadata was last updated.
	 */
	private String lastUpdated;

	/**
	 * 
	 * @return What the latest version in the directory is, including snapshots.
	 */
	public String getLatest() {
		return this.latest;
	}

	public void setLatest(final String latest) {
		this.latest = latest;
	}

	/**
	 * 
	 * @return What the latest version in the directory is, of the releases.
	 */
	public String getRelease() {
		return this.release;
	}

	public void setRelease(final String release) {
		this.release = release;
	}

	/**
	 * 
	 * @return The current snapshot data in use for this version.
	 */
	public Snapshot getSnapshot() {
		return this.snapshot;
	}

	public void setSnapshot(final Snapshot snapshot) {
		this.snapshot = snapshot;
	}

	/**
	 * 
	 * @return Versions available for the artifact.
	 */
	public java.util.List<String> getVersions() {
		if (versions == null) {
			versions = new ArrayList<String>();
		}
		return this.versions;
	}

	public void setVersions(final java.util.List<String> versions) {
		this.versions = versions;
	}

	/**
	 * 
	 * @return When the metadata was last updated.
	 */
	public String getLastUpdated() {
		return this.lastUpdated;
	}

	public void setLastUpdated(final String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public void updateTimestamp() {
		setLastUpdatedTimestamp(new java.util.Date());
	}

	public void setLastUpdatedTimestamp(final java.util.Date date) {
		final java.util.TimeZone timezone = java.util.TimeZone.getTimeZone("UTC");
		final java.text.DateFormat fmt = new java.text.SimpleDateFormat("yyyyMMddHHmmss");
		fmt.setTimeZone(timezone);
		setLastUpdated(fmt.format(date));
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((lastUpdated == null) ? 0 : lastUpdated.hashCode());
		result = (prime * result) + ((latest == null) ? 0 : latest.hashCode());
		result = (prime * result) + ((release == null) ? 0 : release.hashCode());
		result = (prime * result) + ((snapshot == null) ? 0 : snapshot.hashCode());
		result = (prime * result) + ((versions == null) ? 0 : versions.hashCode());
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
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
		final Versioning other = (Versioning) obj;
		if (lastUpdated == null) {
			if (other.lastUpdated != null) {
				return false;
			}
		} else if (!lastUpdated.equals(other.lastUpdated)) {
			return false;
		}
		if (latest == null) {
			if (other.latest != null) {
				return false;
			}
		} else if (!latest.equals(other.latest)) {
			return false;
		}
		if (release == null) {
			if (other.release != null) {
				return false;
			}
		} else if (!release.equals(other.release)) {
			return false;
		}
		if (snapshot == null) {
			if (other.snapshot != null) {
				return false;
			}
		} else if (!snapshot.equals(other.snapshot)) {
			return false;
		}
		if (versions == null) {
			if (other.versions != null) {
				return false;
			}
		} else if (!versions.equals(other.versions)) {
			return false;
		}
		return true;
	}

}
