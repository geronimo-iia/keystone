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

/**
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class Parent implements Serializable {

	private static final long serialVersionUID = -6053043371284615084L;
	/**
	 * @uml.property name="artifactId"
	 */
	private String artifactId;
	/**
	 * @uml.property name="groupId"
	 */
	private String groupId;
	/**
	 * @uml.property name="version"
	 */
	private String version;
	/**
	 * @uml.property name="relativePath"
	 */
	private String relativePath;

	public Parent() {
		super();
	}

	/**
	 * @return the artifactId
	 * @uml.property name="artifactId"
	 */
	public final String getArtifactId() {
		return artifactId;
	}

	/**
	 * @param artifactId
	 *            the artifactId to set
	 * @uml.property name="artifactId"
	 */
	public final void setArtifactId(final String artifactId) {
		this.artifactId = artifactId;
	}

	/**
	 * @return the groupId
	 * @uml.property name="groupId"
	 */
	public final String getGroupId() {
		return groupId;
	}

	/**
	 * @param groupId
	 *            the groupId to set
	 * @uml.property name="groupId"
	 */
	public final void setGroupId(final String groupId) {
		this.groupId = groupId;
	}

	/**
	 * @return the version
	 * @uml.property name="version"
	 */
	public final String getVersion() {
		return version;
	}

	/**
	 * @param version
	 *            the version to set
	 * @uml.property name="version"
	 */
	public final void setVersion(final String version) {
		this.version = version;
	}

	/**
	 * @return the relativePath
	 * @uml.property name="relativePath"
	 */
	public final String getRelativePath() {
		return relativePath;
	}

	/**
	 * @param relativePath
	 *            the relativePath to set
	 * @uml.property name="relativePath"
	 */
	public final void setRelativePath(final String relativePath) {
		this.relativePath = relativePath;
	}

}
