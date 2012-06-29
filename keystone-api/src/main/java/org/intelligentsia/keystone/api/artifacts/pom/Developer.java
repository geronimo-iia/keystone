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
import java.util.Map;

/**
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class Developer implements Serializable {

	private static final long serialVersionUID = 6116571393616243845L;
	/**
	 * @uml.property name="id"
	 */
	private String id;
	/**
	 * @uml.property name="name"
	 */
	private String name;
	/**
	 * @uml.property name="email"
	 */
	private String email;
	/**
	 * @uml.property name="url"
	 */
	private String url;
	/**
	 * @uml.property name="organization"
	 */
	private String organization;
	/**
	 * @uml.property name="organizationUrl"
	 */
	private String organizationUrl;
	/**
	 * @uml.property name="roles"
	 */
	private String roles;
	/**
	 * @uml.property name="timezone"
	 */
	private String timezone;
	/**
	 * @uml.property name="properties"
	 */
	private Map<String, String> properties;

	public Developer() {
		super();
	}

	/**
	 * @return the id
	 * @uml.property name="id"
	 */
	public final String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 * @uml.property name="id"
	 */
	public final void setId(final String id) {
		this.id = id;
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
	 * @return the email
	 * @uml.property name="email"
	 */
	public final String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 * @uml.property name="email"
	 */
	public final void setEmail(final String email) {
		this.email = email;
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
	 * @return the organization
	 * @uml.property name="organization"
	 */
	public final String getOrganization() {
		return organization;
	}

	/**
	 * @param organization
	 *            the organization to set
	 * @uml.property name="organization"
	 */
	public final void setOrganization(final String organization) {
		this.organization = organization;
	}

	/**
	 * @return the organizationUrl
	 * @uml.property name="organizationUrl"
	 */
	public final String getOrganizationUrl() {
		return organizationUrl;
	}

	/**
	 * @param organizationUrl
	 *            the organizationUrl to set
	 * @uml.property name="organizationUrl"
	 */
	public final void setOrganizationUrl(final String organizationUrl) {
		this.organizationUrl = organizationUrl;
	}

	/**
	 * @return the roles
	 * @uml.property name="roles"
	 */
	public final String getRoles() {
		return roles;
	}

	/**
	 * @param roles
	 *            the roles to set
	 * @uml.property name="roles"
	 */
	public final void setRoles(final String roles) {
		this.roles = roles;
	}

	/**
	 * @return the timezone
	 * @uml.property name="timezone"
	 */
	public final String getTimezone() {
		return timezone;
	}

	/**
	 * @param timezone
	 *            the timezone to set
	 * @uml.property name="timezone"
	 */
	public final void setTimezone(final String timezone) {
		this.timezone = timezone;
	}

	/**
	 * @return the properties
	 * @uml.property name="properties"
	 */
	public final Map<String, String> getProperties() {
		return properties;
	}

	/**
	 * @param properties
	 *            the properties to set
	 * @uml.property name="properties"
	 */
	public final void setProperties(final Map<String, String> properties) {
		this.properties = properties;
	}

}
