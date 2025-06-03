import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;

public class Test {
    
    public static class TestResult {
        public final boolean passed;
        public final String actualOutput;
        public final long executionTimeMs;
        public final long memoryUsedBytes;
        public final String errorMessage;
        
        public TestResult(boolean passed, String actualOutput, long executionTimeMs, long memoryUsedBytes, String errorMessage) {
            this.passed = passed;
            this.actualOutput = actualOutput != null ? actualOutput : "";
            this.executionTimeMs = executionTimeMs;
            this.memoryUsedBytes = memoryUsedBytes;
            this.errorMessage = errorMessage;
        }

        public boolean passesConstraints(long maxExecutionTimeMs, long maxMemoryBytes) {
            return passed && 
                   errorMessage == null && 
                   this.executionTimeMs <= maxExecutionTimeMs && 
                   this.memoryUsedBytes <= maxMemoryBytes;
        }
        
        @Override
        public String toString() {
            return String.format("Test %s | Time: %dms | Memory: %d bytes | Output: '%s'%s",
                passed ? "PASSED" : "FAILED",
                executionTimeMs,
                memoryUsedBytes,
                actualOutput.replace("\n", "\\n").replace("\r", "\\r"),
                errorMessage != null ? " | Error: " + errorMessage : "");
        }
    }
    
    public static TestResult testFunction(Runnable func, String input, String expectedOutput) {
        // Auto-detect if input is a file path
        if (input.endsWith(".txt")) {
            return testFunctionWithFileInput(func, input, expectedOutput);
        }
        
        return testFunctionCore(func, input, expectedOutput);
    }
      private static TestResult testFunctionCore(Runnable func, String input, String expectedOutput) {
        if (func == null) {
            return new TestResult(false, "", 0, 0, "Function is null");
        }
        
        Runtime runtime = Runtime.getRuntime();
        
        // Multiple GC calls for more accurate memory measurement
        for (int i = 0; i < 3; i++) {
            runtime.gc();
            try {
                Thread.sleep(10); // Allow GC to complete
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        
        long memoryBefore = runtime.totalMemory() - runtime.freeMemory();
        
        // Create test streams
        ByteArrayInputStream testIn = new ByteArrayInputStream(
            input.getBytes(StandardCharsets.UTF_8));
        ByteArrayOutputStream testOut = new ByteArrayOutputStream();
        ByteArrayOutputStream testErr = new ByteArrayOutputStream();
        
        // Store original streams
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        PrintStream originalErr = System.err;
        
        // Set test streams
        System.setIn(testIn);
        System.setOut(new PrintStream(testOut, true, StandardCharsets.UTF_8));
        System.setErr(new PrintStream(testErr, true, StandardCharsets.UTF_8));
        
        long startTime = System.nanoTime();
        String errorMessage = null;
        
        try {
            func.run();
        } catch (Exception e) {
            errorMessage = e.getClass().getSimpleName() + ": " + 
                          (e.getMessage() != null ? e.getMessage() : "No message");
        } catch (Error e) {
            errorMessage = "Error: " + e.getClass().getSimpleName() + ": " + 
                          (e.getMessage() != null ? e.getMessage() : "No message");
        } finally {
            // Always restore original streams
            System.setIn(originalIn);
            System.setOut(originalOut);
            System.setErr(originalErr);
        }
        
        long endTime = System.nanoTime();
        long executionTime = (endTime - startTime) / 1_000_000; // Convert to milliseconds
        
        // Memory measurement with multiple samples
        for (int i = 0; i < 3; i++) {
            runtime.gc();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        
        long memoryAfter = runtime.totalMemory() - runtime.freeMemory();
        long memoryUsed = Math.max(0, memoryAfter - memoryBefore);
        
        String actualOutput = testOut.toString(StandardCharsets.UTF_8).trim();
        String expectedTrimmed = expectedOutput != null ? expectedOutput.trim() : "";
        boolean passed = actualOutput.equals(expectedTrimmed) && errorMessage == null;
        
        return new TestResult(passed, actualOutput, executionTime, memoryUsed, errorMessage);
    }
      public static TestResult testFunctionWithFileInput(Runnable func, String inputFilePath, String expectedOutput) {
        if (inputFilePath == null || inputFilePath.trim().isEmpty()) {
            return new TestResult(false, "", 0, 0, "Input file path is null or empty");
        }
        
        try {
            String input = Files.readString(Paths.get(inputFilePath), StandardCharsets.UTF_8);
            
            // Check if expected output is also a file path
            if (expectedOutput != null && expectedOutput.endsWith(".txt")) {
                try {
                    String expectedFromFile = Files.readString(Paths.get(expectedOutput), StandardCharsets.UTF_8);
                    return testFunctionCore(func, input, expectedFromFile);
                } catch (IOException e) {
                    return new TestResult(false, "", 0, 0, "Failed to read expected output file: " + e.getMessage());
                }
            } else {
                return testFunctionCore(func, input, expectedOutput);
            }
        } catch (IOException e) {
            return new TestResult(false, "", 0, 0, "Failed to read input file: " + e.getMessage());
        }
    }
    
    public static TestResult testFunctionWithFileInputAndOutput(Runnable func, String inputFilePath, String expectedOutputFilePath) {
        if (inputFilePath == null || inputFilePath.trim().isEmpty()) {
            return new TestResult(false, "", 0, 0, "Input file path is null or empty");
        }
        if (expectedOutputFilePath == null || expectedOutputFilePath.trim().isEmpty()) {
            return new TestResult(false, "", 0, 0, "Expected output file path is null or empty");
        }
        
        try {
            String input = Files.readString(Paths.get(inputFilePath), StandardCharsets.UTF_8);
            String expectedOutput = Files.readString(Paths.get(expectedOutputFilePath), StandardCharsets.UTF_8);
            return testFunctionCore(func, input, expectedOutput);
        } catch (IOException e) {
            return new TestResult(false, "", 0, 0, "Failed to read files: " + e.getMessage());
        }
    }
    
    public static String getActualOutput(Runnable func, String input) {
        TestResult result = testFunction(func, input, "");
        return result.actualOutput;
    }
}
