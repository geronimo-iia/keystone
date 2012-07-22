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
package org.intelligentsia.keystone.kernel.event;

import org.intelligentsia.keystone.api.artifacts.ArtifactIdentifier;
import org.intelligentsia.keystone.kernel.ArtifactContext;
import org.intelligentsia.utilities.Preconditions;

/**
 * ArtifactContextChangeEvent is raised when an {@link ArtifactContext} is
 * initialized or destroyed.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class ArtifactContextChangeEvent {

	public enum State {
		INITIALIZED, DESTROYED
	}

	private final ArtifactIdentifier artifactIdentifier;

	private final State state;

	/**
	 * Build a new instance of ArtifactContextChangeEvent.java.
	 * 
	 * @param artifactIdentifier
	 * @param state
	 * @throws NullPointerException
	 *             if artifactContext or state is null
	 */
	public ArtifactContextChangeEvent(final ArtifactIdentifier artifactIdentifier, final State state) throws NullPointerException {
		super();
		this.artifactIdentifier = Preconditions.checkNotNull(artifactIdentifier, "artifactIdentifier");
		this.state = Preconditions.checkNotNull(state, "state");
	}

	/**
	 * @return the artifactIdentifier
	 */
	public final ArtifactIdentifier getArtifactIdentifier() {
		return artifactIdentifier;
	}

	/**
	 * @return the state
	 */
	public final State getState() {
		return state;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ArtifactContextChangeEvent [artifactIdentifier=" + artifactIdentifier + ", state=" + state + "]";
	}

}
