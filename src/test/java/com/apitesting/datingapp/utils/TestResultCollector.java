package com.apitesting.datingapp.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class TestResultCollector {
    private static final ConcurrentHashMap<String, ReportGenerator.TestResult> results =
            new ConcurrentHashMap<>();
    private static final AtomicInteger passed = new AtomicInteger(0);
    private static final AtomicInteger failed = new AtomicInteger(0);
    private static final AtomicInteger skipped = new AtomicInteger(0);
    private static long startTime;

    public static void startTestRun() {
        startTime = System.currentTimeMillis();
        results.clear();
        passed.set(0);
        failed.set(0);
        skipped.set(0);
    }

    public static void recordTestResult(String testId, String testName, String status,
                                        long durationMs, String errorMessage, String bugId) {
        String duration = durationMs + "ms";
        ReportGenerator.TestResult result = new ReportGenerator.TestResult(testId, testName, status, duration);
        result.setErrorMessage(errorMessage);
        result.setBugId(bugId);
        results.put(testId, result);

        switch (status.toUpperCase()) {
            case "PASS" -> passed.incrementAndGet();
            case "FAIL" -> failed.incrementAndGet();
            case "SKIPPED" -> skipped.incrementAndGet();
        }

        System.out.printf("Test %s: %s (%s)%n", testId, status, duration);
    }

    public static ReportGenerator.TestSummary generateSummary() {
        long executionTime = System.currentTimeMillis() - startTime;
        String formattedTime = formatDuration(executionTime);
        int total = passed.get() + failed.get() + skipped.get();

        return new ReportGenerator.TestSummary(total, passed.get(), failed.get(), skipped.get(), formattedTime);
    }

    private static String formatDuration(long milliseconds) {
        long seconds = milliseconds / 1000;
        long minutes = seconds / 60;
        seconds = seconds % 60;
        return String.format("%d min %d sec", minutes, seconds);
    }

    public static void generateFinalReport() {
        ReportGenerator.TestSummary summary = generateSummary();
        List<ReportGenerator.TestResult> allResults = new ArrayList<>(results.values());
        ReportGenerator.generateHtmlReport(allResults, summary);

        // Print summary to console
        System.out.println("\n=== TEST EXECUTION SUMMARY ===");
        System.out.printf("Total: %d | Passed: %d | Failed: %d | Skipped: %d%n",
                summary.getTotalTests(), summary.getPassedTests(),
                summary.getFailedTests(), summary.getSkippedTests());
        System.out.printf("Success Rate: %.2f%% | Execution Time: %s%n",
                summary.getSuccessRate(), summary.getExecutionTime());
    }

    public static int getPassedCount() { return passed.get(); }
    public static int getFailedCount() { return failed.get(); }
    public static int getSkippedCount() { return skipped.get(); }
    public static int getTotalCount() { return passed.get() + failed.get() + skipped.get(); }
}
