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
public class DependencyManagement implements Serializable {

	private static final long serialVersionUID = -36118210357022081L;
	private List<Dependency> dependencies;

	public DependencyManagement() {
		super();
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
