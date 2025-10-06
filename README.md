**QA Automation Challenge
Overview**

This project contains automated UI tests for the XE.gr website.

The tests cover the following scenarios:
- Search for rental properties.
- Select a specific area and all suggestions.
- Set price and square meter filters.
- Verify property ads are within the specified price and size ranges.
- Verify each ad contains less than a defined number of images.
- Verify ads are sorted in descending order by price.
- Verify contact information is visible in each ad (unsuccessful implementation).

**Prerequisites Installations**
To execute the test you will need 
1) Java 8 installed.
You can check if you have java installed with the following command on cmd:
java -version 

2) Maven installed.
You can check if you have Maven installed with the following command on cmd:
mvn -version

3) ChromeDriver must be available in your system PATH or configured in the code.

4) IntelliJ Community Edition
- You will need to head to Project Structure and setup OpenJDK-25. In case you don't have it, you click Download JDK and select Version 25 & Vendor Oracle OpenJDK25 and click Download. Please wait until the download is completed and press Ctrl + F9 to rebuild your project. Afterwards, head back to  Project Structure, click on Modules and select
Language Level 8. Rebuild your project to ensure that everything runs smoothly.

**Test Execution**
How to Run the tests
1) IntelliJ IDEA is required to run the tests. Open the project in IntelliJ, navigate to SmoteTest and click Run (or SHIFT + F10).

2)Run the tests using Maven:
mvn clean test

**Test Data**
There is a TestData Class with data entered that can be alternated for testing.
Optionally, test data can be moved to a database or external file, but for simplicity, constants are used. 
public static final String AREA_NAME = "Παγκράτι";
public static final int MINIMUM_PRICE = 200;
public static final int MAXIMUM_PRICE = 700;
public static final int MINIMUM_SQUARE_METERS = 75;
public static final int MAXIMUM_SQUARE_METERS = 150;
public static final int MAXIMUM_IMAGES_PER_AD = 30;

**Known Issues**
- The contact info verification test is not fully implemented and may not work correctly.
- Some dynamic elements may cause the test to fail if the page loads slowly.
- Some tests rely on scrolling and timing (You will notice a lot Thread.sleep inside the code), so execution may vary slightly depending on network speed and system performance.

**Notes on Test Execution**
- Test execution order is determined by TestNG priority annotations. It is highly recommended to run the tests in the given order to ensure that everything works.
