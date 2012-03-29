/**
 * 
 */
package org.intelligentsia.keystone.api;

import org.intelligentsia.keystone.boot.KeystoneException;


/**
 * Updater sample.
 * 
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class Updater {

	/**
	 * Main methods.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("Usage: Updater new-jar ");
			return;
		}
		// update system properties with location of an updated jar
		System.setProperty("BootStrap.location", args[0]);
		// tell keystone to restart
		throw new KeystoneException(KeystoneException.Operation.RESTART);
	}
}
