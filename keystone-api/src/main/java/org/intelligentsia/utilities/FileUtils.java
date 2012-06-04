package org.intelligentsia.utilities;

import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.FileChannel;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

/**
 * FileUtils class group some utilities methods around file management.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class FileUtils {

	/**
	 * Copy source file to destination. If destination is a path then source
	 * file name is appended. If destination file exists then: overwrite=true -
	 * destination file is replaced; overwrite=false - exception is thrown. For
	 * larger files (20Mb) we use streams copy, and for smaller files we use
	 * channels.
	 * 
	 * @param src
	 *            source file
	 * @param dst
	 *            destination file or path
	 * @param overwrite
	 *            overwrite destination file
	 * @exception IOException
	 *                I/O problem
	 * @exception IllegalArgumentException
	 *                illegal argument
	 */
	public static void copy(final File src, File dst, final boolean overwrite) throws IOException, IllegalArgumentException {
		// checks
		if (!src.isFile() || !src.exists()) {
			throw new IllegalArgumentException("Source file '" + src.getAbsolutePath() + "' not found!");
		}

		if (dst.exists()) {
			if (dst.isDirectory()) // Directory? -> use source file name
			{
				dst = new File(dst, src.getName());
			} else if (dst.isFile()) {
				if (!overwrite) {
					throw new IllegalArgumentException("Destination file '" + dst.getAbsolutePath() + "' already exists!");
				}
			} else {
				throw new IllegalArgumentException("Invalid destination object '" + dst.getAbsolutePath() + "'!");
			}
		}

		final File dstParent = dst.getParentFile();
		if (!dstParent.exists()) {
			if (!dstParent.mkdirs()) {
				throw new IOException("Failed to create directory " + dstParent.getAbsolutePath());
			}
		}

		long fileSize = src.length();
		if (fileSize > 20971520l) { // for larger files (20Mb) use streams
			final FileInputStream in = new FileInputStream(src);
			final FileOutputStream out = new FileOutputStream(dst);
			try {
				int doneCnt = -1;
				final int bufSize = 32768;
				final byte buf[] = new byte[bufSize];
				while ((doneCnt = in.read(buf, 0, bufSize)) >= 0) {
					if (doneCnt == 0) {
						Thread.yield();
					} else {
						out.write(buf, 0, doneCnt);
					}
				}
				out.flush();
			} finally {
				try {
					in.close();
				} catch (final IOException e) {
				}
				try {
					out.close();
				} catch (final IOException e) {
				}
			}
		} else { // smaller files, use channels
			final FileInputStream fis = new FileInputStream(src);
			final FileOutputStream fos = new FileOutputStream(dst);
			final FileChannel in = fis.getChannel(), out = fos.getChannel();
			try {
				long offs = 0, doneCnt = 0;
				final long copyCnt = Math.min(65536, fileSize);
				do {
					doneCnt = in.transferTo(offs, copyCnt, out);
					offs += doneCnt;
					fileSize -= doneCnt;
				} while (fileSize > 0);
			} finally { // cleanup
				try {
					in.close();
				} catch (final IOException e) {
				}
				try {
					out.close();
				} catch (final IOException e) {
				}
				try {
					fis.close();
				} catch (final IOException e) {
				}
				try {
					fos.close();
				} catch (final IOException e) {
				}
			}
		}
		// http://www.ibm.com/developerworks/java/library/j-jtp09275.html?ca=dgr-jw22JavaUrbanLegends
		// System.out.println(">>> " + String.valueOf(src.length() / 1024) +
		// " Kb, " +
		// String.valueOf(System.currentTimeMillis() - q));
	}

	/**
	 * Copy stream utility.
	 * 
	 * @param in
	 *            input stream
	 * @param out
	 *            output stream
	 * @throws IOException
	 */
	public static void copyStream(final InputStream in, final OutputStream out) throws IOException {
		FileUtils.copyStream(in, out, -1);
	}

	/**
	 * Copy stream utility.
	 * 
	 * @param in
	 *            input stream
	 * @param out
	 *            output stream
	 * @param maxLen
	 *            maximum length to copy (-1 unlimited).
	 * @throws IOException
	 */
	public static void copyStream(final InputStream in, final OutputStream out, final long maxLen) throws IOException {
		final byte[] buf = new byte[4096 * 2];
		int len;
		if (maxLen <= 0) {
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
		} else {
			long max = maxLen;
			while ((len = in.read(buf)) > 0) {
				if (len <= max) {
					out.write(buf, 0, len);
					max -= len;
				} else {
					out.write(buf, 0, (int) max);
					break;
				}
			}
		}
	}

	/**
	 * Close in silent.
	 * 
	 * @param closeable
	 */
	public static void close(final Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (final IOException exception) {
			}
		}
	}

	/**
	 * Utility to delete file (directory or single file)
	 * 
	 * @param from
	 * @return
	 */
	public static boolean delete(final File from) {
		if ((from != null) && from.exists()) {
			if (from.isDirectory()) {
				for (final File child : from.listFiles()) {
					FileUtils.delete(child);
				}
			}
			return from.delete();
		}
		return false;
	}

	/**
	 * Create a new temporary directory. Use something like
	 * {@link #recursiveDelete(File)} to clean this directory up since it isn't
	 * deleted automatically
	 * 
	 * @return the new directory
	 * @throws IOException
	 *             if there is an error creating the temporary directory
	 */
	public static File createTempDir() throws IOException {
		final File sysTempDir = new File(System.getProperty("java.io.tmpdir"));
		File newTempDir;
		final int maxAttempts = 9;
		int attemptCount = 0;
		do {
			attemptCount++;
			if (attemptCount > maxAttempts) {
				throw new IOException("The highly improbable has occurred! Failed to create a unique temporary directory after " + maxAttempts + " attempts.");
			}
			final String dirName = UUID.randomUUID().toString();
			newTempDir = new File(sysTempDir, dirName);
		} while (newTempDir.exists());

		if (newTempDir.mkdirs()) {
			return newTempDir;
		} else {
			throw new IOException("Failed to create temp dir named " + newTempDir.getAbsolutePath());
		}
	}

	/**
	 * Read data file as string.
	 * 
	 * @param data
	 * @return
	 * @throws IOException
	 */
	public static String readAsString(final File data) throws IOException {
		final StringBuilder builder = new StringBuilder();
		FileReader reader = null;
		try {
			reader = new FileReader(data);
			final char[] buffer = new char[2048];
			int max = 0;
			while ((max = reader.read(buffer, 0, 2048)) > 0) {
				if (max > 0) {
					builder.append(buffer, 0, max);
				}
			}
		} finally {
			reader.close();
		}
		return builder.toString();
	}

	/**
	 * Waiting for file.
	 * 
	 * @param waitingTargetFile
	 *            target file to wait
	 * 
	 * @param timeout
	 *            (ms)if waitingTargetFile exists, time out for waiting access
	 *            on this file.
	 * 
	 * @throws TimeoutException
	 */
	public void waiting(final String waitingTargetFile, final int timeout) throws TimeoutException {
		// waits while waitingTargetFile exits and cannot be overwritten
		int waiting = 0;
		while (new File(waitingTargetFile).exists() && !new File(waitingTargetFile).canWrite()) {
			try {
				Thread.sleep(100);
			} catch (final InterruptedException e) {
				e.printStackTrace(System.err);
			}
			waiting += 500;
			if (waiting > timeout) {
				throw new TimeoutException();
			}
		}
	}

	/**
	 * Download target url to target file and check checksum (if provided).
	 * 
	 * @param target
	 *            local target file (will be deleted if ever exist)
	 * @param targetUrl
	 *            target Url
	 * @param checksum
	 *            checksum to use
	 * @return true if al is ok.
	 */
	public static boolean download(final File target, final String targetUrl, final String checksum) {
		if ((targetUrl == null) || "".equals(targetUrl)) {
			return false;
		}
		if (target.exists()) {
			target.delete();
		}
		if (!target.getParentFile().exists()) {
			target.getParentFile().mkdirs();
		}
		URL url = null;
		try {
			url = new URL(targetUrl);
		} catch (final MalformedURLException ex) {
			target.delete();
			return false;
		}
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {

			final URLConnection conn = url.openConnection();
			inputStream = conn.getInputStream();
			outputStream = new BufferedOutputStream(new FileOutputStream(target));
			FileUtils.copyStream(inputStream, outputStream);
			outputStream.flush();
			// check if checksum is provided
			final Boolean check = checksum != null ? CheckSum.validate(target.getPath(), checksum) : Boolean.TRUE;
			return check;
		} catch (final IOException ex) {
			target.delete();
			return false;
		} finally {
			FileUtils.close(inputStream);
			FileUtils.close(outputStream);
		}
	}

}
