/**
 * 
 */
package org.intelligentsia.keystone.kernel.impl;

import java.io.File;
import java.util.Iterator;

import org.intelligentsia.keystone.api.artifacts.ResourceDoesNotExistException;
import org.intelligentsia.keystone.api.artifacts.TransferFailedException;
import org.intelligentsia.keystone.api.artifacts.repository.GroupRepositoryService;
import org.intelligentsia.keystone.api.artifacts.repository.RepositoryService;
import org.intelligentsia.keystone.kernel.RepositoryServer;

/**
 * DefaultRepositoryServer implements {@link RepositoryServer} by delegating to
 * a {@link GroupRepositoryService} instance.
 * 
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class DefaultRepositoryServer implements RepositoryServer {

	/**
	 * {@link GroupRepositoryService} instance.
	 * 
	 * @uml.property name="repositoryService"
	 * @uml.associationEnd
	 */
	private GroupRepositoryService groupRepositoryService;

	/**
	 * Build a new instance of DefaultRepositoryServer.java.
	 */
	public DefaultRepositoryServer() {
		groupRepositoryService = new GroupRepositoryService();
	}

	/**
	 * @see org.intelligentsia.keystone.api.artifacts.repository.RepositoryService#put(java.lang.String,
	 *      java.io.File)
	 */
	@Override
	public void put(String resource, File source) throws ResourceDoesNotExistException, TransferFailedException {
		groupRepositoryService.put(resource, source);
	}

	/**
	 * @see org.intelligentsia.keystone.api.artifacts.repository.RepositoryService#get(java.lang.String)
	 */
	@Override
	public File get(String resource) throws ResourceDoesNotExistException, TransferFailedException {
		return groupRepositoryService.get(resource);
	}

	/**
	 * @see org.intelligentsia.keystone.api.artifacts.repository.RepositoryService#exists(java.lang.String)
	 */
	@Override
	public boolean exists(String resource) throws TransferFailedException {
		return groupRepositoryService.exists(resource);
	}

	/**
	 * @see org.intelligentsia.keystone.api.artifacts.repository.RepositoryService#delete(java.lang.String)
	 */
	@Override
	public boolean delete(String resource) throws ResourceDoesNotExistException {
		return groupRepositoryService.delete(resource);
	}

	/**
	 * @see org.intelligentsia.keystone.kernel.RepositoryServer#add(org.intelligentsia.keystone.api.artifacts.repository.RepositoryService)
	 */
	@Override
	public void add(RepositoryService repositoryService) throws NullPointerException {
		if (repositoryService == null) {
			throw new NullPointerException("repositoryService");
		}
		if (!this.groupRepositoryService.contains(repositoryService)) {
			this.groupRepositoryService.add(repositoryService);
		}
	}

	/**
	 * @see org.intelligentsia.keystone.kernel.RepositoryServer#remove(org.intelligentsia.keystone.api.artifacts.repository.RepositoryService)
	 */
	@Override
	public void remove(RepositoryService repositoryService) throws NullPointerException {
		if (repositoryService == null) {
			throw new NullPointerException("repositoryService");
		}
		this.groupRepositoryService.remove(repositoryService);
	}

	@Override
	public Iterator<RepositoryService> iterator() {
		return groupRepositoryService.iterator();
	}
}
