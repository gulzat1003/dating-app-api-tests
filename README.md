# Dating App API Test Automation Framework

![Java](https://img.shields.io/badge/Java-21-blue)
![RestAssured](https://img.shields.io/badge/RestAssured-5.3.0-green)
![JUnit](https://img.shields.io/badge/JUnit-5.9.2-red)
![Maven](https://img.shields.io/badge/Maven-3.6%2B-orange)

A comprehensive API testing framework for the Dating App challenge, featuring automated testing, detailed reporting, and performance monitoring.

## 🚀 Features

- **REST API Testing** - Full coverage of all endpoints
- **Multiple Test Types** - Positive, Negative, Data Integrity, Performance
- **Automated Reporting** - HTML reports with detailed analytics
- **Performance Monitoring** - Response time tracking and load testing
- **Data-Driven Testing** - External test data configuration
- **Bug Tracking Integration** - Automatic bug reporting
- **CI/CD Ready** - Maven integration and batch scripts

## 📋 Test Coverage

| Category | Tests | Description |
|----------|-------|-------------|
| ✅ Positive Tests | 10+ | Valid scenarios and happy paths |
| ❌ Negative Tests | 20+ | Error conditions and invalid inputs |
| 📊 Data Tests | 15+ | Data integrity and consistency |
| ⚡ Performance Tests | 5+ | Response times and load testing |
| 🔒 Security Tests | 3+ | Security headers and validation |

## 🛠️ Prerequisites

- **Java**: 11 or higher
- **Maven**: 3.6 or higher
- **IDE**: IntelliJ IDEA, Eclipse, or VS Code

## 📥 Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd dating-app-api-tests
Build the project

bash
mvn clean install
🧪 Running Tests
Run all tests:

bash
mvn test
Run specific test category:

bash
mvn test -Dtest=PositiveTests
mvn test -Dtest=NegativeTests  
mvn test -Dtest=PerformanceTests
Run with detailed report:

bash
mvn test surefire-report:report
📊 Test Reports
After test execution, view reports in:

Surefire Reports: target/site/surefire-report.html

Allure Reports: target/site/allure-maven/index.html

🏗️ Project Structure
text
dating-app-api-tests/
├── src/test/java/
│   ├── tests/           # Test classes
│   ├── models/          # POJO classes
│   ├── clients/         # API clients
│   ├── utils/           # Utilities
│   └── data/            # Test data
├── src/test/resources/
│   ├── config.properties # Configuration
│   └── test-data/       # JSON test files
├── target/              # Build output
└── pom.xml             # Maven configuration
🔧 Configuration
Edit src/test/resources/config.properties:

properties
base.url=https://api.datingapp.com
admin.username=admin
admin.password=password
test.timeout=5000
🤝 Contributing
Fork the repository

Create a feature branch

Write tests for new functionality

Ensure all tests pass

Submit a pull request

📄 License
This project is licensed under the MIT License.

🆘 Support
For issues and questions:

Create an issue in GitHub

Check existing test documentation

Review surefire reports for failures
