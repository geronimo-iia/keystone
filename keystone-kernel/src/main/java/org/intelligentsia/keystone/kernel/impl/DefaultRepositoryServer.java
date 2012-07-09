/**
 * 
 */
package org.intelligentsia.keystone.kernel.impl;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;

import org.intelligentsia.keystone.api.artifacts.ResourceDoesNotExistException;
import org.intelligentsia.keystone.api.artifacts.TransferFailedException;
import org.intelligentsia.keystone.api.artifacts.repository.GroupRepositoryService;
import org.intelligentsia.keystone.api.artifacts.repository.RepositoryService;
import org.intelligentsia.keystone.kernel.RepositoryServer;

/**
 * @author geronimo
 *
 */
public class DefaultRepositoryServer implements RepositoryServer {

	/**
	 * GroupRepositoryService instance.
	 * 
	 * @uml.property name="repositoryService"
	 * @uml.associationEnd
	 */
	private GroupRepositoryService repositoryService;
	
	/**
	 * 
	 */
	public DefaultRepositoryServer() {
		repositoryService=new GroupRepositoryService();
	}

	/* (non-Javadoc)
	 * @see org.intelligentsia.keystone.api.artifacts.repository.RepositoryService#put(java.lang.String, java.io.File)
	 */
	@Override
	public void put(String resource, File source)
			throws ResourceDoesNotExistException, TransferFailedException {
		// TODO Auto-generated method stub
		repositoryService.put(resource, source);
	}

	/* (non-Javadoc)
	 * @see org.intelligentsia.keystone.api.artifacts.repository.RepositoryService#get(java.lang.String)
	 */
	@Override
	public File get(String resource) throws ResourceDoesNotExistException,
			TransferFailedException {
		// TODO Auto-generated method stub
		return repositoryService.get(resource);
	}

	/* (non-Javadoc)
	 * @see org.intelligentsia.keystone.api.artifacts.repository.RepositoryService#exists(java.lang.String)
	 */
	@Override
	public boolean exists(String resource) throws TransferFailedException {
		// TODO Auto-generated method stub
		return repositoryService.exists(resource);
	}

	/* (non-Javadoc)
	 * @see org.intelligentsia.keystone.api.artifacts.repository.RepositoryService#delete(java.lang.String)
	 */
	@Override
	public boolean delete(String resource) throws ResourceDoesNotExistException {
		// TODO Auto-generated method stub
		return repositoryService.delete(resource);
	}

	/* (non-Javadoc)
	 * @see org.intelligentsia.keystone.kernel.RepositoryServer#add(org.intelligentsia.keystone.api.artifacts.repository.RepositoryService)
	 */
	@Override
	public void add(RepositoryService repositoryService)
			throws NullPointerException {
		// TODO Auto-generated method stub
		this.repositoryService.add(repositoryService);
	}

	/* (non-Javadoc)
	 * @see org.intelligentsia.keystone.kernel.RepositoryServer#remove(org.intelligentsia.keystone.api.artifacts.repository.RepositoryService)
	 */
	@Override
	public void remove(RepositoryService repositoryService)
			throws NullPointerException {
		// TODO Auto-generated method stub
		this.repositoryService.remove(repositoryService);
	}

	@Override
	public Iterator<RepositoryService> iterator() {
		// TODO Auto-generated method stub
		return repositoryService.iterator();
	}

}
