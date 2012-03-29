/**
 * 
 */
package org.intelligentsia.keystone.api.artifacts.repository;

import java.io.File;

import org.intelligentsia.keystone.api.artifacts.ResourceDoesNotExistException;
import org.intelligentsia.keystone.api.artifacts.TransferFailedException;

/**
 * Repository Service Interface.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface RepositoryService {
	/**
	 * Put a new resource.
	 * 
	 * @param resource
	 *            resource destination.
	 * @param source
	 *            source file.
	 * @throws ResourceDoesNotExistException
	 *             if source does not exists
	 * @throws TransferFailedException
	 *             if error occurs when transferring data.
	 */
	public void put(String resource, File source) throws ResourceDoesNotExistException, TransferFailedException;

	/**
	 * @param resource
	 * @return a file instance of specified resource.
	 * @throws ResourceDoesNotExistException
	 *             if source does not exists
	 * @throws TransferFailedException
	 *             if error occurs when transferring data.
	 */
	public File get(String resource) throws ResourceDoesNotExistException, TransferFailedException;

	/**
	 * @param resource
	 * @return true if resource exist.
	 * @throws TransferFailedException
	 *             if error occurs when transferring data.
	 */
	public boolean exists(String resource) throws TransferFailedException;

	/**
	 * @param resource
	 *            resource to delete
	 * @throws ResourceDoesNotExistException
	 *             if source does not exists
	 * @throws TransferFailedException
	 *             if error occurs when transferring data.
	 * @return true if delete is completed
	 */
	public boolean delete(String resource) throws ResourceDoesNotExistException;
}
