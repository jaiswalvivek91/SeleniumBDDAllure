# Selenium Cucumber Java Framework with Allure Reporting

This README explains the complete setup for the Selenium Cucumber Java automation framework using Maven and Allure reports, suitable for **Windows** and **Mac** environments, and how to run it locally or in GitLab CI.

---

## Table of Contents

- [Prerequisites](#prerequisites)
- [Project Structure](#project-structure)
- [1. Install Java JDK](#1-install-java-jdk)
- [2. Install Maven](#2-install-maven)
- [3. IntelliJ IDEA Setup](#3-intellij-idea-setup)
- [4. Clone Project & Import](#4-clone-project--import)
- [5. Running Tests Locally](#5-running-tests-locally)
- [6. Generating Allure Reports Locally](#6-generating-allure-reports-locally)
- [7. GitLab CI Setup](#7-gitlab-ci-setup)
- [8. Troubleshooting](#8-troubleshooting)

---

## Prerequisites

- Java 11 or higher (JDK)
- Maven 3.6 or higher
- IntelliJ IDEA (Community or Ultimate)
- Git
- (Optional) Allure Commandline for local report generation

---

## Project Structure

```
final_automation_framework_java/
├── .gitlab-ci.yml
├── README.md
├── pom.xml
├── allure-results/
├── files/
│   └── sample_upload.pdf
├── reports/
├── src/
│   └── test/
│       ├── java/
│       │   └── your/base/package/
│       │       ├── hooks/
│       │       │   ├── Hooks.java
│       │       │   └── ScreenshotHook.java
│       │       ├── pages/
│       │       │   └── LoginPage.java
│       │       ├── runner/
│       │       │   └── RunnerTest.java
│       │       ├── steps/
│       │       │   └── LoginSteps.java
│       │       └── utility/
│       │           ├── ConfigReader.java
│       │           ├── DriverFactory.java
│       │           ├── ScreenshotUtil.java
│       │           └── SeleniumHelper.java
│       └── resources/
│           ├── config/
│           │   ├── application_config.json
│           │   ├── browser_config.json
│           │   └── user_credentials.json
│           ├── drivers/
│           └── features/
│               ├── Flight.feature
│               └── login.feature
└── .gitignore
├── README.md
├── .gitlab-ci.yml
```

---

## 1. Install Java JDK

### Windows

1. Download and install JDK from [Adoptium](https://adoptium.net/).
2. Set `JAVA_HOME` environment variable.
3. Add `%JAVA_HOME%\bin` to `Path`.
4. Verify:

```bash
java -version
```

### Mac

```bash
brew install openjdk@17
echo 'export PATH="/usr/local/opt/openjdk@17/bin:$PATH"' >> ~/.zshrc
source ~/.zshrc
java -version
```

---

## 2. Install Maven

### Windows

1. Download from [Maven Official](https://maven.apache.org/download.cgi).
2. Extract and set `MAVEN_HOME`.
3. Add `%MAVEN_HOME%\bin` to `Path`.
4. Verify:

```bash
mvn -version
```

### Mac

```bash
brew install maven
mvn -version
```

---

## 3. IntelliJ IDEA Setup

1. Install IntelliJ IDEA from [JetBrains](https://www.jetbrains.com/idea/).
2. Go to **File > Settings > Plugins** and install:
   - Maven
   - Gherkin
   - Cucumber for Java
   - TestNG
   - Allure
3. Restart IntelliJ after installation.

---

## 4. Clone Project & Import

```bash
git clone <your-repo-url>
cd <project-folder>
```

- Open IntelliJ → **Open Project** → select project folder.
- IntelliJ will auto-import Maven dependencies.

---

## 5. Running Tests Locally

```bash
mvn clean test
```

---

## 6. Generating Allure Reports Locally

### Install Allure CLI

#### Windows

Download from [Allure GitHub](https://github.com/allure-framework/allure2/releases) → extract → add `bin` to system `Path`.

#### Mac

```bash
brew install allure
```

### Generate Report

```bash
allure serve allure-results
```

Or, open `allure-results` using IntelliJ Allure Plugin.

---

## 7. GitLab CI Setup

```yaml
stages:
  - test
  - report

test:
  stage: test
  image: maven:3.9.3-openjdk-17
  script:
    - rm -rf allure-results
    - mvn clean test
  artifacts:
    paths:
      - allure-results
    expire_in: 1 week

generate_report:
  stage: report
  image:
    name: "allureframework/allure:2.21.0"
    entrypoint: [""]
  dependencies:
    - test
  script:
    - export TS=$(date +"%Y%m%d%H%M%S")
    - REPORT_DIR="allure-report-$TS"
    - allure generate allure-results --clean -o $REPORT_DIR
  artifacts:
    paths:
      - allure-report-*
    expire_in: 1 week
```

---

## 8. Troubleshooting

| Issue | Possible Cause | Solution |
|------|----------------|----------|
| `java -version` not recognized | JAVA_HOME or Path not set | Set JAVA_HOME and update Path |
| `mvn` not recognized | Maven not installed or Path not set | Install Maven and set Path |
| Allure command fails | CLI not installed or misconfigured | Install Allure CLI and set Path |
| IntelliJ doesn’t detect plugins | Plugin not installed | Install from Plugins section |
| Tests not running | Feature files or step definitions missing | Verify feature and step mapping |

---

✅ **Happy Testing!** 🚀  
For issues, contact Vivek Jaiswal.