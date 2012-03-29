/**
 * 
 */
package org.intelligentsia.keystone.boot;

import java.io.File;
import java.util.StringTokenizer;

/**
 * JniLoader.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class JniLoader {

	/**
	 * Load specified libraries.
	 * 
	 * @param directory
	 *            directory location of libraries
	 * @param libnames
	 *            libraries name comma separated
	 * @return true if all libraries has been successfully loaded.
	 */
	public static Boolean loadLibraries(File directory, String libnames) {
		StringTokenizer tokenizer = new StringTokenizer(libnames, ",");
		Boolean result = Boolean.TRUE;
		while (tokenizer.hasMoreTokens() && result) {
			result = loadLibrary(directory, tokenizer.nextToken());
		}
		return result;
	}

	/**
	 * Load specified library.
	 * 
	 * @param directory
	 *            directory location of libraries
	 * @param libname
	 *            library name
	 * @return true if library has been successfully loaded.
	 */
	public static Boolean loadLibrary(File directory, String libname) {
		String systemLibName = System.mapLibraryName(libname);
		File lib = new File(directory, systemLibName);
		if (!lib.exists()) {
			Console.WARNING("Library '" + libname + "' has not be found ('" + lib.getAbsolutePath() + "')");
			return Boolean.FALSE;
		}
		try {
			Runtime.getRuntime().load(lib.getAbsolutePath());
		} catch (UnsatisfiedLinkError e) {
			Console.WARNING("Library '" + libname + "' not found ('" + lib.getAbsolutePath() + "') or her dependencies not ever loaded", e);
			return Boolean.FALSE;
		} catch (Throwable e) {
			Console.WARNING("Loading Library '" + libname + "'", e);
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}
}
