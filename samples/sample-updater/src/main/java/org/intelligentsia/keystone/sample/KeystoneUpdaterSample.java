package org.intelligentsia.keystone.sample;

import java.io.File;
import java.util.Scanner;

import org.intelligentsia.keystone.boot.Arguments;
import org.intelligentsia.keystone.boot.Console;
import org.intelligentsia.keystone.boot.KeystoneException;

/**
 * KeystoneUpdaterSample class is our updater sample.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class KeystoneUpdaterSample {

	public static void main(final String[] args) throws Exception {
		// Set log file
		Console.setLogFile(new File("./echo.log"));	
		// some information in console
		Console.INFO("Coucou, Welcome on Sample project " + Arguments.loadArguments(args));
		Console.INFO("Enter 0 to restart, 1 to clean, 2 to end");
		// wait key
		final Scanner scanner = new Scanner(System.in);
		int code = scanner.nextInt();
		if (code == 0) {
			Console.INFO("Waiting 5 secondes before restart");
			Thread.sleep(5000);
			throw new KeystoneException(KeystoneException.Operation.RESTART);
		} else if (code == 1) {
			Console.INFO("Waiting 5 secondes before clean");
			Thread.sleep(5000);
			throw new KeystoneException(KeystoneException.Operation.CLEAN);
		}
		return;
	}
}
