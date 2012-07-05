package org.intelligentsia.keystone.kernel;

import java.util.Collection;

import org.intelligentsia.keystone.api.artifacts.repository.RepositoryService;

/**
 * RepositoryServer act as a {@link RepositoryService} and declare methods to
 * manage multiple {@link RepositoryService} a one aggregate.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface RepositoryServer extends RepositoryService {

	/**
	 * Add specified Repository to this server.
	 * @param repositoryService
	 *            repository Service instance to add
	 * @throws NullPointerException
	 *             if repositoryService is null
	 */
	public void add(RepositoryService repositoryService) throws NullPointerException;

	/**
	 * Remove specified Repository from this server.
	 * @param repositoryService
	 *            repository Service instance to remove
	 * @throws NullPointerException
	 *             if repositoryService is null
	 */
	public void remove(RepositoryService repositoryService) throws NullPointerException;

	/**
	 * @return a collection of {@link RepositoryService}
	 */
	public Collection<RepositoryService> findAllRepositoryService();
}
