# Bootstrap your java application

Keystone [project][prj] is a bootstrap that loads your application and its dependencies into a single archive.
With his maven plugin, its use is very easy!

[prj]: http://intelligents-ia.com/index.php/category/technique/keystone

## Configure your project

See more [doc][dc-wiki] on wiki.

1. Configure Your POM
```xml
	<build>
	    <plugins>
	        <plugin>
	            <groupId>org.intelligentsia.keystone</groupId>
	            <artifactId>plugin</artifactId>
	            <version>2.5</version>
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
```

2. Add some repositories

```xml
    <pluginRepositories>
        <pluginRepository>
            <id>intelligents-ia</id>
            <name>Intelligents-ia Repository</name>
            <url>http://intelligents-ia.com/maven2</url>
        </pluginRepository>
    </pluginRepositories>
    <repositories>
        <repository>
            <id>intelligents-ia</id>
            <name>Intelligents-ia Repository</name>
            <url>http://intelligents-ia.com/maven2</url>
        </repository>
    </repositories>
```


[dc-wiki]: https://github.com/geronimo-iia/keystone/wiki


## Use extra features: Restart your application when needed!

1. Add a new dependency on your project
```xml
<dependency>
	<groupId>org.intelligents-ia.keystone</groupId>
	<artifactId>keystone-boot</artifactId>
	<version>2.5</version>
</dependency>
```

2. When you want to restart, simply throw this exception
```java
throw new KeystoneException(KeystoneException.Operation.RESTART);
```


