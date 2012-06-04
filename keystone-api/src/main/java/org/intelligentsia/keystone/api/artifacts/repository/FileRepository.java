/**
 * 
 */
package org.intelligentsia.keystone.api.artifacts.repository;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.intelligentsia.keystone.api.artifacts.ResourceDoesNotExistException;
import org.intelligentsia.keystone.api.artifacts.TransferFailedException;
import org.intelligentsia.utilities.FileUtils;
import org.intelligentsia.utilities.StringUtils;

/**
 * A FileRepository class.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class FileRepository implements RepositoryService {
	/**
	 * Inner locator.
	 */
	private final FileLocator locator;

	/**
	 * Build a new instance of <code>FileRepository</code>
	 * 
	 * @throws MalformedURLException
	 * @throws IllegalArgumentException
	 * @throws URISyntaxException
	 */
	public FileRepository(final Repository repository) throws MalformedURLException, IllegalArgumentException {
		final URL rootUrl = new URL(repository.getUrl());
		if (!"file".equals(rootUrl.getProtocol())) {
			throw new IllegalArgumentException("url " + repository.getUrl() + " not using file protocol");
		}
		File f = null;
		try {
			f = new File(rootUrl.toURI());
		} catch (final URISyntaxException e) {
			f = new File(rootUrl.getPath());
		}
		locator = new FileLocator(f);
	}

	/**
	 * Put a new resource.
	 * 
	 * @param resource
	 *            resource destination.
	 * @param source
	 *            source file.
	 * @throws ResourceDoesNotExistException
	 *             if source does not exists
	 * @throws TransferFailedException
	 *             if error occurs when transferring data.
	 */
	@Override
	public void put(final String resource, final File source) throws ResourceDoesNotExistException, TransferFailedException {
		if (!source.exists()) {
			throw new ResourceDoesNotExistException(source.getPath());
		}
		try {
			FileUtils.copy(source, locator.resolve(resource), true);
		} catch (final IllegalArgumentException e) {
			throw new TransferFailedException(StringUtils.format("(put %s  %s)", resource, source.getPath()), e);
		} catch (final IOException e) {
			throw new TransferFailedException(StringUtils.format("(put %s  %s)", resource, source.getPath()), e);
		}
	}

	/**
	 * @param resource
	 * @return a file instance of specified resource.
	 * @throws ResourceDoesNotExistException
	 *             if source does not exists
	 * @throws TransferFailedException
	 *             if error occurs when transferring data.
	 */
	@Override
	public File get(final String resource) throws ResourceDoesNotExistException, TransferFailedException {
		final File result = locator.resolve(resource);
		if (!result.exists()) {
			throw new ResourceDoesNotExistException(result.getPath());
		}
		return result;
	}

	/**
	 * @param resource
	 * @return true if resource exist.
	 */
	@Override
	public boolean exists(final String resource) {
		final File destination = locator.resolve(resource);
		if (resource.endsWith("/")) {
			return destination.isDirectory();
		}
		return destination.exists();
	}

	/**
	 * 
	 * @param resource
	 * @return a liste of child resource
	 */
	public List<File> list(final String resource) {
		final File destination = locator.resolve(resource);
		if (!destination.isDirectory()) {
			return Collections.emptyList();
		}
		final List<File> result = new ArrayList<File>();
		for (final File f : destination.listFiles()) {
			if (!f.getName().startsWith(".")) {
				result.add(f);
			}
		}
		return result;
	}

	/**
	 * @param resource
	 *            resource to delete
	 * @throws ResourceDoesNotExistException
	 *             if source does not exists
	 * @throws TransferFailedException
	 *             if error occurs when transferring data.
	 * @return true if delete is completed
	 */
	@Override
	public boolean delete(final String resource) {
		final File destination = locator.resolve(resource);
		return FileUtils.delete(destination);
	}

	/**
	 * Inner File locator.
	 * 
	 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
	 * 
	 */
	private class FileLocator implements Locator<File> {
		private final File root;

		/**
		 * Build a new instance of <code>FileLocator</code>
		 * 
		 * @param root
		 */
		public FileLocator(final File root) throws IllegalArgumentException {
			super();
			this.root = root;
			if (!root.exists()) {
				root.mkdirs();
			}
			if (!root.isDirectory()) {
				throw new IllegalArgumentException("Root " + root.getName() + " is not a directory");
			}
			if (!root.canRead()) {
				throw new IllegalArgumentException("Root " + root.getName() + " cannot be read");
			}
		}

		/**
		 * @see org.intelligentsia.keystone.api.artifacts.repository.Locator#getRoot()
		 */
		@Override
		public File getRoot() {
			return root;
		}

		/**
		 * @see org.intelligentsia.keystone.api.artifacts.repository.Locator#resolve(java.lang.String)
		 */
		@Override
		public File resolve(final String target) {
			// convert each / or \ to a file directory
			String destination = StringUtils.replace(target, "\\", File.separator);
			destination = StringUtils.replace(destination, "/", File.separator);
			File path = null;
			if (destination.equals(".")) {
				path = root;
			} else {
				path = new File(root, destination);
			}
			return path;
		}

	}

}
