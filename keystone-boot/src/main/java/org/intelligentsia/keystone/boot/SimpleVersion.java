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
package org.intelligentsia.keystone.boot;

import java.io.Serializable;
import java.util.IllegalFormatException;
import java.util.StringTokenizer;

/**
 * SimpleVersion class expose methods to compare and analyze version field of
 * JVM Specification.
 * 
 * <p>
 * A version should formatted like this <code>a.b</code> with :
 * </p>
 * <ul>
 * <li>a: major</li>
 * <li>b: medium (optional)</li>
 * </ul>
 * 
 * <p>
 * Versioning
 * </p>
 * <ul>
 * <li>Breaking backward compatibility: major is incremented (medium and minor
 * are reset)</li>
 * <li>New additions without breaking backward compatibility: the medium is
 * incremented</li>
 * </ul>
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public final class SimpleVersion implements Comparable<SimpleVersion>, Serializable {
	/**
	 * serialVersionUID:long
	 */
	private static final long serialVersionUID = -2059600592653555167L;
	/**
	 * Major number.
	 */
	private final Integer major;
	/**
	 * Medium number.
	 */
	private final Integer medium;

	/**
	 * Build a new instance of SimpleVersion.
	 * 
	 * @param major
	 *            major version numebr
	 * @param medium
	 *            optional medium version number
	 * @throws NullPointerException
	 *             if major is null
	 */
	public SimpleVersion(final Integer major, final Integer medium) throws NullPointerException {
		super();
		if (major == null) {
			throw new NullPointerException("major");
		}
		this.major = major;
		this.medium = medium != null ? Math.abs(medium) : null;
	}

	/**
	 * Parse string representation.
	 * 
	 * @param version
	 * @return SimpleVersion instance.
	 * @throws IllegalFormatException
	 *             if version is not a {@link Version} with separator.
	 * @throws NumberFormatException
	 *             if major, medium are not number.
	 */
	public final static SimpleVersion parse(final String version) throws IllegalArgumentException, NumberFormatException {
		// remove classifier
		StringTokenizer tokenizer = new StringTokenizer(version, "-");
		if (!tokenizer.hasMoreTokens()) {
			throw new IllegalArgumentException(version + " is not a valid Version");
		}
		final String numbers = tokenizer.nextToken();
		// analyze sequence number
		tokenizer = new StringTokenizer(numbers, ".");
		final Integer major = Integer.parseInt(tokenizer.nextToken());
		// medium
		Integer medium = null;
		if (tokenizer.hasMoreTokens()) {
			medium = Integer.parseInt(tokenizer.nextToken());
		}
		// return final result
		return new SimpleVersion(major, medium);
	}

	public int compareTo(final SimpleVersion version) {
		int result = major.compareTo(version.major);
		if (result == 0) {
			if (medium != null) {
				result = (version.medium == null) ? 1 : medium.compareTo(version.medium);
			}
		}
		return result;
	}

	/**
	 * Same major and classifier.
	 * 
	 * @param version
	 * @return true if this version is backward compatible with specified
	 *         version.
	 */
	public boolean isBackwardCompatible(final SimpleVersion version) {
		return major.equals(version.major) && this.compareTo(version) >= 0;
	}

	public Integer getMajor() {
		return major;
	}

	public Integer getMedium() {
		return medium;
	}

	@Override
	public String toString() {
		return major + "." + medium;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((major == null) ? 0 : major.hashCode());
		result = prime * result + ((medium == null) ? 0 : medium.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SimpleVersion other = (SimpleVersion) obj;
		if (major == null) {
			if (other.major != null)
				return false;
		} else if (!major.equals(other.major))
			return false;
		if (medium == null) {
			if (other.medium != null)
				return false;
		} else if (!medium.equals(other.medium))
			return false;
		return true;
	}

}
