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
package org.intelligentsia.keystone.api.artifacts;

import java.util.IllegalFormatException;

/**
 * Version class expose methods to compare and analyse version field.
 * 
 * <p>
 * A version should formatted like this <code>a.b.c-ext</code> with :
 * </p>
 * <ul>
 * <li>a: major</li>
 * <li>b: medium (optional)</li>
 * <li>c: minor (optional)</li>
 * <li>ext: classifier (optional)</li>
 * </ul>
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public final class Version implements Comparable<Version> {
	/**
	 * Major number.
	 */
	private final Integer major;
	/**
	 * Medium number.
	 */
	private final Integer medium;
	/**
	 * Minor number.
	 */
	private final Integer minor;
	/**
	 * Classifier.
	 */
	private final String classifier;

	/**
	 * Build a new instance of Version.java.
	 * 
	 * @param major
	 *            major version numebr
	 * @param medium
	 *            optional medium version number
	 * @param minor
	 *            optional minor version number
	 * @param classifier
	 *            optional version classifier
	 * @throws NullPointerException
	 *             if major is null
	 */
	public Version(final Integer major, final Integer medium, final Integer minor, final String classifier) throws NullPointerException {
		super();
		if (major == null) {
			throw new NullPointerException("major");
		}
		this.major = major;
		this.medium = medium != null ? Math.abs(medium) : null;
		this.minor = minor != null ? Math.abs(minor) : null;
		this.classifier = classifier;
	}

	/**
	 * Parse string representation.
	 * 
	 * @param version
	 * @return Version instance.
	 * @throws IllegalFormatException
	 *             if version is not a {@link Version}.
	 */
	public final static Version parse(final String version) throws IllegalFormatException {

		return null;
	}

	/**
	 * Format version.
	 * 
	 * @param version
	 *            version to format.
	 * @return formatted version as
	 *         <code>{@link Version#major}.{@link Version#medium}.{@link Version#minor}-{@link Version#classifier}</code>
	 * @throws NullPointerException
	 *             if version is null
	 */
	public final static String format(final Version version) throws NullPointerException {
		final StringBuilder builder = new StringBuilder(version.major);
		if (version.medium != null) {
			builder.append('.').append(version.medium);
		}
		if (version.minor != null) {
			builder.append('.').append(version.minor);
		}
		if (version.classifier != null) {
			builder.append('-').append(version.classifier);
		}
		return builder.toString();
	}

	@Override
	public int compareTo(final Version arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((classifier == null) ? 0 : classifier.hashCode());
		result = (prime * result) + ((major == null) ? 0 : major.hashCode());
		result = (prime * result) + ((medium == null) ? 0 : medium.hashCode());
		result = (prime * result) + ((minor == null) ? 0 : minor.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Version other = (Version) obj;
		if (classifier == null) {
			if (other.classifier != null) {
				return false;
			}
		} else if (!classifier.equals(other.classifier)) {
			return false;
		}
		if (major == null) {
			if (other.major != null) {
				return false;
			}
		} else if (!major.equals(other.major)) {
			return false;
		}
		if (medium == null) {
			if (other.medium != null) {
				return false;
			}
		} else if (!medium.equals(other.medium)) {
			return false;
		}
		if (minor == null) {
			if (other.minor != null) {
				return false;
			}
		} else if (!minor.equals(other.minor)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return format(this);
	}

}
