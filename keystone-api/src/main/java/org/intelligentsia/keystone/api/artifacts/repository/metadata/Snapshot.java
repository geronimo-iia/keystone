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

/**
 * Snapshot Definition.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class Snapshot {
	/**
	 * The time it was deployed.
	 * 
	 * @uml.property name="timestamp"
	 */
	private String timestamp;

	/**
	 * The incremental build number. Default value is: 0.
	 * 
	 * @uml.property name="buildNumber"
	 */
	private int buildNumber = 0;

	/**
	 * Whether to use a local copy instead (with filename that includes the base
	 * version). Default value is: false.
	 * 
	 * @uml.property name="localCopy"
	 */
	private boolean localCopy = false;

	/**
	 * @return
	 * @uml.property name="timestamp"
	 */
	public String getTimestamp() {
		return this.timestamp;
	}

	/**
	 * @param timestamp
	 * @uml.property name="timestamp"
	 */
	public void setTimestamp(final String timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @return
	 * @uml.property name="buildNumber"
	 */
	public int getBuildNumber() {
		return this.buildNumber;
	}

	/**
	 * @param buildNumber
	 * @uml.property name="buildNumber"
	 */
	public void setBuildNumber(final int buildNumber) {
		this.buildNumber = buildNumber;
	}

	/**
	 * @return
	 * @uml.property name="localCopy"
	 */
	public boolean isLocalCopy() {
		return this.localCopy;
	}

	/**
	 * @param localCopy
	 * @uml.property name="localCopy"
	 */
	public void setLocalCopy(final boolean localCopy) {
		this.localCopy = localCopy;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + this.buildNumber;
		result = (prime * result) + (this.localCopy ? 1231 : 1237);
		result = (prime * result) + ((this.timestamp == null) ? 0 : this.timestamp.hashCode());
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
		final Snapshot other = (Snapshot) obj;
		if (this.buildNumber != other.buildNumber) {
			return false;
		}
		if (this.localCopy != other.localCopy) {
			return false;
		}
		if (this.timestamp == null) {
			if (other.timestamp != null) {
				return false;
			}
		} else if (!this.timestamp.equals(other.timestamp)) {
			return false;
		}
		return true;
	}

}
