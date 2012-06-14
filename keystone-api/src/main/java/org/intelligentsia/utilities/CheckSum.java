/**
 *        Licensed to the Apache Software Foundation (ASF) under one
 *        or more contributor license agreements.  See the NOTICE file
 *        distributed with this work for additional information
 *        regarding copyright ownership.  The ASF licenses this file
 *        to you under the Apache License, Version 2.0 (the
 *        "License"); you may not use this file except in compliance
 *        with the License.  You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *        Unless required by applicable law or agreed to in writing,
 *        software distributed under the License is distributed on an
 *        "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *        KIND, either express or implied.  See the License for the
 *        specific language governing permissions and limitations
 *        under the License.
 *
 */
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
