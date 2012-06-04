/**
 * 
 */
package org.intelligentsia.utilities;

/**
 * String Utils.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class StringUtils {

	/**
	 * String replace utility.
	 * 
	 * @param text
	 * @param repl
	 * @param with
	 * @return
	 */
	public static String replace(final String text, final String repl, final String with) {
		if ((text == null) || (repl == null) || (with == null) || (repl.length() == 0)) {
			return text;
		}
		final StringBuilder buf = new StringBuilder(text.length());
		int start = 0, end = 0;
		while ((end = text.indexOf(repl, start)) != -1) {
			buf.append(text.substring(start, end)).append(with);
			start = end + repl.length();
		}
		buf.append(text.substring(start));
		return buf.toString();
	}

	/**
	 * String format utility.
	 * 
	 * @param template
	 * @param args
	 * @return
	 */
	public static String format(String template, final Object... args) {
		template = String.valueOf(template); // null -> "null"

		// start substituting the arguments into the '%s' placeholders
		final StringBuilder builder = new StringBuilder(template.length() + (16 * args.length));
		int templateStart = 0;
		int i = 0;
		while (i < args.length) {
			final int placeholderStart = template.indexOf("%s", templateStart);
			if (placeholderStart == -1) {
				break;
			}
			builder.append(template.substring(templateStart, placeholderStart));
			builder.append(args[i++]);
			templateStart = placeholderStart + 2;
		}
		builder.append(template.substring(templateStart));

		// if we run out of placeholders, append the extra args in square braces
		if (i < args.length) {
			builder.append(" [");
			builder.append(args[i++]);
			while (i < args.length) {
				builder.append(", ");
				builder.append(args[i++]);
			}
			builder.append(']');
		}

		return builder.toString();
	}
}
