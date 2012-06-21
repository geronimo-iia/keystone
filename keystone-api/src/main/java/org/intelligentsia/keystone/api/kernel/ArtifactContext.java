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
package org.intelligentsia.keystone.api.kernel;

import java.net.URL;

import org.intelligentsia.keystone.api.artifacts.ArtifactIdentifier;

/**
 * ArtifactContext declare all attribut member.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public interface ArtifactContext {

	/**
	 * @return the artifactIdentifier
	 */
	public ArtifactIdentifier getArtifactIdentifier();

	/**
	 * @return the localResource
	 */
	public URL getLocalResource();

	/**
	 * @return the classLoader
	 */
	public ClassLoader getClassLoader();

	/**
	 * @return the isolationLevel
	 */
	public IsolationLevel getIsolationLevel();
}
