package org.intelligentsia.keystone.boot;

import java.io.File;

import junit.framework.TestCase;

/**
 * BootStrapTest dummy.
 * 
 * @author <a href="mailto:jguibert@intelligents-ia.com" >Jerome Guibert</a>
 */
public class BootStrapTest extends TestCase {

	public void testLaunch() throws Exception {
		BootStrap.main(new String[] { Main.class.getName() });
	}

	public void testLaunchFromDefault() throws Exception {
		BootStrap.main(new String[] {});
	}

	public void testLaunchWithExternal() throws Exception {
		BootStrap.main(new String[] { "--Main-Class=" + Main.class.getName(), "--BootStrap.explodeDirectory=." + File.separator + "target" + File.separator + "test-home", "--BootStrap.extraLibrariesFolderPath=src" + File.separator + "external-resources-test" });
	}
}
