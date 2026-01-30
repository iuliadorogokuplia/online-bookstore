# API Automation Assessment: Online Bookstore

## 1. Project Overview
This project is an automated testing framework for the **FakeRestAPI (Books & Authors)**.
Built with: **Java 17**, **RestAssured**, **TestNG/JUnit**, and **Maven**.

## 2. Technical Stack & Patterns
* **Language:** Java 17
* **Test Framework:** RestAssured (Fluent API)
* **Reporting:** Allure Report (Integrated with GitHub Pages)
* **CI/CD:** GitHub Actions
* **Code Quality:** Checkstyle (Google Style Guide)
* **Design Patterns:** Singleton (for Config), Factory (for RequestSpecs), and Builder pattern for Data Models.

## 3. CI/CD & Quality Gates
The project implements a professional CI/CD pipeline in GitHub Actions:
1. **Static Analysis:** Checkstyle validates code style before execution.
2. **Automated Testing:** Runs the full API suite on every push.
3. **Artifacts:** Allure results are processed and hosted automatically.
4. **Visibility:** Live report is available at: https://iuliadorogokuplia.github.io/online-bookstore/

## 4. How to Run Locally
### Prerequisites:
* JDK 17
* Maven 3.8+

### Commands:
```bash
# Run all tests
mvn clean test

# Generate and open Allure Report locally
mvn allure:serve