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
import java.util.List;

/**
 * Metadata class Definition.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class Metadata {
	/**
	 * The groupId that is directory represents, if any.
	 * 
	 * @uml.property name="groupId"
	 */
	private String groupId;

	/**
	 * The artifactId that is directory represents, if any.
	 * 
	 * @uml.property name="artifactId"
	 */
	private String artifactId;

	/**
	 * The version that is directory represents, if any.
	 * 
	 * @uml.property name="version"
	 */
	private String version;

	/**
	 * Versioning information for the artifact.
	 * 
	 * @uml.property name="versioning"
	 * @uml.associationEnd
	 */
	private Versioning versioning;
	/**
	 * The set of plugin mappings for the group.
	 * 
	 * @uml.property name="plugins"
	 */
	private List<Plugin> plugins;

	/**
	 * Build a new instance of <code>Metadata</code>
	 */
	public Metadata() {
		super();
	}

	/**
	 * @return
	 * @uml.property name="groupId"
	 */
	public String getGroupId() {
		return this.groupId;
	}

	/**
	 * @param groupId
	 * @uml.property name="groupId"
	 */
	public void setGroupId(final String groupId) {
		this.groupId = groupId;
	}

	/**
	 * @return
	 * @uml.property name="artifactId"
	 */
	public String getArtifactId() {
		return this.artifactId;
	}

	/**
	 * @param artifactId
	 * @uml.property name="artifactId"
	 */
	public void setArtifactId(final String artifactId) {
		this.artifactId = artifactId;
	}

	/**
	 * @return
	 * @uml.property name="version"
	 */
	public String getVersion() {
		return this.version;
	}

	/**
	 * @param version
	 * @uml.property name="version"
	 */
	public void setVersion(final String version) {
		this.version = version;
	}

	/**
	 * @return
	 * @uml.property name="versioning"
	 */
	public Versioning getVersioning() {
		if (versioning == null) {
			versioning = new Versioning();
		}
		return this.versioning;
	}

	/**
	 * @param versioning
	 * @uml.property name="versioning"
	 */
	public void setVersioning(final Versioning versioning) {
		this.versioning = versioning;
	}

	/**
	 * @return
	 * @uml.property name="plugins"
	 */
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

	/**
	 * @param plugins
	 * @uml.property name="plugins"
	 */
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
