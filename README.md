# API Automation Assessment: Online Bookstore

## 1. Project Overview
This repository contains a professional-grade automated testing framework for the FakeRestAPI (Books & Authors).
The framework is designed for high maintainability, clear reporting, and seamless CI/CD integration, 
following SOLID principles and clean code practices.

## 2. Technical Stack & Architecture Decisions
**Language:** Java 17 (Chosen for its robust ecosystem and native support for modern features).
**Test Framework:** JUnit 5 & RestAssured (Fluent DSL allows for readable, "living documentation" tests).
**Reporting:** Allure Report (Integrated with GitHub Pages for executive visibility).
**CI/CD:** GitHub Actions (DevSecOps approach with security and quality gates).
**Code Quality:** Checkstyle (Google Style Guide).
**Design Patterns:**  Singleton: For centralized Property/Config management.
     Interface-driven: CrudOperations interface ensures consistency across different API clients.
     Builder Pattern: For flexible DTO construction and readable test data. 
     Data-Driven Testing: Using @ParameterizedTest to maximize coverage of edge cases with minimal code.

## 3. Testing Strategy & Edge Cases
**Data Isolation:** Every test generates unique dynamic data using ThreadLocalRandom or UUID to ensure test independence and support parallel execution.
**Negative Scenarios:** Extensive coverage of boundary values (e.g., 5000 char titles), invalid date formats, and non-existent IDs.
**Security & Stability:** Basic SQL Injection probes and payload size limit checks.
**Smart Assertions:** Use of SoftAssertions for detailed validation and usingRecursiveComparison for deep object matching.

## 4. CI/CD & Quality Gates
The project implements a professional CI/CD pipeline in GitHub Actions:
1. **Static Analysis:** Checkstyle validates code style before execution.
2. **Automated Execution:** Runs the full API suite on every Push/PR
3. **Artifacts:** Allure results are processed and hosted automatically.
4. **Visibility:** Live report is available at: https://iuliadorogokuplia.github.io/online-bookstore/

## 5. How to Run Locally
### Prerequisites:
* JDK 17
* Maven 3.8+
* Allure CLI (for local report generation)

### Commands:
```bash
# Run all tests
mvn clean test

# Run specifically Happy Path tests
mvn test -Dgroups="happy_path"

# Generate and open Allure Report locally
allure serve target/allure-results

## 5. Project Structure
.
├── allure-results/           # Raw data for Allure reports (generated after tests)
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── clients/      # API clients implementing RestAssured logic
│   │   │       ├── AuthorsClient.java
│   │   │       ├── BaseClient.java
│   │   │       ├── BooksClient.java
│   │   │       └── CrudOperations.java    # Interface for generic CRUD actions
│   │   │   └── models/       # POJO/DTO classes (Lombok @Data, @Builder)
│   │   │       ├── AuthorDto.java
│   │   │       └── BookDto.java
│   │   │   └── utils/        # Utility classes and Config readers
│   │   │       └── ConfigReader.java      # Singleton for property management
│   │   └── resources/
│   │       └── config.properties          # Environment configurations
│   └── test/
│       ├── java/
│       │   └── tests/        # Test suites organized by functionality
│       │       ├── AuthorsHappyPathTests.java
│       │       ├── AuthorsNegativeTests.java
│       │       ├── BaseTest.java          # Global setup/teardown
│       │       ├── BooksHappyPathTests.java
│       │       └── BooksNegativeTests.java
├── target/                   # Maven build artifacts and compiled classes
├── .gitignore                # Rules for excluding files from Git
├── pom.xml                   # Project Object Model (Dependencies & Plugins)
└── README.md                 # Project documentation

## 6. Known Environment Limitations
**Persistence:** Since FakeRestAPI is a mock service, it does not persist data. Some "State-dependent" tests (like 404 after Delete) are designed based on production logic, even if the mock server returns 200 OK.
**Validation:** The demo API lacks strict server-side schema validation; therefore, some negative tests are expected to highlight these gaps
