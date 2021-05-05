# Room manager

Application for allocating potential guests to rooms, based on specific policy.

# How to run tests and the application

##### Requirements

- Java 16
- Maven 3

##### Steps for tests

- Clone or download this repository
- To run unit tests, run `$ mvn clean test` in the main catalog. The results will be displayed on the console as well as
  saved in `/target/surefire-reports`
- To run unit and integration tests, run `$ mvn clean verify -Dserver.port=8181` in the main catalog (java
  property `-Dserver.port=8181` was added to avoid possible ports already in use collision). The results of
  the unit tests will be displayed on the console as well as saved in `/target/surefire-reports` and the results of the
  integration tests will be displayed on the console as well as saved in `/target/failsafe-reports`

##### Steps for running the application

- To run the application, run ` $ mvn spring-boot:run` in the main catalog. When running for the first time, it will
  take longer, because all dependencies must be downloaded by Maven to your local repository. The application will use
  port 8080 by default, but it can be configured in `application.yml` file
- While application is running, swagger UI is exposed on http://localhost:8080/hotel/swagger-ui.html - there is the
  endpoint `/api/v1/allocate` accepting free premium rooms count, free economy rooms count, and a list of potential
  guests (represented by an array of the amount they willing to pay). 
