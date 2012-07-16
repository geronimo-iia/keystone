package org.intelligentsia.keystone.kernel;

/**
 * {@link KernelServer} declare methods to manage internal kernel 'server'.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface KernelServer {
	/**
	 * 
	 * KernelServer State declaration.
	 * 
	 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
	 * 
	 */
	public enum State {
		CREATED, INITIALIZE, READY, DESTROY, DONE
	}

	/**
	 * @return author information.
	 */
	public String getAuthor();

	/**
	 * @return description information.
	 */
	public String getDescription();

	/**
	 * Initialize kernel server instance.
	 * 
	 * @param kernel
	 *            internal kernel instance.
	 */
	public void initialize(Kernel kernel);

	/**
	 * Detroy kernel server instance (all resources should be closed).
	 */
	public void destroy();

	/**
	 * @return current State.
	 */
	public State getState();
}
