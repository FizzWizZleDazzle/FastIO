import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Comprehensive benchmark comparing FastIO (BufferedReader/PrintWriter) 
 * vs FastIONIO (DirectByteBuffer/NIO Channels) performance.
 * 
 * Tests various competitive programming I/O patterns:
 * - Large integer arrays
 * - Multiple test cases
 * - Mixed integer/string input
 * - Large output generation
 */
public class FastIOBenchmark {
    
    // Test data sizes
    private static final int SMALL_ARRAY_SIZE = 1000;
    private static final int MEDIUM_ARRAY_SIZE = 100000;
    private static final int LARGE_ARRAY_SIZE = 1000000;
    private static final int NUM_TEST_CASES = 100;
    
    public static void main(String[] args) {
        System.out.println("FastIO vs FastIONIO Benchmark");
        System.out.println("============================");
        System.out.println();
        
        try {
            // Warm up JVM
            System.out.println("Warming up JVM...");
            warmUp();
            
            // Run benchmarks
            benchmarkIntegerArrayReading();
            benchmarkLongArrayReading();
            benchmarkMultipleTestCases();
            benchmarkMixedInputTypes();
            benchmarkLargeOutput();
            benchmarkMemoryUsage();
            
        } catch (Exception e) {
            System.err.println("Benchmark failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void warmUp() throws IOException {
        // Create small test data for warm up
        createTestData("warmup_input.txt", 1000, 1);
        
        // Run both implementations a few times
        for (int i = 0; i < 5; i++) {
            testFastIOOriginal("warmup_input.txt", "warmup_output1.txt");
            testFastIONIO("warmup_input.txt", "warmup_output2.txt");
        }
        
        // Clean up
        deleteFile("warmup_input.txt");
        deleteFile("warmup_output1.txt");
        deleteFile("warmup_output2.txt");
    }
    
    /**
     * Benchmark reading large integer arrays
     */
    private static void benchmarkIntegerArrayReading() throws IOException {
        System.out.println("Benchmark: Integer Array Reading");
        System.out.println("---------------------------------");
        
        int[] sizes = {SMALL_ARRAY_SIZE, MEDIUM_ARRAY_SIZE, LARGE_ARRAY_SIZE};
        
        for (int size : sizes) {
            System.out.printf("Array size: %,d integers%n", size);
            
            // Create test data
            String inputFile = "int_array_" + size + ".txt";
            createIntArrayTestData(inputFile, size);
            
            // Test original FastIO
            long originalTime = timeOperation(() -> {
                try {
                    testFastIOOriginal(inputFile, "output1.txt");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            
            // Test FastIONIO
            long nioTime = timeOperation(() -> {
                try {
                    testFastIONIO(inputFile, "output2.txt");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            
            // Calculate improvement
            double improvement = (double) originalTime / nioTime;
            
            System.out.printf("  Original FastIO: %,d ms%n", originalTime);
            System.out.printf("  FastIONIO:       %,d ms%n", nioTime);
            System.out.printf("  Speedup:         %.2fx%n", improvement);
            System.out.println();
            
            // Clean up
            deleteFile(inputFile);
            deleteFile("output1.txt");
            deleteFile("output2.txt");
        }
    }
    
    /**
     * Benchmark reading large long arrays
     */
    private static void benchmarkLongArrayReading() throws IOException {
        System.out.println("Benchmark: Long Array Reading");
        System.out.println("-----------------------------");
        
        // Create test data with large numbers
        String inputFile = "long_array.txt";
        createLongArrayTestData(inputFile, MEDIUM_ARRAY_SIZE);
        
        // Test both implementations
        long originalTime = timeOperation(() -> {
            try {
                testFastIOOriginalLongs(inputFile, "output1.txt");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        
        long nioTime = timeOperation(() -> {
            try {
                testFastIONIOLongs(inputFile, "output2.txt");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        
        double improvement = (double) originalTime / nioTime;
        System.out.printf("Original FastIO: %,d ms%n", originalTime);
        System.out.printf("FastIONIO:       %,d ms%n", nioTime);
        System.out.printf("Speedup:         %.2fx%n", improvement);
        System.out.println();
        
        // Clean up
        deleteFile(inputFile);
        deleteFile("output1.txt");
        deleteFile("output2.txt");
    }
    
    /**
     * Benchmark multiple test cases pattern
     */
    private static void benchmarkMultipleTestCases() throws IOException {
        System.out.println("Benchmark: Multiple Test Cases");
        System.out.println("------------------------------");
        
        String inputFile = "multiple_tests.txt";
        createMultipleTestCasesData(inputFile, NUM_TEST_CASES, 1000);
        
        long originalTime = timeOperation(() -> {
            try {
                testFastIOOriginalMultiple(inputFile, "output1.txt");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        
        long nioTime = timeOperation(() -> {
            try {
                testFastIONIOMultiple(inputFile, "output2.txt");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        
        double improvement = (double) originalTime / nioTime;
        System.out.printf("Original FastIO: %,d ms%n", originalTime);
        System.out.printf("FastIONIO:       %,d ms%n", nioTime);
        System.out.printf("Speedup:         %.2fx%n", improvement);
        System.out.println();
        
        deleteFile(inputFile);
        deleteFile("output1.txt");
        deleteFile("output2.txt");
    }
    
    /**
     * Benchmark mixed input types
     */
    private static void benchmarkMixedInputTypes() throws IOException {
        System.out.println("Benchmark: Mixed Input Types");
        System.out.println("----------------------------");
        
        String inputFile = "mixed_input.txt";
        createMixedInputData(inputFile, 10000);
        
        long originalTime = timeOperation(() -> {
            try {
                testFastIOOriginalMixed(inputFile, "output1.txt");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        
        long nioTime = timeOperation(() -> {
            try {
                testFastIONIOMixed(inputFile, "output2.txt");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        
        double improvement = (double) originalTime / nioTime;
        System.out.printf("Original FastIO: %,d ms%n", originalTime);
        System.out.printf("FastIONIO:       %,d ms%n", nioTime);
        System.out.printf("Speedup:         %.2fx%n", improvement);
        System.out.println();
        
        deleteFile(inputFile);
        deleteFile("output1.txt");
        deleteFile("output2.txt");
    }
    
    /**
     * Benchmark large output generation
     */
    private static void benchmarkLargeOutput() throws IOException {
        System.out.println("Benchmark: Large Output Generation");
        System.out.println("----------------------------------");
        
        long originalTime = timeOperation(() -> {
            try {
                testFastIOOriginalOutput("output1.txt", MEDIUM_ARRAY_SIZE);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        
        long nioTime = timeOperation(() -> {
            try {
                testFastIONIOOutput("output2.txt", MEDIUM_ARRAY_SIZE);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        
        double improvement = (double) originalTime / nioTime;
        System.out.printf("Original FastIO: %,d ms%n", originalTime);
        System.out.printf("FastIONIO:       %,d ms%n", nioTime);
        System.out.printf("Speedup:         %.2fx%n", improvement);
        System.out.println();
        
        deleteFile("output1.txt");
        deleteFile("output2.txt");
    }
    
    /**
     * Compare memory usage patterns
     */
    private static void benchmarkMemoryUsage() throws IOException {
        System.out.println("Benchmark: Memory Usage Analysis");
        System.out.println("--------------------------------");
        
        Runtime runtime = Runtime.getRuntime();
        System.gc(); // Force garbage collection
        
        // Measure memory for original FastIO
        long beforeOriginal = runtime.totalMemory() - runtime.freeMemory();
        
        String inputFile = "memory_test.txt";
        createTestData(inputFile, MEDIUM_ARRAY_SIZE, 1);
        
        testFastIOOriginal(inputFile, "output1.txt");
        System.gc();
        long afterOriginal = runtime.totalMemory() - runtime.freeMemory();
        long originalMemory = afterOriginal - beforeOriginal;
        
        // Clean up and measure for FastIONIO
        deleteFile("output1.txt");
        System.gc();
        long beforeNIO = runtime.totalMemory() - runtime.freeMemory();
        
        testFastIONIO(inputFile, "output2.txt");
        System.gc();
        long afterNIO = runtime.totalMemory() - runtime.freeMemory();
        long nioMemory = afterNIO - beforeNIO;
        
        System.out.printf("Original FastIO memory: %,d bytes%n", originalMemory);
        System.out.printf("FastIONIO memory:       %,d bytes%n", nioMemory);
        System.out.printf("Memory difference:      %,d bytes%n", nioMemory - originalMemory);
        System.out.println();
        
        deleteFile(inputFile);
        deleteFile("output2.txt");
    }
    
    // Helper methods for creating test data
    
    private static void createTestData(String filename, int arraySize, int numArrays) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            Random rand = new Random(42); // Fixed seed for reproducible results
            
            for (int array = 0; array < numArrays; array++) {
                pw.println(arraySize);
                for (int i = 0; i < arraySize; i++) {
                    if (i > 0) pw.print(" ");
                    pw.print(rand.nextInt(1000000));
                }
                pw.println();
            }
        }
    }
    
    private static void createIntArrayTestData(String filename, int size) throws IOException {
        createTestData(filename, size, 1);
    }
    
    private static void createLongArrayTestData(String filename, int size) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            Random rand = new Random(42);
            pw.println(size);
            for (int i = 0; i < size; i++) {
                if (i > 0) pw.print(" ");
                pw.print(rand.nextLong() % 1000000000000L);
            }
            pw.println();
        }
    }
    
    private static void createMultipleTestCasesData(String filename, int numCases, int arraySize) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            Random rand = new Random(42);
            pw.println(numCases);
            
            for (int t = 0; t < numCases; t++) {
                pw.println(arraySize);
                for (int i = 0; i < arraySize; i++) {
                    if (i > 0) pw.print(" ");
                    pw.print(rand.nextInt(1000));
                }
                pw.println();
            }
        }
    }
    
    private static void createMixedInputData(String filename, int lines) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            Random rand = new Random(42);
            
            for (int i = 0; i < lines; i++) {
                // Mix of integers, strings, and numbers
                int type = rand.nextInt(3);
                switch (type) {
                    case 0:
                        pw.println(rand.nextInt(1000000));
                        break;
                    case 1:
                        pw.println("string" + rand.nextInt(1000));
                        break;
                    case 2:
                        pw.println(rand.nextDouble() * 1000);
                        break;
                }
            }
        }
    }
    
    // Test implementation methods
    
    private static boolean testFastIOOriginal(String inputFile, String outputFile) throws IOException {
        try (FastIO f = new FastIO(inputFile, outputFile)) {
            int n = f.nextInt();
            int[] arr = f.nextIntArray(n);
            
            // Process array (sum)
            long sum = 0;
            for (int x : arr) {
                sum += x;
            }
            
            f.println(sum);
            return true;
        }
    }
    
    private static boolean testFastIONIO(String inputFile, String outputFile) throws IOException {
        try (FastIONIO f = new FastIONIO(inputFile, outputFile)) {
            int n = f.nextInt();
            int[] arr = f.nextIntArray(n);
            
            // Process array (sum)
            long sum = 0;
            for (int x : arr) {
                sum += x;
            }
            
            f.println(sum);
            return true;
        }
    }
    
    private static boolean testFastIOOriginalLongs(String inputFile, String outputFile) throws IOException {
        try (FastIO f = new FastIO(inputFile, outputFile)) {
            int n = f.nextInt();
            long[] arr = f.nextLongArray(n);
            
            long sum = 0;
            for (long x : arr) {
                sum += x;
            }
            
            f.println(sum);
            return true;
        }
    }
    
    private static boolean testFastIONIOLongs(String inputFile, String outputFile) throws IOException {
        try (FastIONIO f = new FastIONIO(inputFile, outputFile)) {
            int n = f.nextInt();
            long[] arr = f.nextLongArray(n);
            
            long sum = 0;
            for (long x : arr) {
                sum += x;
            }
            
            f.println(sum);
            return true;
        }
    }
    
    private static boolean testFastIOOriginalMultiple(String inputFile, String outputFile) throws IOException {
        try (FastIO f = new FastIO(inputFile, outputFile)) {
            int t = f.nextInt();
            
            while (t-- > 0) {
                int n = f.nextInt();
                int[] arr = f.nextIntArray(n);
                
                int sum = 0;
                for (int x : arr) {
                    sum += x;
                }
                
                f.println(sum);
            }
            return true;
        }
    }
    
    private static boolean testFastIONIOMultiple(String inputFile, String outputFile) throws IOException {
        try (FastIONIO f = new FastIONIO(inputFile, outputFile)) {
            int t = f.nextInt();
            
            while (t-- > 0) {
                int n = f.nextInt();
                int[] arr = f.nextIntArray(n);
                
                int sum = 0;
                for (int x : arr) {
                    sum += x;
                }
                
                f.println(sum);
            }
            return true;
        }
    }
    
    private static boolean testFastIOOriginalMixed(String inputFile, String outputFile) throws IOException {
        try (FastIO f = new FastIO(inputFile, outputFile)) {
            try {
                while (true) {
                    String token = f.nextLine();
                    if (token == null || token.trim().isEmpty()) break;
                    f.println("Processed: " + token);
                }
            } catch (Exception e) {
                // End of input
            }
            return true;
        }
    }
    
    private static boolean testFastIONIOMixed(String inputFile, String outputFile) throws IOException {
        try (FastIONIO f = new FastIONIO(inputFile, outputFile)) {
            try {
                while (f.hasNext()) {
                    String token = f.nextLine();
                    if (token == null || token.trim().isEmpty()) break;
                    f.println("Processed: " + token);
                }
            } catch (Exception e) {
                // End of input
            }
            return true;
        }
    }
    
    private static boolean testFastIOOriginalOutput(String outputFile, int n) throws IOException {
        try (FastIO f = new FastIO(System.in, new FileOutputStream(outputFile))) {
            for (int i = 0; i < n; i++) {
                f.println(i * i);
            }
            return true;
        }
    }
    
    private static boolean testFastIONIOOutput(String outputFile, int n) throws IOException {
        try (FastIONIO f = new FastIONIO(System.in, new FileOutputStream(outputFile))) {
            for (int i = 0; i < n; i++) {
                f.println(i * i);
            }
            return true;
        }
    }
    
    // Utility methods
    
    private static long timeOperation(Runnable operation) {
        long start = System.nanoTime();
        operation.run();
        long end = System.nanoTime();
        return TimeUnit.NANOSECONDS.toMillis(end - start);
    }
    
    private static void deleteFile(String filename) {
        new File(filename).delete();
    }
}