/**
 * 
 */
package org.intelligentsia.keystone.api.artifacts.repository;

/**
 * a small Locator API.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public interface Locator<T> {

	/**
	 * @return root instance.
	 */
	public T getRoot();

	/**
	 * @param target
	 * @return resolved localization of the specified target.
	 */
	public T resolve(String target);

}
