# Allure Report Integration Guide

## Prerequisites
Make sure that **Homebrew** is installed on your system.

## Installation
1. Open a terminal and run the following command to install Allure:
   ```bash
   brew install allure
   ```

2. Verify the installation by checking the version:
   ```bash
   allure --version
   ```

## Enable Allure Report Integration in Your Project
### Step 1: Setup Allure Report Adapter
Ensure that you have the **Allure Report adapter** enabled for the test framework you are using. Refer to the adapter's documentation for your specific framework in the [Allure Frameworks Guide](https://docs.qameta.io/allure/#_frameworks).

### Step 2: Add Allure REST-Assured Integration
For projects using **Maven**, add the Allure REST Assured dependencies:

1. Define the Allure version you wish to use in the `<properties>` section of your `pom.xml`:
   ```xml
   <properties>
       <allure.version>2.25.0</allure.version>
   </properties>
   ```

2. Add the Allure BOM (Bill of Materials) to your `dependencyManagement` to ensure correct versions of all dependencies:
   ```xml
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
   ```

3. Add the required Allure dependencies to the `<dependencies>` section:
   ```xml
   <dependencies>
       <dependency>
           <groupId>io.qameta.allure</groupId>
           <artifactId>allure-rest-assured</artifactId>
           <scope>test</scope>
       </dependency>
   </dependencies>
   ```

## Running Your Tests
Run your tests with Maven:
```bash
mvn clean test
```

## Generating Allure Reports
You can generate and view test reports using the following command:
```bash
mvn allure:serve
```

This command will build the report and serve it via a local web server, which opens automatically in your default web browser.

---

For more details on Allure, visit the [official documentation](https://docs.qameta.io/allure/).