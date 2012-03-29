package org.intelligentsia.keystone.boot;

import java.util.Locale;

/**
 * Based on value found : http://lopica.sourceforge.net/os.html. The purpose of
 * this class if to determine which platform is used.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class OSDetector {

	/**
	 * Operating System Name.
	 * 
	 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
	 * 
	 */
	public enum Name {
		LINUX, MAC, WINDOWS
	}

	/**
	 * Current OS name detected.
	 */
	private static final Name name;

	/**
	 * Initialization o class loading.
	 */
	static {
		final String os = System.getProperty("os.name", "").toLowerCase(Locale.getDefault());
		if (os.startsWith("windows") || os.contains("nt")) {
			name = Name.WINDOWS;
		} else if (os.startsWith("mac")) {
			name = Name.MAC;
		} else {
			name = Name.LINUX;
		}
	}

	/**
	 * @return true if LINUX is current platform.
	 */
	public static boolean isLinux() {
		return Name.LINUX.equals(OSDetector.name);
	}

	/**
	 * @return true if WINDOWS is current platform.
	 */
	public static boolean isWindows() {
		return Name.WINDOWS.equals(OSDetector.name);
	}

	/**
	 * @return true if MAC is current platform.
	 */
	public static boolean isMac() {
		return Name.MAC.equals(OSDetector.name);
	}

	/**
	 * @return current platform name.
	 */
	public static Name getName() {
		return OSDetector.name;
	}
}
