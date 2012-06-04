/**
 * 
 */
package org.intelligentsia.keystone.api.artifacts;

/**
 * Resource Does Not Exist Exception.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class ResourceDoesNotExistException extends KeystoneRuntimeException {

	private static final long serialVersionUID = 402218340493598939L;

	/**
	 * Build a new instance of <code>ResourceDoesNotExistException</code>
	 */
	public ResourceDoesNotExistException() {
		super();
	}

	/**
	 * Build a new instance of <code>ResourceDoesNotExistException</code>
	 * 
	 * @param message
	 * @param cause
	 */
	public ResourceDoesNotExistException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Build a new instance of <code>ResourceDoesNotExistException</code>
	 * 
	 * @param message
	 */
	public ResourceDoesNotExistException(final String message) {
		super(message);
	}

	/**
	 * Build a new instance of <code>ResourceDoesNotExistException</code>
	 * 
	 * @param cause
	 */
	public ResourceDoesNotExistException(final Throwable cause) {
		super(cause);
	}

}
