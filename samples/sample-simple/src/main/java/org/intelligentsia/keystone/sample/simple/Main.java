package org.intelligentsia.keystone.sample.simple;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simple Sample.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class Main {
	private final Logger logger = LoggerFactory.getLogger(Main.class);

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		new Main().sayHello();
	}

	public void sayHello() {
		logger.info("You're in Simple sample test loaded with his dependencies.");
		logger.info("And we say yo!");
	}
}
