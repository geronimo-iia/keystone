package org.intelligentsia.keystone.kernel;

/**
 * Kernel interface which declare core methods.
 * <p>
 * Microkernel Responsibility:
 * </p>
 * <ul>
 * <li>Provides core mechanisms</li>
 * <li>Offers communication facilities</li>
 * <li>Encapsulates system dependencies</li>
 * <li>Manages and controls resources</li>
 * </ul>
 * 
 * <p>
 * “Perfection is not achieved when there is nothing left to add, but when there
 * is nothing left to take away”<br />
 * --Antoine de St. Exupery
 * </p>
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface Kernel {

	/**
	 * @return a {@link RepositoryServer} instance.
	 */
	public RepositoryServer getRepositoryServer();

	/**
	 * @return a {@link ArtifactServer} instance.
	 */
	public ArtifactServer getArtifactServer();

	/**
	 * @return a {@link EventBus} instance.
	 */
	public EventBus getEventBus();
}
