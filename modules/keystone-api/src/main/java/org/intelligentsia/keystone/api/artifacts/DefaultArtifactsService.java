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

import static org.intelligentsia.keystone.api.artifacts.PathResolver.resolveMetadata;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.intelligentsia.keystone.api.StringUtils;
import org.intelligentsia.keystone.api.artifacts.pom.Pom;
import org.intelligentsia.keystone.api.artifacts.repository.RepositoryService;
import org.intelligentsia.keystone.api.artifacts.repository.metadata.Metadata;

import com.fasterxml.jackson.xml.XmlMapper;

/**
 * DefaultArtifactsService implements ArtifactsService API.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class DefaultArtifactsService implements ArtifactsService {
	/**
	 * inner repository
	 * 
	 * @uml.property name="service"
	 * @uml.associationEnd
	 */
	private final RepositoryService service;
	/**
	 * Jackson Object XML Mapper.
	 */
	private final ObjectMapper mapper = new XmlMapper().configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, Boolean.FALSE);

	/**
	 * Build a new instance of <code>DefaultArtifactsService</code>
	 * 
	 * @param service
	 */
	public DefaultArtifactsService(final RepositoryService service) {
		super();
		this.service = service;
	}

	/**
	 * Resolve an ArtifactIdentifier for specified parameter.
	 * 
	 * @param group
	 *            group identifier
	 * @param artifact
	 *            artifact identifier
	 * @param releaseOnly
	 *            if true, try to resolve artifact without snapshot
	 * @return artifact identifier throws ResourceDoesNotExistException
	 */
	@Override
	public ArtifactIdentifier resolve(final String group, final String artifact, final boolean releaseOnly) throws ResourceDoesNotExistException {
		final ArtifactIdentifier identifier = new ArtifactIdentifier(group, artifact, null);
		final Metadata metadata = getMetadata(identifier);
		if (metadata == null) {
			throw new ResourceDoesNotExistException("no metadata for " + identifier.toString());
		}
		String version = null;
		if (releaseOnly) {
			version = metadata.getVersioning().getRelease();
		} else {
			version = metadata.getVersioning().getLatest();
			// if latest is not feed
			if ((version == null) || "".equals(version)) {
				version = metadata.getVersioning().getRelease();
			}
		}
		if ((version == null) || "".equals(version)) {
			throw new ResourceDoesNotExistException("no version information for " + identifier.toString());
		}
		return new ArtifactIdentifier(group, artifact, version);
	}

	/**
	 * @see org.intelligentsia.keystone.api.artifacts.ArtifactsService#get(org.intelligentsia.keystone.api.artifacts.ArtifactIdentifier)
	 */
	@Override
	public Resource get(final ArtifactIdentifier artifactIdentifier) throws ResourceDoesNotExistException {
		return get(artifactIdentifier, Boolean.TRUE);
	}

	/**
	 * Downloads specified artifact from repositories.
	 * 
	 * @param artifactIdentifier
	 *            artifact Identifier
	 * @param releaseOnly
	 *            used if artifact need to be resolved @see resolve.
	 * @return Resource instance.
	 * @throws ResourceDoesNotExistException
	 *             if source does not exists
	 * @throws TransferFailedException
	 *             if error occurs when transferring data.
	 */
	public Resource get(final ArtifactIdentifier artifactIdentifier, final boolean releaseOnly) throws ResourceDoesNotExistException, TransferFailedException {
		Resource resource = null;
		// resolve if needed
		ArtifactIdentifier identifier = null;
		if ((artifactIdentifier.getVersion() == null) || "".equals(artifactIdentifier.getVersion())) {
			identifier = resolve(artifactIdentifier.getGroupId(), artifactIdentifier.getArtifactId(), releaseOnly);
		} else {
			identifier = new ArtifactIdentifier(artifactIdentifier);
		}
		if (identifier == null) {
			throw new ResourceDoesNotExistException("Cant resolve " + artifactIdentifier.toString());
		}
		// POM
		final String pomResource = getPomResource(identifier, releaseOnly);
		final Pom pom = getPom(pomResource);
		if (pom == null) {
			throw new ResourceDoesNotExistException("Unable to locate pom " + pomResource);
		}
		// which packaging
		final String packaging = pom.getPackaging();
		if ((packaging == null) || "".equals(packaging)) {
			throw new ResourceDoesNotExistException("Illegal packaging for pom " + pomResource);
		}
		final StringBuilder resolved = new StringBuilder(pomResource);
		resolved.setLength(pomResource.length() - 3); // remove 'pom'
		resolved.append(packaging);
		// final resource
		resource = new Resource();
		resource.setArtefactIdentifier(identifier);
		resource.setName(resolved.toString());
		resource.setLocalFile(service.get(resource.getName()));
		return resource;
	}

	/**
	 * @see org.intelligentsia.keystone.api.artifacts.ArtefactsService#getMetadata(org.intelligentsia.keystone.api.artifacts.ArtifactIdentifier)
	 */
	@Override
	public Metadata getMetadata(final ArtifactIdentifier artefactIdentifier) throws TransferFailedException {
		Metadata metadata = null;
		final String resource = resolveMetadata(artefactIdentifier);
		try {
			metadata = mapper.readValue(service.get(resource), Metadata.class);
		} catch (final JsonParseException e) {
			throw new TransferFailedException(StringUtils.format("(ArtifactsService/getMetadata %s)", artefactIdentifier.toString()), e);
		} catch (final JsonMappingException e) {
			throw new TransferFailedException(StringUtils.format("(ArtifactsService/getMetadata %s)", artefactIdentifier.toString()), e);
		} catch (final IOException e) {
			throw new TransferFailedException(StringUtils.format("(ArtifactsService/getMetadata %s)", artefactIdentifier.toString()), e);
		}
		return metadata;
	}

	/**
	 * @see org.intelligentsia.keystone.api.artifacts.ArtifactsService#getPom(org.intelligentsia.keystone.api.artifacts.ArtifactIdentifier)
	 */
	@Override
	public Pom getPom(final ArtifactIdentifier artifactIdentifier) throws ResourceDoesNotExistException, TransferFailedException {
		return getPom(getPomResource(artifactIdentifier, Boolean.TRUE));
	}

	/**
	 * @param artifactIdentifier
	 * @param releaseOnly
	 * @return POM resource
	 * @throws ResourceDoesNotExistException
	 *             if source does not exists
	 * @throws TransferFailedException
	 *             if error occurs when transferring data.
	 */
	private String getPomResource(final ArtifactIdentifier artifactIdentifier, final boolean releaseOnly) throws ResourceDoesNotExistException, TransferFailedException {
		ArtifactIdentifier identifier = null;
		if ((artifactIdentifier.getVersion() == null) || "".equals(artifactIdentifier.getVersion())) {
			identifier = resolve(artifactIdentifier.getGroupId(), artifactIdentifier.getArtifactId(), releaseOnly);
		} else {
			identifier = new ArtifactIdentifier(artifactIdentifier);
		}
		if (identifier == null) {
			throw new ResourceDoesNotExistException("Cant resolve " + artifactIdentifier.toString());
		}
		final StringBuilder resolved = PathResolver.getBase(identifier, Boolean.TRUE);
		resolved.append(identifier.getArtifactId()).append("-").append(identifier.getVersion());
		// start snapshot resolution
		if (identifier.getVersion().endsWith("SNAPSHOT")) {
			// get correct snapshot
			final Metadata metadata = getMetadata(identifier);
			if (metadata == null) {
				throw new ResourceDoesNotExistException("no metadata for " + identifier.toString());
			}
			resolved.append("-").append(metadata.getVersioning().getSnapshot().getTimestamp());
		}
		return resolved.append(".pom").toString();
	}

	/**
	 * @param resource
	 *            pom resource
	 * @return pom instance.
	 * @throws TransferFailedException
	 *             if error occurs when transferring data.
	 */
	private Pom getPom(final String resource) throws TransferFailedException {
		Pom pom = null;
		try {
			pom = mapper.readValue(service.get(resource), Pom.class);
		} catch (final JsonParseException e) {
			throw new TransferFailedException(StringUtils.format("(ArtifactsService/getPom %s)", pom), e);
		} catch (final JsonMappingException e) {
			throw new TransferFailedException(StringUtils.format("(ArtifactsService/getPom %s)", pom), e);
		} catch (final IOException e) {
			throw new TransferFailedException(StringUtils.format("(ArtifactsService/getPom %s)", pom), e);
		}
		return pom;
	}
}
