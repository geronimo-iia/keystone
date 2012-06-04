/**
 * 
 */
package org.intelligentsia.keystone.api.artifacts.repository;

import java.io.Serializable;

/**
 * a Repository bean.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
/**
 * <code></code>.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 *         16:06:08
 */
public class Repository implements Serializable {

	private static final long serialVersionUID = -7481265013458675666L;
	private String id;
	private String url;

	/**
	 * Build a new instance of <code>Repository</code>
	 */
	public Repository() {
		super();
	}

	/**
	 * Build a new instance of <code>Repository</code>
	 * 
	 * @param id
	 * @param url
	 */
	public Repository(final String id, final String url) {
		super();
		this.id = id;
		this.url = url;
	}

	public String getId() {
		return this.id;
	}

	/**
	 * @param id
	 */
	public void setId(final String id) {
		this.id = id;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(final String url) {
		this.url = url;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Repository [" + (this.id != null ? "id=" + this.id + ", " : "") + (this.url != null ? "url=" + this.url : "") + "]";
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((this.id == null) ? 0 : this.id.hashCode());
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
		final Repository other = (Repository) obj;
		if (this.id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!this.id.equals(other.id)) {
			return false;
		}
		return true;
	}

}
