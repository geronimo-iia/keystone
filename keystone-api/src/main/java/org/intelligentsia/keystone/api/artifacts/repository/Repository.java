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
package org.intelligentsia.keystone.api.artifacts.repository;

import java.io.Serializable;

/**
 * a Repository bean.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
/**
 * a Repository bean.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class Repository implements Serializable {

	private static final long serialVersionUID = -7481265013458675666L;
	/**
	 * @uml.property name="id"
	 */
	private String id;
	/**
	 * @uml.property name="url"
	 */
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

	/**
	 * @return
	 * @uml.property name="id"
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * @param id
	 * @uml.property name="id"
	 */
	public void setId(final String id) {
		this.id = id;
	}

	/**
	 * @return
	 * @uml.property name="url"
	 */
	public String getUrl() {
		return this.url;
	}

	/**
	 * @param url
	 * @uml.property name="url"
	 */
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
