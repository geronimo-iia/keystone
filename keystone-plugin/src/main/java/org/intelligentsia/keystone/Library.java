/**
 * 
 */
package org.intelligentsia.keystone;

import java.io.Serializable;

/**
 * Library.
 * 
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class Library implements Serializable {

	/**
	 * serialVersionUID:long
	 */
	private static final long serialVersionUID = -4094326771866885545L;
	/**
	 * Library name without extention
	 * 
	 * @parameter expression="${name}"
	 */
	private String name;
	/**
	 * Path of this library.
	 * 
	 * @parameter expression="${path}"
	 */
	private String path;

	/**
	 * Build a new instance of Library.
	 */
	public Library() {
		super();
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
	public final void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the path
	 */
	public final String getPath() {
		return path;
	}

	/**
	 * @param path
	 *            the path to set
	 */
	public final void setPath(String path) {
		this.path = path;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Library [" + (name != null ? "name=" + name + ", " : "") + (path != null ? "path=" + path : "") + "]";
	}

}
