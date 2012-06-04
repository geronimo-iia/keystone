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
	private String artifactId;
	private String groupId;
	private String version;
	private String relativePath;

	public Parent() {
		super();
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
	 * @return the relativePath
	 */
	public final String getRelativePath() {
		return relativePath;
	}

	/**
	 * @param relativePath
	 *            the relativePath to set
	 */
	public final void setRelativePath(final String relativePath) {
		this.relativePath = relativePath;
	}

}
