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
package org.intelligentsia.keystone.api.kernel.handler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.intelligentsia.keystone.api.kernel.ArtifactContext;

/**
 * 
 * CompositeArtifactLoaderHandler implement a composite handler.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class CompositeArtifactLoaderHandler implements ArtifactLoaderHandler, Iterable<ArtifactLoaderHandler> {

	private final List<ArtifactLoaderHandler> artifactLoaderHandlers = new ArrayList<ArtifactLoaderHandler>();

	/**
	 * Build a new instance of CompositeArtifactLoaderHandler.java.
	 */
	public CompositeArtifactLoaderHandler() {
		this(null);
	}

	/**
	 * 
	 * Build a new instance of CompositeArtefactLoaderHandler.java.
	 * 
	 * @param artifactLoaderHandlers
	 *            a list of ArtefactLoaderHandler instance.
	 */
	public CompositeArtifactLoaderHandler(final List<ArtifactLoaderHandler> artifactLoaderHandlers) {
		super();
		if (artifactLoaderHandlers != null) {
			this.artifactLoaderHandlers.addAll(artifactLoaderHandlers);
		}
	}

	@Override
	public void handle(ArtifactContext context) {
		for (ArtifactLoaderHandler handler : artifactLoaderHandlers) {
			handler.handle(context);
		}
	}

	public boolean isEmpty() {
		return artifactLoaderHandlers.isEmpty();
	}

	public Iterator<ArtifactLoaderHandler> iterator() {
		return artifactLoaderHandlers.iterator();
	}

	public boolean add(ArtifactLoaderHandler handler) {
		return artifactLoaderHandlers.add(handler);
	}

	public boolean remove(ArtifactLoaderHandler handler) {
		return artifactLoaderHandlers.remove(handler);
	}

	public void clear() {
		artifactLoaderHandlers.clear();
	}

}
