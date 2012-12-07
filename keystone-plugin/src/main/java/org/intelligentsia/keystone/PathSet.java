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
import java.util.ArrayList;
import java.util.List;

/**
 * PathSet.
 * 
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class PathSet implements Serializable {

	/**
	 * serialVersionUID:long
	 */
	private static final long serialVersionUID = -4094326771866885545L;

	 /**
     * @parameter
     */
	private List<String> paths;

	/**
	 * Build a new instance of PathSet.
	 */
	public PathSet() {
		super();
	}

	public void addPath(String path) {
		getPaths().add(path);
	}

	public void removePath(String path) {
		getPaths().remove(path);
	}

	public List<String> getPaths() {
		if (paths == null) {
			paths = new ArrayList<String>();
		}
		return paths;
	}

	public void setPaths(List<String> paths) {
		this.paths = paths;
	}

}
