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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * http://maven.apache.org/ref/3.0.3/maven-model/maven.html
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Pom implements Serializable {

	private static final long serialVersionUID = 6397991212624047355L;
	/**
	 * @uml.property name="modelVersion"
	 */
	private String modelVersion;
	/**
	 * @uml.property name="parent"
	 * @uml.associationEnd
	 */
	private Parent parent;
	/**
	 * @uml.property name="groupId"
	 */
	private String groupId;
	/**
	 * @uml.property name="artifactId"
	 */
	private String artifactId;
	/**
	 * @uml.property name="version"
	 */
	private String version;
	/**
	 * @uml.property name="packaging"
	 */
	private String packaging;

	/**
	 * @uml.property name="name"
	 */
	private String name;
	/**
	 * @uml.property name="description"
	 */
	private String description;
	/**
	 * @uml.property name="url"
	 */
	private String url;
	/**
	 * @uml.property name="inceptionYear"
	 */
	private String inceptionYear;

	/**
	 * @uml.property name="organization"
	 * @uml.associationEnd
	 */
	private Organization organization;
	/**
	 * @uml.property name="licenses"
	 */
	private List<Licence> licenses;
	/**
	 * @uml.property name="developers"
	 */
	private List<Developer> developers;
	/**
	 * @uml.property name="contributors"
	 */
	private List<Contributor> contributors;

	/**
	 * @uml.property name="modules"
	 */
	private List<String> modules;
	/**
	 * @uml.property name="dependencyManagement"
	 * @uml.associationEnd
	 */
	private DependencyManagement dependencyManagement;
	/**
	 * @uml.property name="dependencies"
	 */
	private List<Dependency> dependencies;

	public Pom() {
		super();
	}

	/**
	 * @return the modelVersion
	 * @uml.property name="modelVersion"
	 */
	public final String getModelVersion() {
		return modelVersion;
	}

	/**
	 * @param modelVersion
	 *            the modelVersion to set
	 * @uml.property name="modelVersion"
	 */
	public final void setModelVersion(final String modelVersion) {
		this.modelVersion = modelVersion;
	}

	/**
	 * @return the parent
	 * @uml.property name="parent"
	 */
	public final Parent getParent() {
		return parent;
	}

	/**
	 * @param parent
	 *            the parent to set
	 * @uml.property name="parent"
	 */
	public final void setParent(final Parent parent) {
		this.parent = parent;
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
	 * @return the packaging
	 * @uml.property name="packaging"
	 */
	public final String getPackaging() {
		return packaging;
	}

	/**
	 * @param packaging
	 *            the packaging to set
	 * @uml.property name="packaging"
	 */
	public final void setPackaging(final String packaging) {
		this.packaging = packaging;
	}

	/**
	 * @return the name
	 * @uml.property name="name"
	 */
	public final String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 * @uml.property name="name"
	 */
	public final void setName(final String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 * @uml.property name="description"
	 */
	public final String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 * @uml.property name="description"
	 */
	public final void setDescription(final String description) {
		this.description = description;
	}

	/**
	 * @return the url
	 * @uml.property name="url"
	 */
	public final String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 * @uml.property name="url"
	 */
	public final void setUrl(final String url) {
		this.url = url;
	}

	/**
	 * @return the inceptionYear
	 * @uml.property name="inceptionYear"
	 */
	public final String getInceptionYear() {
		return inceptionYear;
	}

	/**
	 * @param inceptionYear
	 *            the inceptionYear to set
	 * @uml.property name="inceptionYear"
	 */
	public final void setInceptionYear(final String inceptionYear) {
		this.inceptionYear = inceptionYear;
	}

	/**
	 * @return the organization
	 * @uml.property name="organization"
	 */
	public final Organization getOrganization() {
		return organization;
	}

	/**
	 * @param organization
	 *            the organization to set
	 * @uml.property name="organization"
	 */
	public final void setOrganization(final Organization organization) {
		this.organization = organization;
	}

	/**
	 * @return the licenses
	 * @uml.property name="licenses"
	 */
	public final List<Licence> getLicenses() {
		return licenses;
	}

	/**
	 * @param licenses
	 *            the licenses to set
	 * @uml.property name="licenses"
	 */
	public final void setLicenses(final List<Licence> licenses) {
		this.licenses = licenses;
	}

	/**
	 * @return the developers
	 * @uml.property name="developers"
	 */
	public final List<Developer> getDevelopers() {
		return developers;
	}

	/**
	 * @param developers
	 *            the developers to set
	 * @uml.property name="developers"
	 */
	public final void setDevelopers(final List<Developer> developers) {
		this.developers = developers;
	}

	/**
	 * @return the contributors
	 * @uml.property name="contributors"
	 */
	public final List<Contributor> getContributors() {
		return contributors;
	}

	/**
	 * @param contributors
	 *            the contributors to set
	 * @uml.property name="contributors"
	 */
	public final void setContributors(final List<Contributor> contributors) {
		this.contributors = contributors;
	}

	/**
	 * @return the modules
	 * @uml.property name="modules"
	 */
	public final List<String> getModules() {
		return modules;
	}

	/**
	 * @param modules
	 *            the modules to set
	 * @uml.property name="modules"
	 */
	public final void setModules(final List<String> modules) {
		this.modules = modules;
	}

	/**
	 * @return the dependencyManagement
	 * @uml.property name="dependencyManagement"
	 */
	public final DependencyManagement getDependencyManagement() {
		return dependencyManagement;
	}

	/**
	 * @param dependencyManagement
	 *            the dependencyManagement to set
	 * @uml.property name="dependencyManagement"
	 */
	public final void setDependencyManagement(final DependencyManagement dependencyManagement) {
		this.dependencyManagement = dependencyManagement;
	}

	/**
	 * @return the dependencies
	 * @uml.property name="dependencies"
	 */
	public final List<Dependency> getDependencies() {
		return dependencies;
	}

	/**
	 * @param dependencies
	 *            the dependencies to set
	 * @uml.property name="dependencies"
	 */
	public final void setDependencies(final List<Dependency> dependencies) {
		this.dependencies = dependencies;
	}

}
