package org.intelligentsia.keystone.api.artifacts;

/**
 * Version class expose methods to compare and analyse version field.
 * 
 * @author geronimo
 * 
 */
public final class Version implements Comparable<Version> {
	private final Integer major;
	private final Integer medium;
	private final Integer minor;
	private final String classifier;

	public Version(Integer major, Integer medium, Integer minor,
			String classifier) {
		super();
		// todo check major not null
		this.major = major;
		this.medium = medium;
		this.minor = minor;
		this.classifier = classifier;
	}

	public final static Version parse(String version) {
		
		return null;
	}

	public final static String format(Version version) {
		return "";
	}
	
	@Override
	public int compareTo(Version arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((classifier == null) ? 0 : classifier.hashCode());
		result = prime * result + ((major == null) ? 0 : major.hashCode());
		result = prime * result + ((medium == null) ? 0 : medium.hashCode());
		result = prime * result + ((minor == null) ? 0 : minor.hashCode());
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
		Version other = (Version) obj;
		if (classifier == null) {
			if (other.classifier != null)
				return false;
		} else if (!classifier.equals(other.classifier))
			return false;
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
		if (minor == null) {
			if (other.minor != null)
				return false;
		} else if (!minor.equals(other.minor))
			return false;
		return true;
	}

	@Override
	public String toString() {
		//TODO format
		return "Version [major=" + major + ", medium=" + medium + ", minor="
				+ minor + ", classifier=" + classifier + "]";
	}

	
}