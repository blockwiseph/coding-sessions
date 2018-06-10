Coding session solutions will be put in here.

Each session will start on a new branch, session/problem-name and then will be merged to master at the end of the session.

For the most part, each project will be an eclipse maven project. We will make heavy use of guice, lombok and spotbugs.

Visit the lombok website to see how to add the eclipse plugin.
https://projectlombok.org/setup/eclipse

The following is a boiler plate exerpt to include in all pom.xml files. It is to be included as a child of the <project> node.
This bootstraps a project with: 

- guice and multibindings (guice extension for dependency injection)
- lombok for eliminating boiler plate cruft 
- spotbugs for static code analysis
- junit, mockito, and hamcrest for unit testing

<dependencies>
	<dependency>
		<groupId>com.google.inject</groupId>
		<artifactId>guice</artifactId>
		<version>4.2.0</version>
	</dependency>
	<dependency>
		<groupId>com.google.inject.extensions</groupId>
		<artifactId>guice-multibindings</artifactId>
		<version>4.2.0</version>
	</dependency>
	<dependency>
		<groupId>org.projectlombok</groupId>
		<artifactId>lombok</artifactId>
		<version>1.16.20</version>
		<scope>provided</scope>
	</dependency>
	<dependency>
		<groupId>junit</groupId>
		<artifactId>junit</artifactId>
		<version>4.12</version>
		<scope>test</scope>
	</dependency>
	<dependency>
		<groupId>org.mockito</groupId>
		<artifactId>mockito-all</artifactId>
		<version>1.10.19</version>
		<scope>test</scope>
	</dependency>
	<dependency>
		<groupId>org.hamcrest</groupId>
		<artifactId>hamcrest-library</artifactId>
		<version>1.3</version>
		<scope>test</scope>
	</dependency>
</dependencies>
<build>
	<plugins>
		<plugin>
			<groupId>com.github.spotbugs</groupId>
			<artifactId>spotbugs-maven-plugin</artifactId>
			<version>3.1.3</version>
			<configuration>
				<effort>max</effort>
			</configuration>
			<dependencies>
				<dependency>
					<groupId>com.github.spotbugs</groupId>
					<artifactId>spotbugs</artifactId>
					<version>3.1.3</version>
				</dependency>
			</dependencies>
		</plugin>
	</plugins>
</build>