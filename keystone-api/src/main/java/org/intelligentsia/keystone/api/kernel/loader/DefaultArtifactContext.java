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
package org.intelligentsia.keystone.api.kernel.loader;

import java.net.URL;

import org.intelligentsia.keystone.api.artifacts.ArtifactIdentifier;
import org.xeustechnologies.jcl.JarClassLoader;

/**
 * DefaultArtifactContext act as a context to a loaded artifact.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class DefaultArtifactContext implements ArtifactContext {

	/**
	 * Artifact identifier.
	 */
	private final ArtifactIdentifier artifactIdentifier;
	/**
	 * Local URL resource.
	 */
	private URL localResource;

	/**
	 * Classloader instance used to load this artifact.
	 */
	private JarClassLoader classLoader;

	/**
	 * Isolation Level used.
	 */
	private IsolationLevel isolationLevel;

	/**
	 * Build a new instance of ArtifactContext.java.
	 * 
	 * @param artifactIdentifier
	 *            artifact Identifier
	 * @throws NullPointerException
	 *             if artifactIdentifier is null
	 */
	public DefaultArtifactContext(final ArtifactIdentifier artifactIdentifier) throws NullPointerException {
		super();
		if (artifactIdentifier == null) {
			throw new NullPointerException("artifactIdentifier is null");
		}
		this.artifactIdentifier = artifactIdentifier;
	}

	/**
	 * @return the localResource
	 */
	@Override
	public URL getLocalResource() {
		return localResource;
	}

	/**
	 * @param localResource
	 *            the localResource to set
	 */
	public void setLocalResource(final URL localResource) {
		this.localResource = localResource;
	}

	/**
	 * @return the classLoader
	 */
	@Override
	public ClassLoader getClassLoader() {
		return classLoader;
	}

	/**
	 * @param classLoader
	 *            the classLoader to set
	 */
	public void setClassLoader(final JarClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	/**
	 * @return the isolationLevel
	 */
	@Override
	public IsolationLevel getIsolationLevel() {
		return isolationLevel;
	}

	/**
	 * @param isolationLevel
	 *            the isolationLevel to set
	 */
	public void setIsolationLevel(final IsolationLevel isolationLevel) {
		this.isolationLevel = isolationLevel;
	}

	/**
	 * @return the artifactIdentifier
	 */
	@Override
	public ArtifactIdentifier getArtifactIdentifier() {
		return artifactIdentifier;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((artifactIdentifier == null) ? 0 : artifactIdentifier.hashCode());
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
		final DefaultArtifactContext other = (DefaultArtifactContext) obj;
		if (artifactIdentifier == null) {
			if (other.artifactIdentifier != null) {
				return false;
			}
		} else if (!artifactIdentifier.equals(other.artifactIdentifier)) {
			return false;
		}
		return true;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ArtifactContext [artifactIdentifier=" + artifactIdentifier + ", isolationLevel=" + isolationLevel + "]";
	}

}
