package org.intelligentsia.keystone.kernel;

import org.intelligentsia.keystone.api.artifacts.KeystoneRuntimeException;

/**
 * 
 * ArtifactEntryPoint. Implement this interface to allow a class to act as a
 * artifact entry point.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public interface ArtifactEntryPoint {

	/**
	 * The entry point method, called automatically by loading an artifact that
	 * declares an implementing class as an entry point.
	 * 
	 * @param kernelContext
	 *            {@link KernelContext} instance
	 * @throws KeystoneRuntimeException
	 *             iof an error occurs
	 */
	public void onLoad(KernelContext kernelContext) throws KeystoneRuntimeException;
}
