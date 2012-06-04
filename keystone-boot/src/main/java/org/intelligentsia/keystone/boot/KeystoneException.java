/**
 * 
 */
package org.intelligentsia.keystone.boot;

/**
 * KeystoneException used to drive which process is done after returning main.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class KeystoneException extends RuntimeException {

	/**
	 * serialVersionUID:long
	 */
	private static final long serialVersionUID = 5526337594737942947L;

	/**
	 * Operation that can be processed by bootstrap.
	 * 
	 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
	 * 
	 */
	public enum Operation {
		NONE, CLEAN, RESTART
	}

	private final Operation operation;

	/**
	 * Build a new instance of KeystoneException.
	 */
	public KeystoneException() {
		super();
		operation = Operation.NONE;
	}

	/**
	 * Build a new instance of KeystoneException.
	 * 
	 * @param operation
	 */
	public KeystoneException(final Operation operation) {
		super();
		this.operation = operation;
	}

	/**
	 * Build a new instance of KeystoneException.
	 * 
	 * @param message
	 * @param cause
	 */
	public KeystoneException(final Operation operation, final String message, final Throwable cause) {
		super(message, cause);
		this.operation = operation;
	}

	/**
	 * Build a new instance of KeystoneException.
	 * 
	 * @param message
	 */
	public KeystoneException(final Operation operation, final String message) {
		super(message);
		this.operation = operation;
	}

	/**
	 * Build a new instance of KeystoneException.
	 * 
	 * @param cause
	 */
	public KeystoneException(final Operation operation, final Throwable cause) {
		super(cause);
		this.operation = operation;
	}

	/**
	 * @return the operation
	 */
	public final Operation getOperation() {
		return operation;
	}

}
