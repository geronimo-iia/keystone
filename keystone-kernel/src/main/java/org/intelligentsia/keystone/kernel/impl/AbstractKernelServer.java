package org.intelligentsia.keystone.kernel.impl;

import org.intelligentsia.keystone.kernel.Kernel;
import org.intelligentsia.keystone.kernel.KernelServer;

/**
 * {@link AbstractKernelServer} class.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public abstract class AbstractKernelServer implements KernelServer {

	protected String author = "";
	protected String description = "";
	protected Kernel kernel;
	private State state = State.CREATED;

	/**
	 * Build a new instance of AbstractKernelServer.java.
	 */
	public AbstractKernelServer() {
		super();
	}

	/**
	 * Build a new instance of AbstractKernelServer.java.
	 * 
	 * @param author
	 * @param description
	 */
	public AbstractKernelServer(String author, String description) {
		super();
		this.author = author;
		this.description = description;
	}

	@Override
	public String getAuthor() {
		return author;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public State getState() {
		return state;
	}

	@Override
	public final void initialize(Kernel kernel) {
		state = State.INITIALIZE;
		this.kernel = kernel;
		onInitialize();
		state = State.READY;
	}

	@Override
	public final void destroy() {
		state = State.DESTROY;
		this.kernel = null;
		onDestroy();
		state = State.DONE;
	}

	/**
	 * Called on initialize event.
	 */
	protected abstract void onInitialize();

	/**
	 * Called on detsory event.
	 */
	protected abstract void onDestroy();

	/**
	 * Log a message in kernel message.
	 * 
	 * @param message
	 * @param args
	 */
	protected void log(String message, final Object... args) {

	}

	/**
	 * Log a message in kernel message.
	 * 
	 * @param throwable
	 * @param message
	 * @param args
	 */
	protected void log(Throwable throwable, String message, final Object... args) {

	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " [author=" + author + ", description=" + description + "]";
	}

}
