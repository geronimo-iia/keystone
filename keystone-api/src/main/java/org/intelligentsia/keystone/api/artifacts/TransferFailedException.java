/**
 * 
 */
package org.intelligentsia.keystone.api.artifacts;

/**
 * Transfer Failed Exception.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class TransferFailedException extends KeystoneRuntimeException {

	private static final long	serialVersionUID	= -2783547055285702711L;

	/**
	 * Build a new instance of <code>TransferFailedException</code>
	 */
	public TransferFailedException() {
		super();
	}

	/**
	 * Build a new instance of <code>TransferFailedException</code>
	 * 
	 * @param message
	 * @param cause
	 */
	public TransferFailedException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Build a new instance of <code>TransferFailedException</code>
	 * 
	 * @param message
	 */
	public TransferFailedException(String message) {
		super(message);
	}

	/**
	 * Build a new instance of <code>TransferFailedException</code>
	 * 
	 * @param cause
	 */
	public TransferFailedException(Throwable cause) {
		super(cause);
	}

}
