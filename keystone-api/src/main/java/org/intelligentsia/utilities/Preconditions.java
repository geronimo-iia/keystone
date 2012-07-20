package org.intelligentsia.utilities;

/**
 * Preconditions waiting for guava integration decision.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public enum Preconditions {
	;

	public static <T> T checkNotNull(final T argument) throws NullPointerException {
		return checkNotNull(argument, "");
	}

	public static <T> T checkNotNull(final T argument, final String message) throws NullPointerException {
		if (argument == null) {
			throw new NullPointerException(String.valueOf(message));
		}
		return argument;
	}
}
