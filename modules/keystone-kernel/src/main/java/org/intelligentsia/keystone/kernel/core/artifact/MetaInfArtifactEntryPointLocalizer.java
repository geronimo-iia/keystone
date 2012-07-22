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
package org.intelligentsia.keystone.kernel.core.artifact;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.intelligentsia.keystone.api.artifacts.KeystoneRuntimeException;
import org.intelligentsia.keystone.kernel.ArtifactContext;
import org.intelligentsia.keystone.kernel.ArtifactEntryPoint;
import org.intelligentsia.keystone.kernel.ArtifactEntryPointLocalizer;
import org.intelligentsia.keystone.kernel.core.ResourceFinder;
import org.intelligentsia.utilities.StringUtils;

/**
 * {@link MetaInfArtifactEntryPointLocalizer} implements
 * {@link ArtifactEntryPointLocalizer} which try to find a ressource, localized
 * under folder:
 * 
 * <pre>
 * &quot;META-INF/services/org.intelligentsia.keystone.kernel.ArtifactEntryPoint&quot;
 * </pre>
 * 
 * and define class name implementation.
 * 
 * 
 */
public class MetaInfArtifactEntryPointLocalizer implements ArtifactEntryPointLocalizer {

	/**
	 * Build a new instance of MetaInfArtifactEntryPointLocalizer.java.
	 */
	public MetaInfArtifactEntryPointLocalizer() {
		super();
	}

	@Override
	public ArtifactEntryPoint localize(final ArtifactContext artifactContext) throws NullPointerException, KeystoneRuntimeException {
		final ArtifactEntryPoint artifactEntryPoint = null;
		String className = null;
		if (artifactContext != null) {
			// build a resource finder
			final ResourceFinder finder = new ResourceFinder(artifactContext.getLocalResource());
			try {
				// find target ?
				final URL definition = finder.find("META-INF/services/org.intelligentsia.keystone.kernel.ArtifactEntryPoint");
				className = formatClassName(readContents(definition));
				if (className != null) {
					final Class<?> clazz = finder.findClass(className);
					if (!ArtifactEntryPoint.class.isAssignableFrom(clazz)) {
						throw new KeystoneRuntimeException(StringUtils.format("Specified class %s not implements org.intelligentsia.keystone.kernel.ArtifactEntryPoint", className));
					}
					return (ArtifactEntryPoint) clazz.newInstance();
				}
			} catch (final IOException e) {
				// no resource => nothing to do
			} catch (final ClassNotFoundException e) {
				throw new KeystoneRuntimeException(StringUtils.format("Specified class %s not found", className));
			} catch (final InstantiationException e) {
				throw new KeystoneRuntimeException(StringUtils.format("Cannot instantiate class '%s'. %s", className, e.getMessage()));
			} catch (final IllegalAccessException e) {
				throw new KeystoneRuntimeException(StringUtils.format("Cannot instantiate class '%s'. %s", className, e.getMessage()));
			}
		}
		return artifactEntryPoint;
	}

	/**
	 * Format value as class name: removing white space and comment.
	 * 
	 * @param value
	 * @return formated value
	 */
	public String formatClassName(final String value) {
		String line = value;
		if (line != null) {
			// remove comment
			final int index = line.indexOf("#");
			if (index > -1) {
				line = line.substring(0, index - 1);
			}
			// remove white space
			line = line.trim();
			// empty is null
			if ("".equals(line)) {
				line = null;
			}
		}
		return line;
	}

	/**
	 * Read content specified resource location
	 * 
	 * @param resource
	 * @return content as string
	 * @throws KeystoneRuntimeException
	 *             if error occurs
	 */
	public String readContents(final URL resource) throws KeystoneRuntimeException {
		BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(resource.openStream()));
			final StringBuilder builder = new StringBuilder();
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				builder.append(line);
			}
			return builder.toString();
		} catch (final IOException e) {
			throw new KeystoneRuntimeException("error when reading content of " + resource.toString(), e);
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (final IOException e) {
				}
			}
		}
	}
}
