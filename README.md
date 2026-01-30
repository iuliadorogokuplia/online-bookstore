# API Automation Assessment: Online Bookstore

## 1. Project Overview
This repository contains a professional-grade automated testing framework 
for the FakeRestAPI (Books & Authors). 
The framework is designed for high maintainability, clear reporting, and seamless CI/CD integration

## 2. Technical Stack & Patterns
* **Language:** Java 17
* **Test Framework:** JUnit 5 & RestAssured
* **Reporting:** Allure Report (Integrated with GitHub Pages)
* **CI/CD:** GitHub Actions
* **Code Quality:** Checkstyle (Google Style Guide)
* **Design Patterns:** Singleton (for Config), Factory (for RequestSpecs), Builder pattern for Data Models and Data-Driven Testing (Using @ParameterizedTest for extensive negative scenario coverage)

## 3. CI/CD & Quality Gates
The project implements a professional CI/CD pipeline in GitHub Actions:
1. **Static Analysis:** Checkstyle validates code style before execution.
2. **Automated Execution:** Runs the full API suite on every Push/PR
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