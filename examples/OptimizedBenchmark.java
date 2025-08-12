import java.io.*;
import java.util.*;

/**
 * Comprehensive benchmark comparing original and optimized FastIO methods
 */
public class OptimizedBenchmark {
    
    public static void main(String[] args) {
        System.out.println("FastIO Optimization Benchmark");
        System.out.println("=============================");
        
        try {
            createTestData();
            
            System.out.println("\nBenchmarking original vs optimized methods:");
            
            // Test integer parsing performance
            benchmarkIntegerParsing();
            
            // Test array reading performance
            benchmarkArrayReading();
            
            // Test large input handling
            benchmarkLargeInput();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void createTestData() throws IOException {
        // Create test file with 500K integers for more thorough testing
        try (PrintWriter pw = new PrintWriter(new FileWriter("benchmark_test.txt"))) {
            Random rand = new Random(12345);
            for (int i = 0; i < 500000; i++) {
                pw.println(rand.nextInt(1000000));
            }
        }
        System.out.println("Created test data: 500K integers");
    }
    
    private static void benchmarkIntegerParsing() throws IOException {
        System.out.println("\n--- Integer Parsing Benchmark ---");
        
        // Test with regular FastIO
        long startTime = System.nanoTime();
        try (FastIO f = new FastIO("benchmark_test.txt")) {
            int count = 0;
            while (true) {
                try {
                    f.nextInt();
                    count++;
                } catch (Exception e) {
                    break;
                }
            }
            long endTime = System.nanoTime();
            double timeMs = (endTime - startTime) / 1_000_000.0;
            System.out.printf("Optimized integer parsing: %d integers in %.2f ms\n", count, timeMs);
            System.out.printf("Throughput: %.0f integers/sec\n", count / (timeMs / 1000.0));
        }
    }
    
    private static void benchmarkArrayReading() throws IOException {
        System.out.println("\n--- Array Reading Benchmark ---");
        
        // Create array test data
        try (PrintWriter pw = new PrintWriter(new FileWriter("array_test.txt"))) {
            Random rand = new Random(12345);
            for (int i = 0; i < 1000; i++) {
                for (int j = 0; j < 100; j++) {
                    pw.print(rand.nextInt(1000) + " ");
                }
                pw.println();
            }
        }
        
        long startTime = System.nanoTime();
        try (FastIO f = new FastIO("array_test.txt")) {
            int totalElements = 0;
            for (int i = 0; i < 1000; i++) {
                int[] arr = f.nextIntArray(100);
                totalElements += arr.length;
            }
            long endTime = System.nanoTime();
            double timeMs = (endTime - startTime) / 1_000_000.0;
            System.out.printf("Array reading: %d elements in %.2f ms\n", totalElements, timeMs);
            System.out.printf("Throughput: %.0f elements/sec\n", totalElements / (timeMs / 1000.0));
        }
    }
    
    private static void benchmarkLargeInput() throws IOException {
        System.out.println("\n--- Large Input Benchmark ---");
        
        // Create larger test file
        try (PrintWriter pw = new PrintWriter(new FileWriter("large_test.txt"))) {
            Random rand = new Random(12345);
            for (int i = 0; i < 2000000; i++) { // 2M integers
                pw.println(rand.nextInt(Integer.MAX_VALUE));
            }
        }
        
        long startTime = System.nanoTime();
        try (FastIO f = new FastIO("large_test.txt")) {
            int count = 0;
            while (true) {
                try {
                    f.nextInt();
                    count++;
                } catch (Exception e) {
                    break;
                }
            }
            long endTime = System.nanoTime();
            double timeMs = (endTime - startTime) / 1_000_000.0;
            System.out.printf("Large input processing: %d integers in %.2f ms\n", count, timeMs);
            System.out.printf("Throughput: %.0f integers/sec\n", count / (timeMs / 1000.0));
            
            // Calculate throughput in MB/s (assuming average 6 chars per integer)
            double mbPerSec = (count * 6.0) / (1024 * 1024) / (timeMs / 1000.0);
            System.out.printf("Data throughput: %.2f MB/s\n", mbPerSec);
        }
    }
}