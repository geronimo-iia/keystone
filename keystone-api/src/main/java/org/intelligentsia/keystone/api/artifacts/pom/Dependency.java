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
package org.intelligentsia.keystone.api.artifacts.pom;

import java.io.Serializable;
import java.util.List;

/**
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class Dependency implements Serializable {

	private static final long serialVersionUID = -7672589090039674061L;
	private String groupId;
	private String artifactId;
	private String version;
	private String type;
	private String classifier;
	private String scope;
	private String systemPath;
	private List<Exclusion> exclusions;
	private String optional;

	public Dependency() {
		super();
	}

	/**
	 * @return the groupId
	 */
	public final String getGroupId() {
		return groupId;
	}

	/**
	 * @param groupId
	 *            the groupId to set
	 */
	public final void setGroupId(final String groupId) {
		this.groupId = groupId;
	}

	/**
	 * @return the artifactId
	 */
	public final String getArtifactId() {
		return artifactId;
	}

	/**
	 * @param artifactId
	 *            the artifactId to set
	 */
	public final void setArtifactId(final String artifactId) {
		this.artifactId = artifactId;
	}

	/**
	 * @return the version
	 */
	public final String getVersion() {
		return version;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	public final void setVersion(final String version) {
		this.version = version;
	}

	/**
	 * @return the type
	 */
	public final String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public final void setType(final String type) {
		this.type = type;
	}

	/**
	 * @return the classifier
	 */
	public final String getClassifier() {
		return classifier;
	}

	/**
	 * @param classifier
	 *            the classifier to set
	 */
	public final void setClassifier(final String classifier) {
		this.classifier = classifier;
	}

	/**
	 * @return the scope
	 */
	public final String getScope() {
		return scope;
	}

	/**
	 * @param scope
	 *            the scope to set
	 */
	public final void setScope(final String scope) {
		this.scope = scope;
	}

	/**
	 * @return the systemPath
	 */
	public final String getSystemPath() {
		return systemPath;
	}

	/**
	 * @param systemPath
	 *            the systemPath to set
	 */
	public final void setSystemPath(final String systemPath) {
		this.systemPath = systemPath;
	}

	/**
	 * @return the exclusions
	 */
	public final List<Exclusion> getExclusions() {
		return exclusions;
	}

	/**
	 * @param exclusions
	 *            the exclusions to set
	 */
	public final void setExclusions(final List<Exclusion> exclusions) {
		this.exclusions = exclusions;
	}

	/**
	 * @return the optional
	 */
	public final String getOptional() {
		return optional;
	}

	/**
	 * @param optional
	 *            the optional to set
	 */
	public final void setOptional(final String optional) {
		this.optional = optional;
	}

}
