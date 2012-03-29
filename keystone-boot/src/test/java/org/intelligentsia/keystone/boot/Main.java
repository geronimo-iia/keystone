package org.intelligentsia.keystone.boot;

import java.util.Scanner;

/**
 * Little Main class for testing by hand...
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class Main {

	public static void main(final String[] args) throws Exception {
		// Console.setLogFile(new File("./test.log"));
		System.out.println("In Main: ");

		Scanner sc = new Scanner(System.in);

		if (sc.nextInt() == 0) {
			System.out.println("Call autoRestart");
			try {
				Restarter.autoRestart(new Runnable() {

					public void run() {
						System.out.println("Run before auto restart");
					}
				});
			} catch (IllegalStateException e) {
				System.out.println(e.getMessage());
			}
			System.out.println("Call restartWith");
			try {
				Restarter.restartWith(new Runnable() {

					public void run() {
						System.out.println("Run before restart with");
					}
				}, "org.intelligentsia.keystone.boot.Main");
			} catch (IllegalStateException e) {
				System.out.println(e.getMessage());
			}
		}

		System.out.println("Exit in main");
	}

}
