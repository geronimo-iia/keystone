/**
 * 
 */
package org.intelligentsia.keystone.api.artifacts.repository.metadata;

/**
 * Snapshot Definition.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class Snapshot {
	/**
	 * The time it was deployed.
	 */
	private String	timestamp;

	/**
	 * The incremental build number.
	 * Default value is: 0.
	 */
	private int		buildNumber	= 0;

	/**
	 * Whether to use a local copy instead (with filename that includes the base version).
	 * Default value is: false.
	 */
	private boolean	localCopy	= false;

	public String getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public int getBuildNumber() {
		return this.buildNumber;
	}

	public void setBuildNumber(int buildNumber) {
		this.buildNumber = buildNumber;
	}

	public boolean isLocalCopy() {
		return this.localCopy;
	}

	public void setLocalCopy(boolean localCopy) {
		this.localCopy = localCopy;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.buildNumber;
		result = prime * result + (this.localCopy ? 1231 : 1237);
		result = prime * result + ((this.timestamp == null) ? 0 : this.timestamp.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Snapshot other = (Snapshot) obj;
		if (this.buildNumber != other.buildNumber)
			return false;
		if (this.localCopy != other.localCopy)
			return false;
		if (this.timestamp == null) {
			if (other.timestamp != null)
				return false;
		} else if (!this.timestamp.equals(other.timestamp))
			return false;
		return true;
	}

}
