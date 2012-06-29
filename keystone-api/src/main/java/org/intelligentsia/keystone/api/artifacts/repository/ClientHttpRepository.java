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
package org.intelligentsia.keystone.api.artifacts.repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.MalformedURLException;
import java.nio.ByteBuffer;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.intelligentsia.keystone.api.artifacts.KeystoneRuntimeException;
import org.intelligentsia.keystone.api.artifacts.ResourceDoesNotExistException;
import org.intelligentsia.keystone.api.artifacts.TransferFailedException;
import org.intelligentsia.utilities.StringUtils;

import com.ning.http.client.AsyncHandler;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncHttpClientConfig;
import com.ning.http.client.AsyncHttpClientConfig.Builder;
import com.ning.http.client.HttpResponseBodyPart;
import com.ning.http.client.HttpResponseHeaders;
import com.ning.http.client.HttpResponseStatus;
import com.ning.http.client.Response;
import com.ning.http.client.resumable.ResumableAsyncHandler;
import com.ning.http.client.resumable.ResumableListener;
import com.ning.http.util.AsyncHttpProviderUtils;

/**
 * An ClientHttpRepository using an AsyncHttpClient to retrieve resource.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class ClientHttpRepository implements RepositoryService {
	/**
	 * Inner locator.
	 * 
	 * @uml.property name="locator"
	 * @uml.associationEnd
	 */
	private final Locator<String> locator;
	/**
	 * Async HTTP Client.
	 */
	private AsyncHttpClient asyncHttpClient;

	/**
	 * Build a new instance of <code>ClientHttpRepository</code>
	 * 
	 * @param repository
	 * @throws MalformedURLException
	 */
	public ClientHttpRepository(final Repository repository) {
		this(repository, null);
	}

	/**
	 * Build a new instance of ClientHttpRepository.
	 * 
	 * @param repository
	 * @param httpClient
	 * @throws MalformedURLException
	 */
	public ClientHttpRepository(final Repository repository, final AsyncHttpClient httpClient) {
		super();
		locator = new URLLocator(AsyncHttpProviderUtils.createUri(repository.getUrl()).toString());
		if (httpClient == null) {
			final Builder builder = new AsyncHttpClientConfig.Builder();
			builder.setCompressionEnabled(true).setAllowPoolingConnection(true).setRequestTimeoutInMs(30000).build();
			asyncHttpClient = new AsyncHttpClient(builder.build());
		} else {
			asyncHttpClient = httpClient;
		}
	}

	/**
	 * @see org.intelligentsia.keystone.api.artifacts.repository.RepositoryService#get(java.lang.String)
	 */
	@Override
	public File get(final String resource) throws ResourceDoesNotExistException, TransferFailedException {
		File destination = null;
		try {
			destination = File.createTempFile("repository", ".tmp");
		} catch (final IOException e) {
			throw new TransferFailedException(StringUtils.format("(get %s) cannot create temp file", resource), e);
		}
		// use a resumable download
		// from
		// http://jfarcand.wordpress.com/2011/01/04/going-asynchronous-using-asynchttpclient-the-complex/
		final RandomAccessFile file;
		try {
			file = new RandomAccessFile(destination, "rw");
		} catch (final FileNotFoundException e) {
			throw new TransferFailedException("cannot access on temp file", e);
		}
		final ResumableAsyncHandler<Response> handler = new ResumableAsyncHandler<Response>();
		handler.setResumableListener(new ResumableListener() {
			/**
			 * @see com.ning.http.client.resumable.ResumableListener#onBytesReceived(java.nio.ByteBuffer)
			 */
			@Override
			public void onBytesReceived(final ByteBuffer byteBuffer) throws IOException {
				file.seek(file.length());
				file.write(byteBuffer.array());
			}

			/**
			 * @see com.ning.http.client.resumable.ResumableListener#onAllBytesReceived()
			 */
			@Override
			public void onAllBytesReceived() {
				try {
					file.close();
				} catch (final IOException e) {
					handler.onThrowable(e);
				}
			}

			/**
			 * @see com.ning.http.client.resumable.ResumableListener#length()
			 */
			@Override
			public long length() {
				try {
					return file.length();
				} catch (final IOException e) {
					handler.onThrowable(e);
					return -1;
				}
			}
		});
		Response response = null;
		boolean cleanUp = true;
		try {
			response = asyncHttpClient.prepareGet(locator.resolve(resource)).execute(handler).get();
			if (response.getStatusCode() == 404) {
				throw new ResourceDoesNotExistException(StringUtils.format("%s not found", resource));
			}
			if (response.getStatusCode() == 200) {
				cleanUp = false;
			} else {
				throw new TransferFailedException(StringUtils.format("error %s when transferring %s", response.getStatusCode(), resource));
			}
		} catch (final InterruptedException e) {
			throw new TransferFailedException(StringUtils.format("(get %s)", resource), e);
		} catch (final ExecutionException e) {
			throw new TransferFailedException(StringUtils.format("(get %s)", resource), e);
		} catch (final IOException e) {
			throw new TransferFailedException(StringUtils.format("(get %s)", resource), e);
		} finally {
			if (cleanUp) {
				destination.delete();
			}
		}

		return destination;
	}

	/**
	 * @see org.intelligentsia.keystone.api.artifacts.repository.RepositoryService#exists(java.lang.String)
	 */
	@Override
	public boolean exists(final String resource) throws TransferFailedException {
		Future<Boolean> f;
		try {
			f = asyncHttpClient.prepareGet(locator.resolve(resource)).execute(new AsyncHandler<Boolean>() {
				Boolean result = Boolean.FALSE;

				/**
				 * @see com.ning.http.client.AsyncHandler#onThrowable(java.lang.Throwable)
				 */
				@Override
				public void onThrowable(final Throwable t) {
					result = Boolean.FALSE;
				}

				/**
				 * @see com.ning.http.client.AsyncHandler#onBodyPartReceived(com.ning.http.client.HttpResponseBodyPart)
				 */
				@Override
				public com.ning.http.client.AsyncHandler.STATE onBodyPartReceived(final HttpResponseBodyPart bodyPart) throws Exception {
					return STATE.ABORT;
				}

				/**
				 * @see com.ning.http.client.AsyncHandler#onStatusReceived(com.ning.http.client.HttpResponseStatus)
				 */
				@Override
				public com.ning.http.client.AsyncHandler.STATE onStatusReceived(final HttpResponseStatus responseStatus) throws Exception {
					result = (responseStatus.getStatusCode() == 200);
					return STATE.ABORT;
				}

				/**
				 * @see com.ning.http.client.AsyncHandler#onHeadersReceived(com.ning.http.client.HttpResponseHeaders)
				 */
				@Override
				public com.ning.http.client.AsyncHandler.STATE onHeadersReceived(final HttpResponseHeaders headers) throws Exception {
					return STATE.ABORT;
				}

				/**
				 * @see com.ning.http.client.AsyncHandler#onCompleted()
				 */
				@Override
				public Boolean onCompleted() throws Exception {
					return result;
				}
			});
		} catch (final IOException e) {
			throw new TransferFailedException(StringUtils.format("(exists %s)", resource), e);
		}
		try {
			return f.get();
		} catch (final InterruptedException e) {
			throw new TransferFailedException(StringUtils.format("(exists %s)", resource), e);
		} catch (final ExecutionException e) {
			throw new TransferFailedException(StringUtils.format("(exists %s)", resource), e);
		}
	}

	/**
	 * @see org.intelligentsia.keystone.api.artifacts.repository.RepositoryService#put(java.lang.String,
	 *      java.io.File)
	 */
	@Override
	public void put(final String resource, final File source) throws ResourceDoesNotExistException, TransferFailedException {
		// RequestBuilder builder = new RequestBuilder("PUT");
		// Request request =
		// builder.setUrl(locator.resolve(resource)).addHeader("name",
		// "value").setBody(source).build();
		if (!source.exists()) {
			throw new ResourceDoesNotExistException(source.getPath());
		}
		Response response = null;
		try {
			response = asyncHttpClient.preparePut(locator.resolve(resource)).setBody(source).execute().get();
			if (response.getStatusCode() == 404) {
				throw new ResourceDoesNotExistException(StringUtils.format("%s not found", resource));
			}
			if (response.getStatusCode() != 200) {
				throw new IllegalStateException("response code not 200 OK");
			}
			if (response.getStatusCode() != 200) {
				throw new TransferFailedException(StringUtils.format("error %s when transferring %s ", response.getStatusCode(), resource));
			}
		} catch (final InterruptedException e) {
			throw new TransferFailedException(StringUtils.format("(put %s %s)", resource, source.getPath()), e);
		} catch (final ExecutionException e) {
			throw new TransferFailedException(StringUtils.format("(put %s %s)", resource, source.getPath()), e);
		} catch (final IOException e) {
			throw new TransferFailedException(StringUtils.format("(put %s %s)", resource, source.getPath()), e);
		}
	}

	/**
	 * @see org.intelligentsia.keystone.api.artifacts.repository.RepositoryService#delete(java.lang.String)
	 */
	@Override
	public boolean delete(final String resource) throws ResourceDoesNotExistException {
		throw new KeystoneRuntimeException("not supported");
	}

	/**
	 * Inner URL locator.
	 * 
	 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
	 */
	private class URLLocator implements Locator<String> {

		/**
		 * @uml.property name="root"
		 */
		private final String root;

		/**
		 * Build a new instance of <code>URLLocator</code>
		 * 
		 * @param root
		 */
		public URLLocator(final String root) {
			super();
			this.root = root.endsWith("/") ? root : root + "/";
		}

		/**
		 * @see org.intelligentsia.keystone.api.artifacts.repository.Locator#getRoot()
		 * @uml.property name="root"
		 */
		@Override
		public String getRoot() {
			return root;
		}

		/**
		 * @see org.intelligentsia.keystone.api.artifacts.repository.Locator#resolve(java.lang.String)
		 */
		@Override
		public String resolve(final String target) {
			return new StringBuilder(root).append(target).toString();
		}

	}

}
