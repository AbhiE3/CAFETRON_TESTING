# TestNG Groups in Cafetron Automation Framework

## Purpose

In this project, `groups` are used to organize and control automated test execution based on testing objective, risk, and execution scope.

They are not just mentioned for documentation. They are implemented in the automation framework using TestNG group support.

The grouping is not based on Excel. Excel can be used for deliverables, reporting, or test case documentation, but the actual execution control happens inside the Java test classes and TestNG XML suite files.

## Why Groups Were Mentioned

Groups were mentioned because this Cafetron automation project needs different execution levels:

- Quick build verification
- Focused validation after changes
- Full regression coverage
- Role-based access checks
- User acceptance coverage
- Integration and end-to-end workflow validation
- Defect retesting

Without groups, every test would need to be run together or duplicated into separate classes. TestNG groups solve this by allowing one test method to belong to multiple execution categories.

## Where Groups Are Implemented

Groups are implemented in the test methods using the TestNG `@Test` annotation.

Example:

```java
@Test(description = "TC-001: Verify employee login with valid credentials",
        groups = {"smoke", "regression", "rbac", "uat"})
public void verifyEmployeeLoginWithValidCredentials() {
    // test steps
}
```

This means the same login test can run as part of:

- Smoke testing
- Regression testing
- RBAC testing
- UAT testing

So the test is written once, but reused in multiple suites.

## Where Groups Are Used

Groups are used in the TestNG XML suite files through `<groups>` and `<include>` tags.

Example from `testng-smoke.xml`:

```xml
<groups>
    <run>
        <include name="smoke"/>
    </run>
</groups>
```

This tells TestNG to execute only the tests that are tagged with the `smoke` group.

## Suite Files Created for Groups

The project contains separate suite files for different group executions:

| Suite File | Included Group | Purpose |
|---|---:|---|
| `testng-smoke.xml` | `smoke` | Runs critical tests to confirm build stability |
| `testng-sanity.xml` | `sanity` | Runs focused checks for important or recently changed areas |
| `testng-regression.xml` | `regression` | Runs broad coverage to verify existing functionality |
| `testng-rbac.xml` | `rbac` | Runs role-based access control validations |
| `testng-uat.xml` | `uat` | Runs business-critical user acceptance scenarios |
| `testng-integration.xml` | `integration` | Runs tests covering interaction between modules |
| `testng-e2e.xml` | `e2e` | Runs complete end-to-end workflow tests |
| `testng-defect.xml` | `defect` | Runs defect-related validation and retesting scenarios |
| `testng-usability.xml` | `usability` | Runs UI feedback, navigation, and usability checks |

## Group Count in the Project

Current group usage across the test classes:

| Group | Number of Tagged Tests |
|---|---:|
| `regression` | 54 |
| `defect` | 24 |
| `uat` | 20 |
| `integration` | 16 |
| `usability` | 16 |
| `smoke` | 14 |
| `rbac` | 13 |
| `sanity` | 10 |
| `e2e` | 1 |

This shows that regression has the widest coverage, while smoke and sanity are smaller, faster, and more focused.

## Basis for Each Group

| Group | Basis of Selection | Example Impact |
|---|---|---|
| `smoke` | Critical flows required to confirm that the application is stable enough for testing | Quickly verifies login, menu access, cart, wallet, vendor, and admin entry points |
| `sanity` | Focused checks for specific modules, fixes, or important validations | Confirms changed or sensitive areas without running the full suite |
| `regression` | Broad existing functionality that must not break after new changes | Protects the full application flow from side effects |
| `rbac` | Role and permission-related scenarios | Ensures employee, vendor, admin, and unauthenticated access rules are respected |
| `uat` | Business-facing flows aligned with user acceptance needs | Confirms key user journeys are ready from a business perspective |
| `integration` | Scenarios where multiple modules interact | Validates flows like cart to checkout, order processing, and vendor queue behavior |
| `e2e` | Complete workflow from start to finish | Verifies an end-to-end journey across user and vendor actions |
| `defect` | Tests created around known defects or defect-prone areas | Helps retest fixed defects and prevent repeated issues |
| `usability` | UI behavior, feedback, navigation, and validation messages | Improves user-facing quality and clarity |

## How to Run Group-Based Suites

The Maven Surefire plugin is configured to accept a TestNG suite file through the `suiteXmlFile` property.

Default suite:

```bash
mvn clean test
```

Smoke suite:

```bash
mvn clean test -DsuiteXmlFile=testng-smoke.xml
```

Sanity suite:

```bash
mvn clean test -DsuiteXmlFile=testng-sanity.xml
```

Regression suite:

```bash
mvn clean test -DsuiteXmlFile=testng-regression.xml
```

RBAC suite:

```bash
mvn clean test -DsuiteXmlFile=testng-rbac.xml
```

Defect suite:

```bash
mvn clean test -DsuiteXmlFile=testng-defect.xml
```

## Impact Created by Groups

Using groups created the following impact in the project:

| Impact Area | Benefit |
|---|---|
| Faster execution | Smoke and sanity suites can run quickly without executing the complete regression suite |
| Better test control | Specific test categories can be executed based on need |
| Less duplication | One test can be reused in multiple suites by adding multiple group names |
| Risk-based testing | Critical and high-impact areas can be prioritized |
| Better maintenance | Tests remain in their functional classes while execution is controlled separately |
| CI/CD readiness | Different pipelines can trigger smoke, sanity, or regression suites independently |
| Clear reporting | Test results can be analyzed by execution purpose |
| Defect confidence | Defect-specific tests can be rerun separately after fixes |

## Practical Example

A login test is tagged like this:

```java
groups = {"smoke", "regression", "rbac", "uat"}
```

Reason:

- It is `smoke` because login is a critical entry point.
- It is `regression` because login must keep working after every change.
- It is `rbac` because login behavior depends on user role.
- It is `uat` because business users must be able to access the application.

This single test can run in four different suites without duplicating code.

## Best Explanation for Project Review

We used TestNG groups in the Cafetron automation framework to control test execution based on purpose, risk, and coverage scope. Groups are implemented directly in the Java test methods using `@Test(groups = {...})`, and they are consumed by separate TestNG XML suite files such as `testng-smoke.xml`, `testng-sanity.xml`, and `testng-regression.xml`.

The main impact is that we can run only the required set of tests depending on the situation. For example, smoke tests can be run for quick build stability, sanity tests for focused validation, regression tests for full coverage, and RBAC tests for role-based access checks. This avoids duplicate scripts, improves execution speed, and makes the framework more flexible and maintainable.

## Final Summary

Groups were used because the Cafetron framework needed controlled, reusable, and purpose-based test execution.

They were implemented in:

- Java test classes through `@Test(groups = {...})`
- TestNG XML files through `<include name="groupName"/>`
- Maven execution through `-DsuiteXmlFile=...`

Their impact was faster execution, better coverage control, reusable tests, easier maintenance, and stronger confidence in smoke, sanity, regression, RBAC, UAT, integration, defect, and usability testing.
