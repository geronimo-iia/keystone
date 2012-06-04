package org.intelligentsia.utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;

/**
 * Integrity Check with MD5.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class CheckSum {

	/**
	 * @param path
	 * @param checksum
	 * @return true if computed checksum of targeted path and specified checksum
	 *         are equals.
	 * @throws RuntimeException
	 */
	public static Boolean validate(final String path, final String checksum) throws RuntimeException {
		return checksum.equals(CheckSum.md5(path));
	}

	/**
	 * @param path
	 * @return a String value of MD5 computed.
	 * @throws RuntimeException
	 *             if file is not found
	 */
	public static String md5(final String path) throws RuntimeException {
		InputStream in = null;
		try {
			in = new FileInputStream(path);
			return CheckSum.md5(in);
		} catch (final FileNotFoundException ex) {
			throw new RuntimeException(ex);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (final IOException ex) {
				}
			}
		}
	}

	/**
	 * @param source
	 * @return a String value of MD5 computed.
	 * @throws RuntimeException
	 */
	public static String md5(final InputStream source) throws RuntimeException {
		try {
			final MessageDigest md = MessageDigest.getInstance("MD5");
			final byte[] buf = new byte[8096];
			int len;
			while ((len = source.read(buf)) >= 0) {
				md.update(buf, 0, len);
			}
			return CheckSum.hex(md.digest());
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @param array
	 * @return a string version of hexadecimal byte array.
	 */
	public static String hex(final byte[] array) {
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < array.length; ++i) {
			sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).toUpperCase().substring(1, 3));
		}
		return sb.toString();
	}
}
