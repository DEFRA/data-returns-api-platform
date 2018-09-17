# Data Returns API Platform
[![Build Status](https://travis-ci.org/DEFRA/data-returns-api-platform.svg?branch=master)](https://travis-ci.org/DEFRA/data-returns-api-platform)
[![Maintainability](https://api.codeclimate.com/v1/badges/58078d7827cdeaefe0f0/maintainability)](https://codeclimate.com/github/DEFRA/data-returns-api-platform/maintainability)
[![Test Coverage](https://api.codeclimate.com/v1/badges/58078d7827cdeaefe0f0/test_coverage)](https://codeclimate.com/github/DEFRA/data-returns-api-platform/test_coverage)
[![Licence](https://img.shields.io/badge/Licence-OGLv3-blue.svg)](http://www.nationalarchives.gov.uk/doc/open-government-licence/version/3)

The API platform provides a framework for developing RESTful API's using Spring Data REST for Java.

It provides a number of sensible defaults for both the API configuration and for building and verifyng API's which use this platform.

A RESTful API can be produced using this framework by simply defining the required data model using JPA (Java Persistence Annotations) and supplying a matching liquibase XML changelog to manage database migrations.

## Cloning
Cloning via SSH from behind a corporate firewall which blocks port 22:
```bash
git clone ssh://git@ssh.github.com:443/DEFRA/data-returns-api-platform
```

## Prerequisites
- Java 1.8
- (Optional) Maven 3.52 or greater (or use the supplied mvnw wrapper)


## Create a new API using the data-returns-api-platform

This project can be included using the [Jitpack](https://jitpack.io/) repository.

An example pom.xml using Jitpack to include the necessary dependencies:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>com.github.DEFRA.data-returns-api-platform</groupId>
        <artifactId>api-parent</artifactId>
        <version>{COMMIT_ID/RELEASE/TAG}</version>
    </parent>

    <groupId>com.example</groupId>
    <artifactId>example-api</artifactId>
    <version>1.0.0</version>
    <packaging>jar</packaging>

    <name>Example API</name>

    <repositories>
        <repository>
            <id>jitpack.io</id>
            <url>https://jitpack.io</url>
        </repository>
    </repositories>

    <pluginRepositories>
        <pluginRepository>
            <id>jitpack.io</id>
            <url>https://jitpack.io</url>
        </pluginRepository>
    </pluginRepositories>

    <dependencies>
        <dependency>
            <groupId>com.github.DEFRA.data-returns-api-platform</groupId>
            <artifactId>api-common</artifactId>
            <version>{COMMIT_ID/RELEASE/TAG}</version>
        </dependency>

        <dependency>
            <groupId>com.github.DEFRA.data-returns-api-platform</groupId>
            <artifactId>api-common</artifactId>
            <version>{COMMIT_ID/RELEASE/TAG}</version>
            <type>test-jar</type>
            <classifier>testcommons</classifier>
            <scope>test</scope>
        </dependency>
    </dependencies>
</project>
```
## Generating site reports
To generate all site reports to target/site:

```bash
./mvnw verify site
```

This report includes:

| Document          | Description |
| ---               | ---         |
|Javadoc            | Javadoc API documentation. |
|Test Javadoc       | Test Javadoc API documentation.|
|Surefire Report    | Report on the test results of the project.|
|Failsafe Report	| Report on the integration test results of the project. |
|Checkstyle         | Report on coding style conventions.|
|Source Xref        | HTML based, cross-reference version of Java source code.|
|Test Source Xref	| HTML based, cross-reference version of Java test source code.|
|FindBugs	        | Generates a source code report with the FindBugs Library.|
|JaCoCo	            | JaCoCo Coverage Report.|


To include the OWASP dependency security report:

```bash
./mvnw -P full-verify verify site
```

In addition to the reports listed above, this includes:

| Document          | Description |
| ---               | ---         |
|dependency-check	|Generates a report providing details on any published vulnerabilities within project dependencies. This report is a best effort and may contain false positives and false negatives.|

## Contributing to this project

If you have an idea you'd like to contribute please log an issue.

All contributions should be submitted via a pull request.

## License

THIS INFORMATION IS LICENSED UNDER THE CONDITIONS OF THE OPEN GOVERNMENT LICENCE found at:

http://www.nationalarchives.gov.uk/doc/open-government-licence/version/3

The following attribution statement MUST be cited in your products and applications when using this information.

>Contains public sector information licensed under the Open Government license v3

### About the license

The Open Government Licence (OGL) was developed by the Controller of Her Majesty's Stationery Office (HMSO) to enable information providers in the public sector to license the use and re-use of their information under a common open licence.

It is designed to encourage use and re-use of information freely and flexibly, with only a few conditions.
