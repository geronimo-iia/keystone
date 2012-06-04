/**
 * 
 */
package org.intelligentsia.keystone.api.artifacts;

import java.io.File;

/**
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class Resource {

	private ArtifactIdentifier artefactIdentifier;

	private String name;

	private File localFile;

	/**
	 * Build a new instance of <code>Resource</code>
	 */
	public Resource() {
		super();
	}

	public ArtifactIdentifier getArtefactIdentifier() {
		return this.artefactIdentifier;
	}

	public void setArtefactIdentifier(final ArtifactIdentifier artefactIdentifier) {
		this.artefactIdentifier = artefactIdentifier;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public File getLocalFile() {
		return this.localFile;
	}

	public void setLocalFile(final File localFile) {
		this.localFile = localFile;
	}

}
