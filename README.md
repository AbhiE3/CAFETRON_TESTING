# Cafetron Hybrid UI Automation Framework


## Smoke Test
Smoke tests were selected for the most critical end-to-end flows that confirm 
the application is stable enough for further testing. For example: login, basic 
navigation, menu access, placing an order, payment, and logout.


## sanity Test
Sanity tests were selected for focused validation of recently changed or important areas. 
These are quick checks to confirm that a specific module or fix is working as expected.

## Regression Test
Regression tests were selected for broader coverage of existing functionality 
to make sure new changes did not break already working features. These include 
positive, negative, edge case, and cross-module scenarios.









Selenium Java + TestNG hybrid framework for Cafetron UI automation.

This project is UI-only. It does not include API tests, backend validation, database checks, token checks, or localStorage assertions.

## Tech Stack

- Java
- Selenium WebDriver
- TestNG
- Maven
- Extent Reports
- Apache POI utility for Excel-driven data when needed
- Screenshots on test failure

## Default Application URL

The default target is:

```text
http://cafetron-frontend.vercel.app/
```

You can override it at runtime:

```bash
mvn clean test -DbaseUrl=https://your-url/
```

## Folder Structure

```text
src/main/java/com/cafetron
|-- base
|-- config
|-- data
|-- flows
|-- listeners
|-- pages
|-- reports
`-- utilities

src/test/java/com/cafetron/tests
|-- admin
|-- auth
|-- cart
|-- checkout
|-- defects
|-- e2e
|-- menu
|-- orders
|-- rbac
|-- registration
|-- vendor
`-- wallet
```

## Run Tests

Full suite:

```bash
mvn clean test
```

Specific suite:

```bash
mvn clean test -DsuiteXmlFile=testng-smoke.xml
```

Headless:

```bash
mvn clean test -Dheadless=true
```

Different browser:

```bash
mvn clean test -Dbrowser=edge
```

## Suite Files

- `testng.xml`
- `testng-smoke.xml`
- `testng-sanity.xml`
- `testng-regression.xml`
- `testng-rbac.xml`
- `testng-e2e.xml`
- `testng-defect.xml`
- `testng-uat.xml`
- `testng-integration.xml`
- `testng-usability.xml`

## Reports And Screenshots

- Extent report: `test-output/ExtentReports/Cafetron-Automation-Report.html`
- Failure screenshots: `test-output/Screenshots`
- TestNG/Surefire output: `target/surefire-reports`

## Credentials

Employee, vendor, and admin login tests use credentials from `src/test/resources/config.properties`
or matching system properties.

Vendor/admin credentials must be supplied before running role-specific tests. Registration through the UI
is reserved for registration test cases only.
