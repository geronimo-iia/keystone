package org.intelligentsia.keystone.kernel;

import java.util.Collection;

import org.intelligentsia.keystone.api.artifacts.ArtifactIdentifier;
import org.intelligentsia.keystone.api.artifacts.KeystoneRuntimeException;
 

/**
 * 
 * ArtifactServer.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public interface ArtifactServer {
	/**
	 * @return an unmodifiable collection of loaded artifact identifier.
	 */
	public Collection<ArtifactIdentifier> findAllArtifactIdentifiers();

	/**
	 * Check if an artifact with the specified artifact identifier is managed by
	 * this instance of kernel service.
	 * 
	 * @param artifactIdentifier
	 *            artifact Identifier
	 * @return true if specified artifact Identifier is managed by this kernel
	 *         instance.
	 * @throws NullPointerException
	 *             if artifactIdentifier is null
	 */
	public boolean contains(ArtifactIdentifier artifactIdentifier) throws NullPointerException;

	/**
	 * Find specified artifact.
	 * 
	 * @param artifactIdentifier
	 * @return ArtifactContext instance or null if none is found
	 * @throws KeystoneRuntimeException
	 *             if an error occurs
	 * @throws NullPointerException
	 *             if artifactIdentifier is null
	 */
	public ArtifactContext find(ArtifactIdentifier artifactIdentifier) throws KeystoneRuntimeException, NullPointerException;

	/**
	 * Load specified artefact.
	 * 
	 * @param artifactIdentifier
	 *            artifact Identifier
	 * @param isolationLevel
	 *            isolation level
	 * @throws KeystoneRuntimeException
	 *             if error occurs
	 * @return an ArtifactContext instance.
	 * @throws NullPointerException
	 *             if artifactIdentifier or {@link IsolationLevel} is null
	 */
	public ArtifactContext load(ArtifactIdentifier artifactIdentifier, IsolationLevel isolationLevel) throws KeystoneRuntimeException, NullPointerException;

	/**
	 * Unload specified artefact.
	 * 
	 * @param artifactIdentifier
	 *            artifact Identifier
	 * @throws KeystoneRuntimeException
	 *             if error occurs
	 * @throws NullPointerException
	 *             if artifactIdentifier is null
	 */
	public void unload(ArtifactIdentifier artifactIdentifier) throws KeystoneRuntimeException, NullPointerException;
}
