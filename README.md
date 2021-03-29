## Overview:
The framework implemented to test https://gorest.co.in/ Users Rest APIs.

## Setup:
* Used Java 1.8 SDK for Maven project 
* Run `git clone https://github.com/davitgrigoryan1992/Trial_task_for_Disco.git`
* install `cucumber.js` plugin from IDE settings
* install MySQL Database Service, create database table with name`usersdb` and `users` inside it.

## Framework structure
* Runner file is `src/test/java/gorest/co/in/integration/users/UsersApiRunner.java`
* Cucumber scenarios are located in `src/test/java/resources/features/users/UsersAPI.feature` file
* Cucumber step definition are located in `src/test/java/gorest/co/in/integration/users/UsersStepDefinitions.java` file
* All API endpoints are declared in `src/test/java/gorest/co/in/integration/helper/ApiForUsers.java` file
* Functions for storing and removing data from DB are implemented in `src/test/java/gorest/co/in/integration/helper/StoreTestDataMySQL.java` file

## Tests
* All scenarios are independent of each other
* All created data is removed after each scenario
* To run the first scenario you need to install MySQL locally, create database table with name`usersdb` and `users`
 inside it.
* Requests and response logs are displayed during the run

## Run tests:
* Install dependencies form pom.xml file
* Opening `UsersApiRunner.java` file you will see that the "feature" property contains the location of the feature file
* The "glue" property contains the location of the "glue code"
* The "tags" property will help to run a specific scenario 
* Replace the tag in the tags property with the tag of the scenario number you wish to run
* If you right click on this file and `Run UsersApiRunner.java`, it will execute all scenarios in the feature file with that tag
* Right-click on the file and `Run UsersApiRunner.java` – the scenario will execute the gherkin steps in the order they are written

## Run all tests using maven commands
* Run all tests using `mvn clean install -U -Dmaven.test.failure.ignore=true` command

## Run specific scenario from cmd 
* Run `mvn test -Dcucumber.options="--tags @scenario1"` command

## Reporting
* Report generated in `target\maven-cucumber-reports\overview-features.html` file
* `maven-cucumber-reporting` is used for generating report

## Frameworks and plugins:
* Maven
* RestAssured
* Cucumber 
* Junit

## Database
MySQL Database Service
