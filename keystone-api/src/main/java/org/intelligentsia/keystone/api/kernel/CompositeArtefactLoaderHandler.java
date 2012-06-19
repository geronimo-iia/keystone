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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.intelligentsia.keystone.api.artifacts.Resource;

/**
 * 
 * CompositeArtefactLoaderHandler implement a composite handler.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class CompositeArtefactLoaderHandler implements ArtefactLoaderHandler, Iterable<ArtefactLoaderHandler> {

	private final List<ArtefactLoaderHandler> artefactLoaderHandlers = new ArrayList<ArtefactLoaderHandler>();

	/**
	 * Build a new instance of CompositeArtefactLoaderHandler.java.
	 */
	public CompositeArtefactLoaderHandler(final List<ArtefactLoaderHandler> artefactLoaderHandlers) {
		super();
		if (artefactLoaderHandlers != null)
			this.artefactLoaderHandlers.addAll(artefactLoaderHandlers);
	}

	@Override
	public void handle(Resource resource) {
		for (ArtefactLoaderHandler handler : artefactLoaderHandlers) {
			handler.handle(resource);
		}
	}

	public boolean isEmpty() {
		return artefactLoaderHandlers.isEmpty();
	}

	public Iterator<ArtefactLoaderHandler> iterator() {
		return artefactLoaderHandlers.iterator();
	}

	public boolean add(ArtefactLoaderHandler handler) {
		return artefactLoaderHandlers.add(handler);
	}

	public boolean remove(ArtefactLoaderHandler handler) {
		return artefactLoaderHandlers.remove(handler);
	}

}
