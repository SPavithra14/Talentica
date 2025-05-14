# Inventory API Automation Project

This project automates the testing of an Inventory Management API using **RestAssured** and **TestNG**, with test reporting integrated via **ExtentReports**.

# Project Structure
Talentica
 src/test/java
    Listeners
    Payload
    TestSuite
  Testng.xml
  report
  test-output
  pom.xml

  
# Technologies Used

- Java 8+
- RestAssured
- TestNG
- ExtentReports
- Docker 
- Maven 
- Eclipse

# Running Inventory API via Docker
 Step 1: Pull the Docker image containing the API
docker pull automaticbytes/demo-app

 Step 2: Run the Docker image and expose port 3100
docker run -p 3100:3100 automaticbytes/demo-app
Once the container is running, verify the API is up by visiting:
http://localhost:3100/api
This will be the base URL for your test cases.

# Features

- Validate GET and POST operations of the Inventory API.
- Assertion of fields like id, name, price, and image.
- ExtentReports HTML report generation.
- Test coverage includes:
  - Retrieving all items
  - Retrieving by ID
  - Adding new items
  - Duplicate item handling
  - Missing field validation

 ##  How to Run Tests
1. Make sure the Inventory API is running via Docker on `localhost:3100`.
2. Open the project in your Java IDE (Eclipse).
3. Run Testng.xml as a **TestNG Test**.
4. View the generated report at:
/report/inventoryReport.html

