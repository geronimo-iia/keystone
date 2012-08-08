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
package org.intelligentsia.keystone.extra.vfs;

import java.io.File;
import java.lang.reflect.Constructor;
import java.net.URLStreamHandlerFactory;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.vfs2.CacheStrategy;
import org.apache.commons.vfs2.Capability;
import org.apache.commons.vfs2.FileContentInfoFactory;
import org.apache.commons.vfs2.FileName;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystem;
import org.apache.commons.vfs2.FileSystemConfigBuilder;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.FilesCache;
import org.apache.commons.vfs2.NameScope;
import org.apache.commons.vfs2.VFS;
import org.apache.commons.vfs2.operations.FileOperationProvider;
import org.intelligentsia.keystone.api.Preconditions;
import org.intelligentsia.keystone.api.StringUtils;
import org.intelligentsia.keystone.api.artifacts.KeystoneRuntimeException;
import org.intelligentsia.keystone.kernel.ArtifactContext;
import org.intelligentsia.keystone.kernel.Kernel;
import org.intelligentsia.keystone.kernel.KernelContext;

/**
 * DefaultVirtualFileSystem implements {@link VirtualFileSystem}.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 * 
 */
public class DefaultVirtualFileSystem implements VirtualFileSystem {

	private final ArtifactContext artifactContext;

	private FileSystemManager fileSystemManager;

	/**
	 * Build a new instance of DefaultVirtualFileSystem.java.
	 */
	public DefaultVirtualFileSystem(final ArtifactContext artifactContext, final Kernel kernel) throws NullPointerException {
		super();
		this.artifactContext = Preconditions.checkNotNull(artifactContext, "artifactContext");
	}

	/**
	 * @see org.intelligentsia.keystone.kernel.Service#getName()
	 */
	@Override
	public String getName() {
		return StringUtils.format("virtual-file-system-service (%s)", artifactContext.getArtifactIdentifier());
	}

	/**
	 * @see org.intelligentsia.keystone.kernel.Service#initialize(org.intelligentsia.keystone.kernel.KernelContext)
	 */
	@Override
	public void initialize(final KernelContext context) {
		try {
			fileSystemManager = VFS.getManager();
		} catch (final FileSystemException e) {
			throw new KeystoneRuntimeException("VirtualFileSystem/initialize", e);
		}
	}

	/**
	 * @see org.intelligentsia.keystone.kernel.Service#destroy()
	 */
	@Override
	public void destroy() {
		fileSystemManager = null;
	}

	/**
	 * @return
	 * @throws FileSystemException
	 * @see org.apache.commons.vfs2.FileSystemManager#getBaseFile()
	 */
	@Override
	public FileObject getBaseFile() throws FileSystemException {
		return fileSystemManager.getBaseFile();
	}

	/**
	 * @param name
	 * @return
	 * @throws FileSystemException
	 * @see org.apache.commons.vfs2.FileSystemManager#resolveFile(java.lang.String)
	 */
	@Override
	public FileObject resolveFile(final String name) throws FileSystemException {
		return fileSystemManager.resolveFile(name);
	}

	/**
	 * @param name
	 * @param fileSystemOptions
	 * @return
	 * @throws FileSystemException
	 * @see org.apache.commons.vfs2.FileSystemManager#resolveFile(java.lang.String,
	 *      org.apache.commons.vfs2.FileSystemOptions)
	 */
	@Override
	public FileObject resolveFile(final String name, final FileSystemOptions fileSystemOptions) throws FileSystemException {
		return fileSystemManager.resolveFile(name, fileSystemOptions);
	}

	/**
	 * @param baseFile
	 * @param name
	 * @return
	 * @throws FileSystemException
	 * @see org.apache.commons.vfs2.FileSystemManager#resolveFile(org.apache.commons.vfs2.FileObject,
	 *      java.lang.String)
	 */
	@Override
	public FileObject resolveFile(final FileObject baseFile, final String name) throws FileSystemException {
		return fileSystemManager.resolveFile(baseFile, name);
	}

	/**
	 * @param baseFile
	 * @param name
	 * @return
	 * @throws FileSystemException
	 * @see org.apache.commons.vfs2.FileSystemManager#resolveFile(java.io.File,
	 *      java.lang.String)
	 */
	@Override
	public FileObject resolveFile(final File baseFile, final String name) throws FileSystemException {
		return fileSystemManager.resolveFile(baseFile, name);
	}

	/**
	 * @param root
	 * @param name
	 * @return
	 * @throws FileSystemException
	 * @see org.apache.commons.vfs2.FileSystemManager#resolveName(org.apache.commons.vfs2.FileName,
	 *      java.lang.String)
	 */
	@Override
	public FileName resolveName(final FileName root, final String name) throws FileSystemException {
		return fileSystemManager.resolveName(root, name);
	}

	/**
	 * @param root
	 * @param name
	 * @param scope
	 * @return
	 * @throws FileSystemException
	 * @see org.apache.commons.vfs2.FileSystemManager#resolveName(org.apache.commons.vfs2.FileName,
	 *      java.lang.String, org.apache.commons.vfs2.NameScope)
	 */
	@Override
	public FileName resolveName(final FileName root, final String name, final NameScope scope) throws FileSystemException {
		return fileSystemManager.resolveName(root, name, scope);
	}

	/**
	 * @param file
	 * @return
	 * @throws FileSystemException
	 * @see org.apache.commons.vfs2.FileSystemManager#toFileObject(java.io.File)
	 */
	@Override
	public FileObject toFileObject(final File file) throws FileSystemException {
		return fileSystemManager.toFileObject(file);
	}

	/**
	 * @param provider
	 * @param file
	 * @return
	 * @throws FileSystemException
	 * @see org.apache.commons.vfs2.FileSystemManager#createFileSystem(java.lang.String,
	 *      org.apache.commons.vfs2.FileObject)
	 */
	@Override
	public FileObject createFileSystem(final String provider, final FileObject file) throws FileSystemException {
		return fileSystemManager.createFileSystem(provider, file);
	}

	/**
	 * @param filesystem
	 * @see org.apache.commons.vfs2.FileSystemManager#closeFileSystem(org.apache.commons.vfs2.FileSystem)
	 */
	@Override
	public void closeFileSystem(final FileSystem filesystem) {
		fileSystemManager.closeFileSystem(filesystem);
	}

	/**
	 * @param file
	 * @return
	 * @throws FileSystemException
	 * @see org.apache.commons.vfs2.FileSystemManager#createFileSystem(org.apache.commons.vfs2.FileObject)
	 */
	@Override
	public FileObject createFileSystem(final FileObject file) throws FileSystemException {
		return fileSystemManager.createFileSystem(file);
	}

	/**
	 * @param rootUri
	 * @return
	 * @throws FileSystemException
	 * @see org.apache.commons.vfs2.FileSystemManager#createVirtualFileSystem(java.lang.String)
	 */
	@Override
	public FileObject createVirtualFileSystem(final String rootUri) throws FileSystemException {
		return fileSystemManager.createVirtualFileSystem(rootUri);
	}

	/**
	 * @param rootFile
	 * @return
	 * @throws FileSystemException
	 * @see org.apache.commons.vfs2.FileSystemManager#createVirtualFileSystem(org.apache.commons.vfs2.FileObject)
	 */
	@Override
	public FileObject createVirtualFileSystem(final FileObject rootFile) throws FileSystemException {
		return fileSystemManager.createVirtualFileSystem(rootFile);
	}

	/**
	 * @return
	 * @see org.apache.commons.vfs2.FileSystemManager#getURLStreamHandlerFactory()
	 */
	@Override
	public URLStreamHandlerFactory getURLStreamHandlerFactory() {
		return fileSystemManager.getURLStreamHandlerFactory();
	}

	/**
	 * @param file
	 * @return
	 * @throws FileSystemException
	 * @see org.apache.commons.vfs2.FileSystemManager#canCreateFileSystem(org.apache.commons.vfs2.FileObject)
	 */
	@Override
	public boolean canCreateFileSystem(final FileObject file) throws FileSystemException {
		return fileSystemManager.canCreateFileSystem(file);
	}

	/**
	 * @return
	 * @see org.apache.commons.vfs2.FileSystemManager#getFilesCache()
	 */
	@Override
	public FilesCache getFilesCache() {
		return fileSystemManager.getFilesCache();
	}

	/**
	 * @return
	 * @see org.apache.commons.vfs2.FileSystemManager#getCacheStrategy()
	 */
	@Override
	public CacheStrategy getCacheStrategy() {
		return fileSystemManager.getCacheStrategy();
	}

	/**
	 * @return
	 * @see org.apache.commons.vfs2.FileSystemManager#getFileObjectDecorator()
	 */
	@Override
	public Class<?> getFileObjectDecorator() {
		return fileSystemManager.getFileObjectDecorator();
	}

	/**
	 * @return
	 * @see org.apache.commons.vfs2.FileSystemManager#getFileObjectDecoratorConst()
	 */
	@Override
	public Constructor<?> getFileObjectDecoratorConst() {
		return fileSystemManager.getFileObjectDecoratorConst();
	}

	/**
	 * @return
	 * @see org.apache.commons.vfs2.FileSystemManager#getFileContentInfoFactory()
	 */
	@Override
	public FileContentInfoFactory getFileContentInfoFactory() {
		return fileSystemManager.getFileContentInfoFactory();
	}

	/**
	 * @param scheme
	 * @return
	 * @see org.apache.commons.vfs2.FileSystemManager#hasProvider(java.lang.String)
	 */
	@Override
	public boolean hasProvider(final String scheme) {
		return fileSystemManager.hasProvider(scheme);
	}

	/**
	 * @return
	 * @see org.apache.commons.vfs2.FileSystemManager#getSchemes()
	 */
	@Override
	public String[] getSchemes() {
		return fileSystemManager.getSchemes();
	}

	/**
	 * @param scheme
	 * @return
	 * @throws FileSystemException
	 * @see org.apache.commons.vfs2.FileSystemManager#getProviderCapabilities(java.lang.String)
	 */
	@Override
	public Collection<Capability> getProviderCapabilities(final String scheme) throws FileSystemException {
		return fileSystemManager.getProviderCapabilities(scheme);
	}

	/**
	 * @param log
	 * @see org.apache.commons.vfs2.FileSystemManager#setLogger(org.apache.commons.logging.Log)
	 */
	@Override
	public void setLogger(final Log log) {
		fileSystemManager.setLogger(log);
	}

	/**
	 * @param scheme
	 * @return
	 * @throws FileSystemException
	 * @see org.apache.commons.vfs2.FileSystemManager#getFileSystemConfigBuilder(java.lang.String)
	 */
	@Override
	public FileSystemConfigBuilder getFileSystemConfigBuilder(final String scheme) throws FileSystemException {
		return fileSystemManager.getFileSystemConfigBuilder(scheme);
	}

	/**
	 * @param uri
	 * @return
	 * @throws FileSystemException
	 * @see org.apache.commons.vfs2.FileSystemManager#resolveURI(java.lang.String)
	 */
	@Override
	public FileName resolveURI(final String uri) throws FileSystemException {
		return fileSystemManager.resolveURI(uri);
	}

	/**
	 * @param scheme
	 * @param operationProvider
	 * @throws FileSystemException
	 * @see org.apache.commons.vfs2.FileSystemManager#addOperationProvider(java.lang.String,
	 *      org.apache.commons.vfs2.operations.FileOperationProvider)
	 */
	@Override
	public void addOperationProvider(final String scheme, final FileOperationProvider operationProvider) throws FileSystemException {
		fileSystemManager.addOperationProvider(scheme, operationProvider);
	}

	/**
	 * @param schemes
	 * @param operationProvider
	 * @throws FileSystemException
	 * @see org.apache.commons.vfs2.FileSystemManager#addOperationProvider(java.lang.String[],
	 *      org.apache.commons.vfs2.operations.FileOperationProvider)
	 */
	@Override
	public void addOperationProvider(final String[] schemes, final FileOperationProvider operationProvider) throws FileSystemException {
		fileSystemManager.addOperationProvider(schemes, operationProvider);
	}

	/**
	 * @param scheme
	 * @return
	 * @throws FileSystemException
	 * @see org.apache.commons.vfs2.FileSystemManager#getOperationProviders(java.lang.String)
	 */
	@Override
	public FileOperationProvider[] getOperationProviders(final String scheme) throws FileSystemException {
		return fileSystemManager.getOperationProviders(scheme);
	}

}
