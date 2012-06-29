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
/**
 * 
 */
package org.intelligentsia.keystone.api.artifacts.pom;

import java.io.Serializable;

/**
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class Licence implements Serializable {
	private static final long serialVersionUID = -8198638735891821626L;
	/**
	 * @uml.property name="name"
	 */
	private String name;
	/**
	 * @uml.property name="url"
	 */
	private String url;
	/**
	 * @uml.property name="distribution"
	 */
	private String distribution;
	/**
	 * @uml.property name="comments"
	 */
	private String comments;

	public Licence() {
		super();
	}

	/**
	 * @return the name
	 * @uml.property name="name"
	 */
	public final String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 * @uml.property name="name"
	 */
	public final void setName(final String name) {
		this.name = name;
	}

	/**
	 * @return the url
	 * @uml.property name="url"
	 */
	public final String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 * @uml.property name="url"
	 */
	public final void setUrl(final String url) {
		this.url = url;
	}

	/**
	 * @return the distribution
	 * @uml.property name="distribution"
	 */
	public final String getDistribution() {
		return distribution;
	}

	/**
	 * @param distribution
	 *            the distribution to set
	 * @uml.property name="distribution"
	 */
	public final void setDistribution(final String distribution) {
		this.distribution = distribution;
	}

	/**
	 * @return the comments
	 * @uml.property name="comments"
	 */
	public final String getComments() {
		return comments;
	}

	/**
	 * @param comments
	 *            the comments to set
	 * @uml.property name="comments"
	 */
	public final void setComments(final String comments) {
		this.comments = comments;
	}

}
