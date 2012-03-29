/**
 * 
 */
package org.intelligentsia.keystone.api.artifacts;

/**
 * Root of Keystone Runtime Exception.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class KeystoneRuntimeException extends RuntimeException {

	private static final long	serialVersionUID	= 6954846105141143288L;

	/**
	 * Build a new instance of <code>KeystoneRuntimeException</code>
	 */
	public KeystoneRuntimeException() {
		super();
	}

	/**
	 * Build a new instance of <code>KeystoneRuntimeException</code>
	 * 
	 * @param message
	 * @param cause
	 */
	public KeystoneRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Build a new instance of <code>KeystoneRuntimeException</code>
	 * 
	 * @param message
	 */
	public KeystoneRuntimeException(String message) {
		super(message);
	}

	/**
	 * Build a new instance of <code>KeystoneRuntimeException</code>
	 * 
	 * @param cause
	 */
	public KeystoneRuntimeException(Throwable cause) {
		super(cause);
	}

}
