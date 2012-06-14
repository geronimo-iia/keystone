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
package org.intelligentsia.keystone.api.artifacts;

import java.io.File;

/**
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class Resource {

	private ArtifactIdentifier artefactIdentifier;

	private String name;

	private File localFile;

	/**
	 * Build a new instance of <code>Resource</code>
	 */
	public Resource() {
		super();
	}

	public ArtifactIdentifier getArtefactIdentifier() {
		return this.artefactIdentifier;
	}

	public void setArtefactIdentifier(final ArtifactIdentifier artefactIdentifier) {
		this.artefactIdentifier = artefactIdentifier;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public File getLocalFile() {
		return this.localFile;
	}

	public void setLocalFile(final File localFile) {
		this.localFile = localFile;
	}

}
