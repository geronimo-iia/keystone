KEYSTONE : Bootstrap your java application
==========================================


**Keystone** [project](http://intelligents-ia.com/index.php/category/technique/keystone "Keystone Web site") is a bootstrap that loads your application and its dependencies from a single archive.  
With his **maven plugin** and few line of configuration, your application will be available with all his dependencies in a single archive.

The four main ideas which make the difference with other solution are:

* all dependencies are not exploded in final archive, 'jar' file stay without anny modification, so manifest also...
* user our maven plugin to package all that you need (and we'll loading dependencies automatically for you) 
* you could load native library
* you could restart your application 

So, you will be able to run it as this: 

	java -jar myapp.jar


This project was born after some code on another project named 'winstone'. 
Class loading and dependencies packaging can be a nightmare when you did more than just a war file to deploy (war can be very difficult also, .. I known ...)

This project is now used for many application, like:
* distribution of tool (database managment, batch processing)
* installation tool with gui
* web server (winstone)
* little games 

So it's time to start


Configure your project
======================

Add repositories 
----------------

    <repositories>
		<repository>
			<id>intelligents-ia-releases</id>
			<name>Intelligents-ia releases repository</name>
			<url>http://mvn.intelligents-ia.com/releases</url>
			<releases>
				<enabled>true</enabled>
			</releases>
			<snapshots>
				<enabled>false</enabled>
			</snapshots>
		</repository>
	</repositories>
	<pluginRepositories>
		<pluginRepository>
			<id>intelligents-ia-releases</id>
			<name>Intelligents-ia releases repository</name>
			<url>http://mvn.intelligents-ia.com/releases</url>
			<releases>
				<enabled>true</enabled>
			</releases>
			<snapshots>
				<enabled>false</enabled>
			</snapshots>
		</pluginRepository>
	</pluginRepositories>


*note: with maven 3, you could omit "pluginRepositories" tag*


Configure keystone maven plugin
-------------------------------


	<build>
	    <plugins>
	        <plugin>
	            <groupId>org.intelligents-ia.keystone</groupId>
	            <artifactId>keystone-plugin</artifactId>
	            <version>3.2</version>
	            <executions>
	                <execution>
	                    <id>make-assembly</id>
	                    <phase>package</phase>
	                    <configuration>
	                        <mainClass>your.class.Main</mainClass>
	                    </configuration>
	                    <goals>
	                        <goal>custom</goal>
	                    </goals>
	                </execution>
	            </executions>
	        </plugin>
	    </plugins>
	</build>
	
	
The single archive will be builded with your maven dependencies and main class is 'your.class.Main'.




Keystone Maven Plugin Parameters
==================================

* **mainClass**: your class ‘Main’ which implements:  
```public static void main(String[] args) ```
* **cleanUpLib**: true | false (default true),  
clean up folder named “lib” at startup before extraction of initial archive
* **cleanUpBeforeShutdown**: true | false (default is false),  
clean up folder named “lib” at shutdown
* **info**: true | false (default is false),  
level ‘INFO’ logs generated by the Keystone bootsrap
* **verbose**: true | false (default is false),  
level ‘VERBOSE’ logs generated by the Keystone bootsrap
* **logFile**: Log file path.  
Per default, all logs go on standard output.
* **minimalJvmVersion**: minimal JVM specification version required. 
* **includeJavaHomeLib**: true | false (default is true),  
add to classpath application, archives from the JAVA_HOME / lib if it exists
* **includeSystemClassLoader**: true | false (default is true).  
Adding the system classloader as a parent application class loaders
* **explodeDirectory**: Root extracting archives embark with the bootstrap.  
By default, this directory is the current directory if write operations are allowed on it, otherwise it is a temporary folder that is used.
* **finalName**: Final artifact name.  
By default, this is the name of the original artefact suffix “-boot”. Example for “sample.jar”, it will be “sample-boot.jar”
* **replaceProjectArtifact**: true | false (default is false).  
If enabled, the archive packaged artifact replaces the current project
* **includedScope**: regular expression, specify which dependencies scope will be included. By default, only 'test' scope are excluded.
* **natives**: add a list of native libraries.  
* **libraries**: add a list of extra java libraries. Each path can be a file or a directory (Not recursive).


Each path can be a file or a directory (Not recursive).

	<natives>
		<paths>
			<path>${basedir}/extra/timer.dll</path>
			<path>${basedir}/extra/timer.so</path>
			<path>${basedir}/extra-natives</path>
		</paths>
	</natives>


	<libraries>
		<paths>
			<path>${basedir}/h2-driver.jar</path>
			<path>${basedir}/drivers</path>
		</paths>
	</libraries>


Native libraries are supported in both way
* with plugin parameters
* within embedded jar



Use extra features: Restart your application when needed!
=========================================================


1. add a new dependency on your project
2. When you want to restart, simply throw this exception


In your pom:

	<dependency>
		<groupId>org.intelligents-ia.keystone</groupId>
		<artifactId>keystone-boot</artifactId>
		<version>3.2</version>
	</dependency>



In your code:

	throw new KeystoneException(KeystoneException.Operation.RESTART);





Releases Notes
==============

3.2:
----

* configure dependencies scope with maven plugin, parameter 'includedScope'. By default, only 'test' scope is excluded (since 3.1).
* add optional parameter 'minimalJvmVersion' to check minimal jvm version on start
* update deprecated logback configuration
* update deprecated maven properties declaration
* update parent pom (1.4.7, to use new distribution management configuration)

3.1
------

* exclude only “test” scope artifacts
* add native support
    * be able to load embedded native library file
    * be able to load native library in classpath at runtime
* add plugin configuration utilities:
    * add a path set of native libraries
    * add a path set of extra java libraries

3.0
------

* Integrate super pom 1.4.4 and add git.properties
* Integrate keystone sample in this project
* use org.intelligents-ia:intelligents-ia as super pom
* extract all keystone-kernel and extras in another project:
    * keystone project focus on bootstrap application, maven integration, restart features and pattern to update embedded java application at runtime
    * keystone-kernel focus on micro kernel container (yes, i like playing…)

2.6.0
--------

* Bootstrap: add stack trace when exception occurs
* Use Jackson libraries from github: com.fasterxml.jackson
* Fix header of license
* Add copy constructor on Version (keystone api)
