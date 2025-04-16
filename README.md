Make sure Homebrew is installed.

In a terminal, run this command:

brew install allure
Run this command to see if it reports the latest version:

To enable the integration in your project:

Make sure you have the Allure Report adapter enabled for the test framework you use.

See the instructions in the adapter's documentation in Frameworks.

Add the Allure REST Assured integration to your project's dependencies.

Maven

xml
<!-- Define the version of Allure you want to use via the allure.version property -->
<properties>
    <allure.version>2.25.0</allure.version>
</properties>

<!-- Add allure-bom to dependency management to ensure correct versions of all the dependencies are used -->
<dependencyManagement>
<dependencies>
    <dependency>
        <groupId>io.qameta.allure</groupId>
        <artifactId>allure-bom</artifactId>
        <version>${allure.version}</version>
        <type>pom</type>
        <scope>import</scope>
    </dependency>
</dependencies>
</dependencyManagement>

<!-- Add necessary Allure dependencies to dependencies section -->
<dependencies>
<dependency>
    <groupId>io.qameta.allure</groupId>
    <artifactId>allure-rest-assured</artifactId>
    <scope>test</scope>
</dependency>
</dependencies>


mvn clean test - run your tests
You can generate a report using one of the following command:

mvn allure:serve
Report will be generated into temp folder. Web server with results will start.

mvn allure:report
Report will be generated tо directory: target/site/allure-maven/index.html
