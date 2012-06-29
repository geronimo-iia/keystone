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
package org.intelligentsia.keystone.api.artifacts.repository.metadata;

import java.util.ArrayList;

/**
 * Versioning class Definition.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class Versioning {
	/**
	 * What the latest version in the directory is, including snapshots.
	 * 
	 * @uml.property name="latest"
	 */
	private String latest;

	/**
	 * What the latest version in the directory is, of the releases.
	 * 
	 * @uml.property name="release"
	 */
	private String release;

	/**
	 * The current snapshot data in use for this version.
	 * 
	 * @uml.property name="snapshot"
	 * @uml.associationEnd
	 */
	private Snapshot snapshot;

	/**
	 * Versions available for the artifact.
	 * 
	 * @uml.property name="versions"
	 */
	private java.util.List<String> versions;

	/**
	 * When the metadata was last updated.
	 * 
	 * @uml.property name="lastUpdated"
	 */
	private String lastUpdated;

	/**
	 * @return What the latest version in the directory is, including snapshots.
	 * @uml.property name="latest"
	 */
	public String getLatest() {
		return this.latest;
	}

	/**
	 * @param latest
	 * @uml.property name="latest"
	 */
	public void setLatest(final String latest) {
		this.latest = latest;
	}

	/**
	 * @return What the latest version in the directory is, of the releases.
	 * @uml.property name="release"
	 */
	public String getRelease() {
		return this.release;
	}

	/**
	 * @param release
	 * @uml.property name="release"
	 */
	public void setRelease(final String release) {
		this.release = release;
	}

	/**
	 * @return The current snapshot data in use for this version.
	 * @uml.property name="snapshot"
	 */
	public Snapshot getSnapshot() {
		return this.snapshot;
	}

	/**
	 * @param snapshot
	 * @uml.property name="snapshot"
	 */
	public void setSnapshot(final Snapshot snapshot) {
		this.snapshot = snapshot;
	}

	/**
	 * @return Versions available for the artifact.
	 * @uml.property name="versions"
	 */
	public java.util.List<String> getVersions() {
		if (versions == null) {
			versions = new ArrayList<String>();
		}
		return this.versions;
	}

	/**
	 * @param versions
	 * @uml.property name="versions"
	 */
	public void setVersions(final java.util.List<String> versions) {
		this.versions = versions;
	}

	/**
	 * @return When the metadata was last updated.
	 * @uml.property name="lastUpdated"
	 */
	public String getLastUpdated() {
		return this.lastUpdated;
	}

	/**
	 * @param lastUpdated
	 * @uml.property name="lastUpdated"
	 */
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
