package org.intelligentsia.keystone;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.Stack;

import org.apache.maven.archiver.MavenArchiveConfiguration;
import org.apache.maven.archiver.MavenArchiver;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.artifact.metadata.ArtifactMetadataSource;
import org.apache.maven.artifact.resolver.ArtifactCollector;
import org.apache.maven.artifact.resolver.ArtifactNotFoundException;
import org.apache.maven.artifact.resolver.ArtifactResolutionException;
import org.apache.maven.artifact.resolver.filter.ArtifactFilter;
import org.apache.maven.artifact.resolver.filter.ScopeArtifactFilter;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.shared.dependency.tree.DependencyNode;
import org.apache.maven.shared.dependency.tree.DependencyTreeBuilder;
import org.apache.maven.shared.dependency.tree.DependencyTreeBuilderException;
import org.codehaus.plexus.archiver.ArchiverException;
import org.codehaus.plexus.archiver.UnArchiver;
import org.codehaus.plexus.archiver.jar.JarArchiver;
import org.codehaus.plexus.archiver.jar.ManifestException;
import org.codehaus.plexus.archiver.manager.ArchiverManager;
import org.codehaus.plexus.archiver.manager.NoSuchArchiverException;
import org.codehaus.plexus.util.FileUtils;

/**
 * BootStrapMojo implement maven plugin for keystone bootstrap project.
 * 
 * @goal custom
 * 
 * @phase package
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class BootStrapMojo extends AbstractMojo {

	/**
	 * file name.
	 */
	private static final String KEYSTONE_PROPERTIES_FILE_NAME = "keystone.properties";
	/**
	 * artifact id.
	 */
	private static final String KEYSTONE_BOOT = "keystone-boot";
	/**
	 * group id.
	 */
	private static final String GROUP_ID = "org.intelligents-ia.keystone";
	/**
	 * Project target.
	 * 
	 * @parameter expression="${project}"
	 */
	private org.apache.maven.project.MavenProject project;
	/**
	 * The Jar archiver needed for archiving.
	 * 
	 * 
	 * @component role="org.codehaus.plexus.archiver.Archiver" roleHint="jar"
	 * @required
	 */
	private JarArchiver jarArchiver;
	/**
	 * The maven archive configuration to use
	 * 
	 * @parameter
	 */
	protected MavenArchiveConfiguration archive = new MavenArchiveConfiguration();
	/**
	 * Build directory.
	 * 
	 * @parameter expression="${project.build.directory}"
	 * @required
	 */
	private File buildDirectory;
	/** @component */
	private org.apache.maven.artifact.factory.ArtifactFactory artifactFactory;
	/** @component */
	private org.apache.maven.artifact.resolver.ArtifactResolver resolver;
	/** @parameter default-value="${localRepository}" */
	private org.apache.maven.artifact.repository.ArtifactRepository localRepository;
	/** @parameter default-value="${project.remoteArtifactRepositories}" */
	private List<?> remoteRepositories;
	/**
	 * @component
	 * @required
	 * @readonly
	 */
	private ArtifactMetadataSource artifactMetadataSource;
	/**
	 * @component
	 * @required
	 * @readonly
	 */
	private ArtifactCollector artifactCollector;
	/**
	 * @component
	 * @required
	 * @readonly
	 */
	private DependencyTreeBuilder treeBuilder;
	/**
	 * support for accessing archives
	 * 
	 * @component
	 */
	private ArchiverManager archiverManager;

	/** @parameter default-value="${plugin.artifacts}" */
	private java.util.List<Artifact> pluginArtifacts;
	/**
	 * java main class to run
	 * 
	 * @parameter expression="${mainClass}"
	 */
	private String mainClass = null;

	/**
	 * Parameter for Bootstrap: true|false (default true) clean up local 'lib'
	 * file system on startup.
	 * 
	 * @parameter expression="${cleanUpLib}"
	 */
	private Boolean cleanUpLib = true;

	/**
	 * Parameter for Bootstrap: true|false (default false) clean up local 'lib'
	 * file system on shutdown.
	 * 
	 * @parameter expression="${cleanUpBeforeShutdown}"
	 */
	private Boolean cleanUpBeforeShutdown = false;

	/**
	 * Parameter for Bootstrap: true|false (default false) activate 'verbose'
	 * mode
	 * 
	 * @parameter expression="${verbose}"
	 */
	private Boolean verbose = false;
	/**
	 * Parameter for Bootstrap: true|false (default false) activate 'info log'
	 * mode
	 * 
	 * @parameter expression="${info}"
	 */
	private Boolean info = false;
	/**
	 * Parameter for Bootstrap: log file of bootstrap (default is none)
	 * 
	 * @parameter expression="${logFile}"
	 */
	private String logFile = null;
	/**
	 * Parameter for Bootstrap: true|false (default true) include java home
	 * librarie
	 * 
	 * @parameter expression="${includeJavaHomeLib}"
	 */
	private Boolean includeJavaHomeLib = true;
	/**
	 * Parameter for Bootstrap: true|false (default true) include system class
	 * loader
	 * 
	 * @parameter expression="${includeSystemClassLoader}"
	 */
	private Boolean includeSystemClassLoader = true;

	/**
	 * Parameter for Bootstrap: explode Directory for inner jar. Default is
	 * current path location or temp directory if path is not writable.
	 * 
	 * @parameter expression="${explodeDirectory}"
	 */
	private String explodeDirectory = null;

	/**
	 * if true, the final boot archive will replace project artifact.
	 * 
	 * @parameter expression="${replaceProjectArtifact}"
	 */
	private Boolean replaceProjectArtifact = false;

	/**
	 * Final name of keystone artifact.
	 * 
	 * @parameter expression="${finalName}"
	 */
	private String finalName = "";

	/**
	 * KEYSTONE version
	 */
	private String pluginVersion = "";

	/**
	 * List of ordered libraries to add on final artifact (native or not).
	 * 
	 * @parameter alias="${libraries}"
	 */
	private List<Library> libraries = null;

	/**
	 * If true then all dependencies will be exploded and packaged inside final
	 * archive (like "onejarplugin"). Per default is false.
	 * 
	 * @parameter expression="${explodeDependencies}"
	 */
	private Boolean explodeDependencies = false;

	/**
	 * {@inheritDoc}
	 */
	public void execute() throws MojoExecutionException {
		getLog().info("Build Bootstrap");
		// initialize version
		pluginVersion = "";
		for (final Artifact a : pluginArtifacts) {
			if (a.getGroupId().equals(BootStrapMojo.GROUP_ID) && a.getArtifactId().equals(BootStrapMojo.KEYSTONE_BOOT)) {
				pluginVersion = a.getVersion();
				break;
			}
		}
		/**
		 * locate and create root directory
		 */
		final File root = new File(buildDirectory, "bootstrap");
		/** clean up */
		if (root.exists()) {
			BootStrapMojo.delete(root);
		}
		if (!root.mkdirs()) {
			throw new MojoExecutionException("Cannot create output directory " + root.getName());
		}
		// Analyze native libraries
		analyzeNativeLibraries();
		// write default plugin properties
		writeBootStrapProperties(root);
		// copy all runtime dependencies
		final File libFile = copyDependencies(root);
		// copy external library
		copyNativeLibraries(libFile);
		// copy main artifact
		copyMainArtifact(root, libFile);
		// add class of org.intelligentsia.keystone:boot
		copyBootStrap(root);
		// package result
		packageApplication(root);
	}

	/**
	 * Analyze native libraries. Compute library name if needed.
	 * 
	 * @throws MojoExecutionException
	 *             if a library don't exists
	 */
	private void analyzeNativeLibraries() throws MojoExecutionException {
		if (libraries != null) {
			getLog().info("analyze native libraries");
			for (Library library : libraries) {
				File f = new File(library.getPath());
				if (!f.exists()) {
					throw new MojoExecutionException("Unable to found library: " + library);
				}
				// compute name
				if (library.getName() == null || "".equals(library.getName())) {
					library.setName(f.getName());
					// removing extension
					int index = library.getName().lastIndexOf('.');
					if (index > 0) {
						library.setName(library.getName().substring(0, index));
					}
				}
			}
		}
	}

	/**
	 * Copy native libraries
	 * 
	 * @param libDirectory
	 *            destination libraries directory
	 * @throws MojoExecutionException
	 */
	private void copyNativeLibraries(File libDirectory) throws MojoExecutionException {
		if (libraries != null) {
			getLog().info("copy native libraries");
			for (Library library : libraries) {
				File f = new File(library.getPath());
				try {
					getLog().debug("copy " + library.getPath());
					FileUtils.copyFileToDirectory(f, libDirectory);
				} catch (IOException e) {
					throw new MojoExecutionException("Error copying library " + library, e);
				}
			}
		}
	}

	/**
	 * Copy all runtimes dependencies of the project inside %root%/lib
	 * 
	 * @param root
	 * @throws MojoExecutionException
	 * @return library directory
	 */
	private File copyDependencies(final File root) throws MojoExecutionException {
		getLog().info("copy runtime dependencies");
		final File libDirectory = new File(root, "lib");
		if (!libDirectory.mkdirs()) {
			throw new MojoExecutionException("Cannot create libraries directory " + libDirectory.getName());
		}
		try {
			final Set<Artifact> artifacts = new HashSet<Artifact>();
			// init filter
			final ArtifactFilter artifactFilter = new ScopeArtifactFilter(null);
			// Build project dependency tree
			final DependencyNode rootNode = treeBuilder.buildDependencyTree(project, localRepository, artifactFactory, artifactMetadataSource, artifactFilter, artifactCollector);
			// collect
			for (final Iterator<?> iterator = rootNode.getChildren().iterator(); iterator.hasNext();) {
				final DependencyNode child = (DependencyNode) iterator.next();
				collect(child, artifacts);
			}
			for (final Artifact artifact : artifacts) {
				try {
					resolver.resolve(artifact, remoteRepositories, localRepository);
				} catch (final ArtifactResolutionException e) {
					throw new MojoExecutionException("Unable to resolve " + artifact, e);
				} catch (final ArtifactNotFoundException e) {
					throw new MojoExecutionException("Unable to resolve " + artifact, e);
				}
				if (!explodeDependencies) {
					FileUtils.copyFileToDirectory(artifact.getFile(), libDirectory);
				} else {
					unArchiveArtifact(root, artifact.getFile());
				}
			}

		} catch (final DependencyTreeBuilderException e) {
			throw new MojoExecutionException("Error analysing dependency", e);
		} catch (final IOException e) {
			throw new MojoExecutionException("Error copying libs", e);
		}

		return libDirectory;
	}

	/**
	 * collect children dependency starting on specified node and add them in
	 * artifacts set.
	 * 
	 * @param node
	 * @param artifacts
	 */
	private void collect(final DependencyNode node, final Set<Artifact> artifacts) {
		collectNoRec(node, artifacts);
	}

	/**
	 * collect children dependency starting on specified node and add them in
	 * artifacts set. Recursive algorithm.
	 * 
	 * @param node
	 * @param artifacts
	 */
	protected void collectRec(final DependencyNode node, final Set<Artifact> artifacts) {
		if (node.getState() == DependencyNode.INCLUDED) {
			final Artifact artifact = node.getArtifact();
			if (Artifact.SCOPE_COMPILE.equals(artifact.getScope()) || Artifact.SCOPE_RUNTIME.equals(artifact.getScope())) {
				getLog().info("Adding Artifact: " + artifact.toString());
				artifacts.add(artifact);
				for (final Iterator<?> iterator = node.getChildren().iterator(); iterator.hasNext();) {
					final DependencyNode child = (DependencyNode) iterator.next();
					collect(child, artifacts);
				}
			}
		}
	}

	/**
	 * collect children dependency starting on specified node and add them in
	 * artifacts set. No recursive algorithm.
	 * 
	 * @param node
	 * @param artifacts
	 */
	@SuppressWarnings("unchecked")
	protected void collectNoRec(final DependencyNode root, final Set<Artifact> artifacts) {
		Stack<DependencyNode> stack = new Stack<DependencyNode>();
		stack.push(root);
		while (!stack.isEmpty()) {
			DependencyNode node = stack.pop();
			if (node.getState() == DependencyNode.INCLUDED) {
				final Artifact artifact = node.getArtifact();
				if (Artifact.SCOPE_COMPILE.equals(artifact.getScope()) || Artifact.SCOPE_RUNTIME.equals(artifact.getScope())) {
					getLog().info("Adding Artefact: " + artifact.toString());
					artifacts.add(artifact);
					// check children
					if (!node.getChildren().isEmpty()) {
						stack.addAll(node.getChildren());
					}
				}
			}
		}

	}

	/**
	 * Copy project artifact in lib directory or explode it.
	 * 
	 * @param root
	 *            folder root
	 * @param libDirectory
	 * @throws MojoExecutionException
	 */
	private void copyMainArtifact(final File root, final File libDirectory) throws MojoExecutionException {
		try {
			if (!explodeDependencies) {
				FileUtils.copyFileToDirectory(project.getArtifact().getFile(), libDirectory);
			} else {
				unArchiveArtifact(root, project.getArtifact().getFile());
			}
		} catch (final IOException e) {
			throw new MojoExecutionException("Error copying project artifact ", e);
		}
	}

	/**
	 * Write properties file "META-INF/keystone.properties"
	 */
	private void writeBootStrapProperties(final File root) throws MojoExecutionException {
		getLog().info("write Bootstrap properties");
		final Properties properties = new Properties();
		// version management
		// ADD Keystone version
		properties.put("BootStrap.keystone.version", pluginVersion);
		// ADD original project version
		properties.put("BootStrap.project.version", project.getVersion());
		// main class
		if (mainClass != null) {
			properties.put("Main-Class", mainClass);
		}
		// explode folder
		if (explodeDirectory != null && !"".equals(explodeDirectory)) {
			properties.put("BootStrap.explodeDirectory", explodeDirectory);
		}
		properties.put("BootStrap.cleanUpLib", Boolean.toString(cleanUpLib));
		properties.put("BootStrap.cleanUpBeforeShutdown", Boolean.toString(cleanUpBeforeShutdown));
		// log
		properties.put("BootStrap.verbose", Boolean.toString(verbose));
		properties.put("BootStrap.info", Boolean.toString(info));
		if (logFile != null) {
			properties.put("BootStrap.logFile", logFile);
		}
		// classpath
		properties.put("BootStrap.includeJavaHomeLib", Boolean.toString(includeJavaHomeLib));
		properties.put("BootStrap.includeSystemClassLoader", Boolean.toString(includeSystemClassLoader));

		// native libraries
		if (libraries != null) {
			Set<String> names = new HashSet<String>();
			for (Library library : libraries) {
				names.add(library.getName());
			}
			StringBuilder builder = null;
			for (String name : names) {
				if (builder != null) {
					builder.append(name).append(",");
				} else {
					builder = new StringBuilder(name);
				}
			}
			if (builder != null) {
				properties.put("BootStrap.nativeCode", builder.toString());
			}
		}

		// Create META-INF directory
		final File metainf = new File(root, "META-INF");
		if (!metainf.exists()) {
			if (!metainf.mkdirs()) {
				throw new MojoExecutionException("Unable to create META-INF directory");
			}
		}
		// MAKE properties file.
		final File keystone = new File(metainf, BootStrapMojo.KEYSTONE_PROPERTIES_FILE_NAME);
		FileOutputStream w = null;
		try {
			w = new FileOutputStream(keystone);
			properties.store(w, "AUTO generated");
		} catch (final IOException e) {
			throw new MojoExecutionException("Error writing properties file META-INF/keystone.properties ", e);
		} finally {
			if (w != null) {
				try {
					w.close();
				} catch (final IOException e) {
					// ignore
				}
			}
		}
	}

	/**
	 * package application and add artifact into project.
	 * 
	 * @param root
	 *            folder root
	 * @throws MojoExecutionException
	 */
	private void packageApplication(final File root) throws MojoExecutionException {
		// Compute archive name
		final String archiveName = getFinalArchiveName();
		getLog().info("package boot: " + archiveName);
		final File custFile = new File(buildDirectory, archiveName);
		// Configure archiver
		// archive.setAddMavenDescriptor(false);
		final MavenArchiver archiver = new MavenArchiver();
		archiver.setArchiver(jarArchiver);
		archiver.setOutputFile(custFile);
		try {
			jarArchiver.setManifest(new File(new File(root, "META-INF"), "MANIFEST.MF"));
			// add archive directory
			archiver.getArchiver().addDirectory(root);
			// add keystone.properties from project if exists
			final File keystone = new File(project.getBuild().getOutputDirectory(), BootStrapMojo.KEYSTONE_PROPERTIES_FILE_NAME);
			if (keystone.exists()) {
				archiver.getArchiver().addFile(keystone, BootStrapMojo.KEYSTONE_PROPERTIES_FILE_NAME);
			}
			// create archive
			archiver.createArchive(project, archive);
			// set archive as artifact
			if (replaceProjectArtifact) {
				project.getArtifact().setFile(custFile);
			} else {
				// add an other artifact in current project
				final Artifact artifact = artifactFactory.createArtifact(project.getGroupId(), project.getArtifactId() + "-boot", project.getVersion(), Artifact.SCOPE_COMPILE, "jar");
				artifact.setFile(custFile);
				project.addAttachedArtifact(artifact);
			}
		} catch (final ArchiverException e) {
			throw new MojoExecutionException("Exception while packaging", e);
		} catch (final ManifestException e) {
			throw new MojoExecutionException("Exception while packaging", e);
		} catch (final IOException e) {
			throw new MojoExecutionException("Exception while packaging", e);
		} catch (final DependencyResolutionRequiredException e) {
			throw new MojoExecutionException("Exception while packaging", e);
		}
	}

	/**
	 * @return final name archive (specified by "finalName" plugin parameter or
	 *         by final name of project with '-boot' suffix.
	 */
	private String getFinalArchiveName() {
		if (replaceProjectArtifact) {
			return project.getArtifact().getFile().getName();
		}
		if (finalName != null && !"".equals(finalName)) {
			return finalName;
		}
		return project.getBuild().getFinalName() + "-boot.jar";
	}

	/**
	 * Copy bootstrap under specified root directory.
	 * 
	 * @param root
	 * @throws MojoExecutionException
	 */
	private void copyBootStrap(final File root) throws MojoExecutionException {
		// retrieve it
		try {
			final Artifact artifact = artifactFactory.createArtifact(BootStrapMojo.GROUP_ID, BootStrapMojo.KEYSTONE_BOOT, pluginVersion, "compile", "jar");
			resolver.resolve(artifact, remoteRepositories, localRepository);
			final File artifactFile = artifact.getFile();
			unArchiveArtifact(root, artifactFile);
		} catch (final ArtifactNotFoundException e) {
			throw new MojoExecutionException("Exception while copying bootsrap", e);
		} catch (final ArtifactResolutionException e) {
			throw new MojoExecutionException("Exception while copying bootsrap", e);
		} catch (final MojoExecutionException e) {
			throw new MojoExecutionException("Exception while copying bootsrap", e);
		}
	}

	/**
	 * extract specified artifact.
	 * 
	 * @param destination
	 *            destination folder
	 * @param artifactFile
	 * @throws NoSuchArchiverException
	 * @throws ArchiverException
	 * @throws MojoExecutionException
	 */
	private void unArchiveArtifact(final File destination, final File artifactFile) throws MojoExecutionException {
		try {
			// unarchive artifact in root directory
			final UnArchiver unArchiver = archiverManager.getUnArchiver(artifactFile);
			unArchiver.setSourceFile(artifactFile);
			unArchiver.setDestDirectory(destination);
			unArchiver.setOverwrite(true);
			unArchiver.extract();
		} catch (final NoSuchArchiverException e) {
			throw new MojoExecutionException("Unable to unarchive " + artifactFile.getName(), e);
		} catch (final ArchiverException e) {
			throw new MojoExecutionException("Unable to unarchive " + artifactFile.getName(), e);
		} catch (final IOException e) {
			throw new MojoExecutionException("Unable to unarchive " + artifactFile.getName(), e);
		}
	}

	/**
	 * Utility to delete file (directory or single file)
	 * 
	 * @param from
	 * @return
	 */
	private static boolean delete(final File from) {
		if ((from != null) && from.exists()) {
			if (from.isDirectory()) {
				for (final File child : from.listFiles()) {
					BootStrapMojo.delete(child);
				}
			}
			return from.delete();
		}
		return false;
	}
}
