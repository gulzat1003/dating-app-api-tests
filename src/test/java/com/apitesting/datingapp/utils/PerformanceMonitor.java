package com.apitesting.datingapp.utils;

public class PerformanceMonitor {

    public static class MeasurementResult {
        private final long averageTime;
        private final long maxTime;
        private final long minTime;
        private final int iterations;

        public MeasurementResult(long averageTime, long maxTime, long minTime, int iterations) {
            this.averageTime = averageTime;
            this.maxTime = maxTime;
            this.minTime = minTime;
            this.iterations = iterations;
        }

        public long getAverageTime() { return averageTime; }
        public long getMaxTime() { return maxTime; }
        public long getMinTime() { return minTime; }
        public int getIterations() { return iterations; }
    }

    public static MeasurementResult measure(Runnable operation, int iterations) {
        long totalTime = 0;
        long maxTime = Long.MIN_VALUE;
        long minTime = Long.MAX_VALUE;

        for (int i = 0; i < iterations; i++) {
            long startTime = System.currentTimeMillis();
            operation.run();
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;

            totalTime += duration;
            maxTime = Math.max(maxTime, duration);
            minTime = Math.min(minTime, duration);

            // Small delay between iterations
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        long averageTime = totalTime / iterations;
        return new MeasurementResult(averageTime, maxTime, minTime, iterations);
    }
}
