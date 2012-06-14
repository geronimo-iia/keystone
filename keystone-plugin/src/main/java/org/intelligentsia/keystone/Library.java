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
	public final void setName(final String name) {
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
	public final void setPath(final String path) {
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
