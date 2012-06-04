/**
 * 
 */
package org.intelligentsia.keystone.api.artifacts.pom;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * http://maven.apache.org/ref/3.0.3/maven-model/maven.html
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Pom implements Serializable {

	private static final long serialVersionUID = 6397991212624047355L;
	private String modelVersion;
	private Parent parent;
	private String groupId;
	private String artifactId;
	private String version;
	private String packaging;

	private String name;
	private String description;
	private String url;
	private String inceptionYear;

	private Organization organization;
	private List<Licence> licenses;
	private List<Developer> developers;
	private List<Contributor> contributors;

	private List<String> modules;
	private DependencyManagement dependencyManagement;
	private List<Dependency> dependencies;

	public Pom() {
		super();
	}

	/**
	 * @return the modelVersion
	 */
	public final String getModelVersion() {
		return modelVersion;
	}

	/**
	 * @param modelVersion
	 *            the modelVersion to set
	 */
	public final void setModelVersion(final String modelVersion) {
		this.modelVersion = modelVersion;
	}

	/**
	 * @return the parent
	 */
	public final Parent getParent() {
		return parent;
	}

	/**
	 * @param parent
	 *            the parent to set
	 */
	public final void setParent(final Parent parent) {
		this.parent = parent;
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
	 * @return the packaging
	 */
	public final String getPackaging() {
		return packaging;
	}

	/**
	 * @param packaging
	 *            the packaging to set
	 */
	public final void setPackaging(final String packaging) {
		this.packaging = packaging;
	}

	/**
	 * @return the name
	 */
	public final String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public final void setName(final String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public final String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public final void setDescription(final String description) {
		this.description = description;
	}

	/**
	 * @return the url
	 */
	public final String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public final void setUrl(final String url) {
		this.url = url;
	}

	/**
	 * @return the inceptionYear
	 */
	public final String getInceptionYear() {
		return inceptionYear;
	}

	/**
	 * @param inceptionYear
	 *            the inceptionYear to set
	 */
	public final void setInceptionYear(final String inceptionYear) {
		this.inceptionYear = inceptionYear;
	}

	/**
	 * @return the organization
	 */
	public final Organization getOrganization() {
		return organization;
	}

	/**
	 * @param organization
	 *            the organization to set
	 */
	public final void setOrganization(final Organization organization) {
		this.organization = organization;
	}

	/**
	 * @return the licenses
	 */
	public final List<Licence> getLicenses() {
		return licenses;
	}

	/**
	 * @param licenses
	 *            the licenses to set
	 */
	public final void setLicenses(final List<Licence> licenses) {
		this.licenses = licenses;
	}

	/**
	 * @return the developers
	 */
	public final List<Developer> getDevelopers() {
		return developers;
	}

	/**
	 * @param developers
	 *            the developers to set
	 */
	public final void setDevelopers(final List<Developer> developers) {
		this.developers = developers;
	}

	/**
	 * @return the contributors
	 */
	public final List<Contributor> getContributors() {
		return contributors;
	}

	/**
	 * @param contributors
	 *            the contributors to set
	 */
	public final void setContributors(final List<Contributor> contributors) {
		this.contributors = contributors;
	}

	/**
	 * @return the modules
	 */
	public final List<String> getModules() {
		return modules;
	}

	/**
	 * @param modules
	 *            the modules to set
	 */
	public final void setModules(final List<String> modules) {
		this.modules = modules;
	}

	/**
	 * @return the dependencyManagement
	 */
	public final DependencyManagement getDependencyManagement() {
		return dependencyManagement;
	}

	/**
	 * @param dependencyManagement
	 *            the dependencyManagement to set
	 */
	public final void setDependencyManagement(final DependencyManagement dependencyManagement) {
		this.dependencyManagement = dependencyManagement;
	}

	/**
	 * @return the dependencies
	 */
	public final List<Dependency> getDependencies() {
		return dependencies;
	}

	/**
	 * @param dependencies
	 *            the dependencies to set
	 */
	public final void setDependencies(final List<Dependency> dependencies) {
		this.dependencies = dependencies;
	}

}
