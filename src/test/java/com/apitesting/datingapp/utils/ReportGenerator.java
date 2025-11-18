package com.apitesting.datingapp.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ReportGenerator {

    public static class TestResult {
        private String testId;
        private String testName;
        private String status; // "PASS", "FAIL", "SKIPPED"
        private String duration;
        private String errorMessage;
        private String bugId;

        public TestResult(String testId, String testName, String status, String duration) {
            this.testId = testId;
            this.testName = testName;
            this.status = status;
            this.duration = duration;
        }

        // Getters and setters
        public String getTestId() { return testId; }
        public void setTestId(String testId) { this.testId = testId; }

        public String getTestName() { return testName; }
        public void setTestName(String testName) { this.testName = testName; }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }

        public String getDuration() { return duration; }
        public void setDuration(String duration) { this.duration = duration; }

        public String getErrorMessage() { return errorMessage; }
        public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }

        public String getBugId() { return bugId; }
        public void setBugId(String bugId) { this.bugId = bugId; }
    }

    public static class TestSummary {
        private int totalTests;
        private int passedTests;
        private int failedTests;
        private int skippedTests;
        private String executionTime;
        private LocalDateTime executionDate;

        public TestSummary(int totalTests, int passedTests, int failedTests, int skippedTests,
                           String executionTime) {
            this.totalTests = totalTests;
            this.passedTests = passedTests;
            this.failedTests = failedTests;
            this.skippedTests = skippedTests;
            this.executionTime = executionTime;
            this.executionDate = LocalDateTime.now();
        }

        // Getters
        public int getTotalTests() { return totalTests; }
        public int getPassedTests() { return passedTests; }
        public int getFailedTests() { return failedTests; }
        public int getSkippedTests() { return skippedTests; }
        public String getExecutionTime() { return executionTime; }
        public LocalDateTime getExecutionDate() { return executionDate; }

        public double getSuccessRate() {
            return totalTests > 0 ? (double) passedTests / totalTests * 100 : 0;
        }
    }

    public static void generateHtmlReport(List<TestResult> testResults, TestSummary summary) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        String filename = "test-report_" + timestamp + ".html";

        String htmlContent = generateHtmlContent(testResults, summary, timestamp);

        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(htmlContent);
            System.out.println("HTML report generated: " + filename);
        } catch (IOException e) {
            System.err.println("Failed to generate HTML report: " + e.getMessage());
        }
    }

    private static String generateHtmlContent(List<TestResult> testResults, TestSummary summary, String timestamp) {
        StringBuilder html = new StringBuilder();

        // Basic HTML structure
        html.append("<!DOCTYPE html>")
                .append("<html lang='en'>")
                .append("<head>")
                .append("<meta charset='UTF-8'>")
                .append("<meta name='viewport' content='width=device-width, initial-scale=1.0'>")
                .append("<title>Dating App API Test Report</title>")
                .append("<style>")
                .append("body { font-family: Arial, sans-serif; margin: 20px; background-color: #f8f9fa; }")
                .append(".container { max-width: 1200px; margin: 0 auto; background: white; border-radius: 10px; box-shadow: 0 0 20px rgba(0,0,0,0.1); overflow: hidden; }")
                .append(".header { background: #667eea; color: white; padding: 30px; text-align: center; }")
                .append(".header h1 { font-size: 2.5em; margin-bottom: 10px; }")
                .append(".summary-cards { display: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); gap: 20px; padding: 20px; background: #f8f9fa; }")
                .append(".summary-card { background: white; padding: 20px; border-radius: 8px; text-align: center; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }")
                .append(".summary-card.total { border-top: 4px solid #007bff; }")
                .append(".summary-card.passed { border-top: 4px solid #28a745; }")
                .append(".summary-card.failed { border-top: 4px solid #dc3545; }")
                .append(".summary-card.skipped { border-top: 4px solid #ffc107; }")
                .append(".summary-card h3 { font-size: 2em; margin-bottom: 5px; }")
                .append(".summary-card.total h3 { color: #007bff; }")
                .append(".summary-card.passed h3 { color: #28a745; }")
                .append(".summary-card.failed h3 { color: #dc3545; }")
                .append(".summary-card.skipped h3 { color: #ffc107; }")
                .append(".success-rate { background: #e8f5e8; padding: 15px; margin: 20px; border-radius: 8px; text-align: center; font-size: 1.2em; font-weight: bold; color: #155724; }")
                .append(".section { padding: 20px; border-bottom: 1px solid #eee; }")
                .append(".section h2 { color: #495057; margin-bottom: 15px; padding-bottom: 10px; border-bottom: 2px solid #e9ecef; }")
                .append("table { width: 100%; border-collapse: collapse; margin: 15px 0; }")
                .append("th { background: #f8f9fa; padding: 12px; text-align: left; font-weight: 600; border-bottom: 2px solid #dee2e6; }")
                .append("td { padding: 12px; border-bottom: 1px solid #dee2e6; }")
                .append(".test-row.pass { background: #f8fff9; }")
                .append(".test-row.fail { background: #fff5f5; }")
                .append(".test-row.skipped { background: #fffef0; }")
                .append(".status { font-weight: bold; padding: 4px 8px; border-radius: 4px; text-align: center; }")
                .append(".status.pass { background: #d4edda; color: #155724; }")
                .append(".status.fail { background: #f8d7da; color: #721c24; }")
                .append(".status.skipped { background: #fff3cd; color: #856404; }")
                .append(".bug-id { background: #ffeaa7; color: #856404; padding: 2px 6px; border-radius: 3px; font-size: 0.85em; font-weight: bold; }")
                .append(".error-message { background: #f8f9fa; padding: 8px; border-radius: 4px; font-family: monospace; font-size: 0.9em; color: #dc3545; }")
                .append(".bugs-table { width: 100%; border-collapse: collapse; margin: 15px 0; }")
                .append(".bug-severity.critical { background: #dc3545; color: white; }")
                .append(".bug-severity.major { background: #fd7e14; color: white; }")
                .append(".bug-severity.minor { background: #ffc107; color: #212529; }")
                .append(".bug-severity { padding: 4px 8px; border-radius: 4px; font-weight: bold; text-align: center; }")
                .append(".execution-info { background: #e7f3ff; padding: 15px; margin: 20px; border-radius: 8px; text-align: center; }")
                .append(".footer { text-align: center; padding: 20px; background: #f8f9fa; color: #6c757d; font-size: 0.9em; }")
                .append("</style>")
                .append("</head>")
                .append("<body>")
                .append("<div class='container'>")
                .append("<div class='header'>")
                .append("<h1>📊 Dating App API Test Report</h1>")
                .append("<p>Comprehensive API Testing Results</p>")
                .append("<p>Generated on: ").append(timestamp).append("</p>")
                .append("</div>")
                .append("<div class='summary-cards'>")
                .append("<div class='summary-card total'><h3>").append(summary.getTotalTests()).append("</h3><p>Total Tests</p></div>")
                .append("<div class='summary-card passed'><h3>").append(summary.getPassedTests()).append("</h3><p>Passed</p></div>")
                .append("<div class='summary-card failed'><h3>").append(summary.getFailedTests()).append("</h3><p>Failed</p></div>")
                .append("<div class='summary-card skipped'><h3>").append(summary.getSkippedTests()).append("</h3><p>Skipped</p></div>")
                .append("</div>")
                .append("<div class='success-rate'>")
                .append("🎯 Success Rate: ").append(String.format("%.2f", summary.getSuccessRate())).append("%")
                .append("</div>")
                .append("<div class='execution-info'>")
                .append("<strong>Execution Time:</strong> ").append(summary.getExecutionTime()).append(" | ")
                .append("<strong>Environment:</strong> https://hr-challenge.dev.tapyou.com/api/test")
                .append("</div>")
                .append("<div class='section'>")
                .append("<h2>📋 Test Results Details</h2>")
                .append("<table class='results-table'>")
                .append("<thead><tr>")
                .append("<th>Test ID</th>")
                .append("<th>Test Name</th>")
                .append("<th>Status</th>")
                .append("<th>Duration</th>")
                .append("<th>Bug ID</th>")
                .append("<th>Error Message</th>")
                .append("</tr></thead>")
                .append("<tbody>");

        // Test results rows
        for (TestResult result : testResults) {
            String statusClass = result.getStatus().toLowerCase();
            String bugCell = result.getBugId() != null ?
                    "<span class='bug-id'>" + result.getBugId() + "</span>" : "";
            String errorCell = result.getErrorMessage() != null ?
                    "<div class='error-message'>" + result.getErrorMessage() + "</div>" : "";

            html.append("<tr class='test-row ").append(statusClass).append("'>")
                    .append("<td class='test-id'>").append(result.getTestId()).append("</td>")
                    .append("<td class='test-name'>").append(result.getTestName()).append("</td>")
                    .append("<td class='status ").append(statusClass).append("'>").append(result.getStatus()).append("</td>")
                    .append("<td class='duration'>").append(result.getDuration()).append("</td>")
                    .append("<td class='bug-id'>").append(bugCell).append("</td>")
                    .append("<td class='error'>").append(errorCell).append("</td>")
                    .append("</tr>");
        }

        html.append("</tbody></table></div>")
                .append("<div class='section'>")
                .append("<h2>🐛 Known Issues & Bug Reports</h2>")
                .append("<table class='bugs-table'>")
                .append("<thead><tr>")
                .append("<th>Bug ID</th>")
                .append("<th>Title</th>")
                .append("<th>Severity</th>")
                .append("<th>Status</th>")
                .append("</tr></thead>")
                .append("<tbody>");

        // Known bugs rows
        List<String> knownBugs = getKnownBugs();
        for (String bug : knownBugs) {
            String[] bugInfo = bug.split("\\|");
            if (bugInfo.length >= 4) {
                html.append("<tr class='bug-row'>")
                        .append("<td class='bug-id'>").append(bugInfo[0]).append("</td>")
                        .append("<td class='bug-title'>").append(bugInfo[1]).append("</td>")
                        .append("<td class='bug-severity ").append(bugInfo[2].toLowerCase()).append("'>").append(bugInfo[2]).append("</td>")
                        .append("<td class='bug-status'>").append(bugInfo[3]).append("</td>")
                        .append("</tr>");
            }
        }

        html.append("</tbody></table></div>")
                .append("<div class='section'>")
                .append("<h2>🔍 Test Summary</h2>")
                .append("<div style='padding: 15px; background: #f8f9fa; border-radius: 5px;'>")
                .append("<p><strong>Test Scope:</strong> Comprehensive API testing including Positive, Negative, Data Integrity, and Performance tests</p>")
                .append("<p><strong>Coverage:</strong> All endpoints (/users, /user/{id}) with various scenarios</p>")
                .append("<p><strong>Key Findings:</strong> ").append(countCriticalBugs()).append(" critical issues identified requiring immediate attention</p>")
                .append("</div></div>")
                .append("<div class='footer'>")
                .append("<p>Generated by Dating App API Test Automation Framework</p>")
                .append("<p>For detailed bug information, refer to individual bug reports (BUG-001 to BUG-027)</p>")
                .append("</div></div>")
                .append("<script>")
                .append("document.addEventListener('DOMContentLoaded', function() {")
                .append("const errorMessages = document.querySelectorAll('.error-message');")
                .append("errorMessages.forEach(msg => {")
                .append("msg.addEventListener('click', function() {")
                .append("this.style.whiteSpace = this.style.whiteSpace === 'normal' ? 'nowrap' : 'normal';")
                .append("this.style.overflow = this.style.overflow === 'visible' ? 'hidden' : 'visible';")
                .append("});});")
                .append("const statusCells = document.querySelectorAll('.status');")
                .append("statusCells.forEach(cell => {")
                .append("cell.addEventListener('click', function() {")
                .append("const status = this.textContent.toLowerCase();")
                .append("const rows = document.querySelectorAll('.test-row');")
                .append("rows.forEach(row => {")
                .append("if (status === 'all' || row.classList.contains(status)) {")
                .append("row.style.display = '';")
                .append("} else {")
                .append("row.style.display = 'none';")
                .append("}});})});")
                .append("</script>")
                .append("</body></html>");

        return html.toString();
    }

    private static List<String> getKnownBugs() {
        List<String> bugs = new ArrayList<>();
        bugs.add("BUG-001|API returns 500 for gender=invalid|Critical|Open");
        bugs.add("BUG-002|Gender inconsistency between endpoints|Major|Open");
        bugs.add("BUG-003|Invalid user data for ID=911|Critical|Open");
        bugs.add("BUG-004|ID mismatch in user response|Critical|Open");
        bugs.add("BUG-005|Documentation format inconsistency|Minor|Open");
        bugs.add("BUG-006|500 error for ID=0|Major|Open");
        bugs.add("BUG-007|Incorrect handling of negative ID|Major|Open");
        bugs.add("BUG-008|Wrong user returned for large ID|Major|Open");
        bugs.add("BUG-009|Missing user with ID=1|Major|Open");
        bugs.add("BUG-010|Case sensitivity issue with gender|Major|Open");
        bugs.add("BUG-011|Invalid date format|Minor|Open");
        bugs.add("BUG-012|Age and registration date inconsistency|Major|Open");
        bugs.add("BUG-013|Invalid city names|Minor|Open");
        bugs.add("BUG-014|Unrealistic age values|Minor|Open");
        bugs.add("BUG-015|Documentation vs implementation mismatch|Major|Open");
        bugs.add("BUG-016|JSON structure inconsistency|Major|Open");
        bugs.add("BUG-017|Invalid pagination parameters accepted|Major|Open");
        bugs.add("BUG-018|No pagination limits|Minor|Open");
        bugs.add("BUG-019|Non-existing IDs returned|Major|Open");
        bugs.add("BUG-020|500 error for gender=magic|Major|Open");
        bugs.add("BUG-021|500 error for gender=McCloud|Major|Open");
        bugs.add("BUG-022|500 error for numeric gender|Major|Open");
        bugs.add("BUG-023|Incorrect gender filtering|Major|Open");
        bugs.add("BUG-024|User substitution bug|Critical|Open");
        bugs.add("BUG-025|Performance issues under load|Major|Open");
        bugs.add("BUG-026|Missing security headers|Major|Open");
        bugs.add("BUG-027|Invalid Content-Type accepted|Major|Open");
        return bugs;
    }

    private static int countCriticalBugs() {
        return (int) getKnownBugs().stream()
                .filter(bug -> bug.contains("|Critical|"))
                .count();
    }

    // Utility method to create sample data for demonstration
    public static void generateSampleReport() {
        List<TestResult> results = new ArrayList<>();
        results.add(new TestResult("PT-001", "Get male users IDs", "PASS", "450ms"));
        results.add(new TestResult("PT-002", "Get female users IDs", "PASS", "420ms"));
        results.add(new TestResult("PT-003", "Get all users with gender=any", "FAIL", "380ms"));
        results.get(2).setBugId("BUG-019");
        results.get(2).setErrorMessage("Returns non-existing IDs 0 and 212");

        results.add(new TestResult("PT-004A", "Get user data by valid ID", "PASS", "320ms"));
        results.add(new TestResult("PT-004B", "Get user data by other ID", "FAIL", "310ms"));
        results.get(4).setBugId("BUG-003");
        results.get(4).setErrorMessage("Invalid data: age=911, gender=McCloud");

        results.add(new TestResult("NT-001", "Test gender=magic", "FAIL", "280ms"));
        results.get(5).setBugId("BUG-020");
        results.get(5).setErrorMessage("500 Internal Server Error");

        results.add(new TestResult("NT-010", "Test user ID=0", "FAIL", "260ms"));
        results.get(6).setBugId("BUG-006");
        results.get(6).setErrorMessage("500 Internal Server Error");

        TestSummary summary = new TestSummary(25, 15, 8, 2, "45 seconds");
        generateHtmlReport(results, summary);
    }

    public static void main(String[] args) {
        // Generate a sample report when running directly
        generateSampleReport();
    }
}