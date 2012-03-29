/**
 * 
 */
package org.intelligentsia.keystone.api.artifacts.pom;

import java.io.Serializable;
import java.util.Map;

/**
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class Developer implements Serializable {

	private static final long serialVersionUID = 6116571393616243845L;
	private String id;
	private String name;
	private String email;
	private String url;
	private String organization;
	private String organizationUrl;
	private String roles;
	private String timezone;
	private Map<String, String> properties;

	public Developer() {
		super();
	}

	/**
	 * @return the id
	 */
	public final String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public final void setId(final String id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public final String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public final void setName(final String name) {
		this.name = name;
	}

	/**
	 * @return the email
	 */
	public final String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public final void setEmail(final String email) {
		this.email = email;
	}

	/**
	 * @return the url
	 */
	public final String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public final void setUrl(final String url) {
		this.url = url;
	}

	/**
	 * @return the organization
	 */
	public final String getOrganization() {
		return organization;
	}

	/**
	 * @param organization
	 *            the organization to set
	 */
	public final void setOrganization(final String organization) {
		this.organization = organization;
	}

	/**
	 * @return the organizationUrl
	 */
	public final String getOrganizationUrl() {
		return organizationUrl;
	}

	/**
	 * @param organizationUrl
	 *            the organizationUrl to set
	 */
	public final void setOrganizationUrl(final String organizationUrl) {
		this.organizationUrl = organizationUrl;
	}

	/**
	 * @return the roles
	 */
	public final String getRoles() {
		return roles;
	}

	/**
	 * @param roles
	 *            the roles to set
	 */
	public final void setRoles(final String roles) {
		this.roles = roles;
	}

	/**
	 * @return the timezone
	 */
	public final String getTimezone() {
		return timezone;
	}

	/**
	 * @param timezone
	 *            the timezone to set
	 */
	public final void setTimezone(final String timezone) {
		this.timezone = timezone;
	}

	/**
	 * @return the properties
	 */
	public final Map<String, String> getProperties() {
		return properties;
	}

	/**
	 * @param properties
	 *            the properties to set
	 */
	public final void setProperties(final Map<String, String> properties) {
		this.properties = properties;
	}

}
