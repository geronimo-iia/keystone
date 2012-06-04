/**
 * 
 */
package org.intelligentsia.keystone.api.artifacts;

import org.intelligentsia.keystone.api.artifacts.pom.Pom;
import org.intelligentsia.keystone.api.artifacts.repository.metadata.Metadata;

/**
 * Artifacts Service API.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public interface ArtifactsService {

	/**
	 * Downloads specified artifact from repositories.
	 * 
	 * @param identifier
	 *            Artifact identifier
	 * @return Resource instance.
	 * @throws ResourceDoesNotExistException
	 *             if source does not exists
	 * @throws TransferFailedException
	 *             if error occurs when transferring data.
	 */
	public Resource get(final ArtifactIdentifier identifier) throws ResourceDoesNotExistException, TransferFailedException;

	/**
	 * @param identifier
	 *            artifact Identifier
	 * @return Metadata instance for specified artifact
	 * @throws ResourceDoesNotExistException
	 *             if source does not exists
	 * @throws TransferFailedException
	 *             if error occurs when transferring data.
	 */
	public Metadata getMetadata(final ArtifactIdentifier identifier) throws ResourceDoesNotExistException, TransferFailedException;

	/**
	 * Resolve an ArtifactIdentifier for specified parameter.
	 * 
	 * @param group
	 *            group identifier
	 * @param artifact
	 *            artifact identifier
	 * @param releaseOnly
	 *            if true, try to resolve artifact without snapshot
	 * @return artifact identifier
	 * @throws ResourceDoesNotExistException
	 *             if source does not exists
	 * @throws TransferFailedException
	 *             if error occurs when transferring data.
	 */
	public ArtifactIdentifier resolve(final String group, final String artifact, boolean releaseOnly) throws ResourceDoesNotExistException, TransferFailedException;

	/**
	 * @param identifier
	 *            artifact Identifier
	 * @return pom instance for specified artifact. If artifact must be
	 *         resolved, only release will be used.
	 * @throws ResourceDoesNotExistException
	 *             if source does not exists
	 * @throws TransferFailedException
	 *             if error occurs when transferring data.
	 */
	public Pom getPom(final ArtifactIdentifier identifier) throws ResourceDoesNotExistException, TransferFailedException;

}
