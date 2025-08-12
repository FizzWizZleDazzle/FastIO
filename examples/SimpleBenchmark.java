import java.io.*;
import java.util.*;

/**
 * Simple performance benchmark for FastIO library
 */
public class SimpleBenchmark {
    
    public static void main(String[] args) {
        System.out.println("FastIO Simple Benchmark");
        System.out.println("=======================");
        
        try {
            // Create small test data
            createSmallTestData();
            benchmarkCurrentFastIO();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void createSmallTestData() throws IOException {
        // Create test file with 100K integers
        try (PrintWriter pw = new PrintWriter(new FileWriter("small_test.txt"))) {
            Random rand = new Random(12345);
            for (int i = 0; i < 100000; i++) {
                pw.println(rand.nextInt(1000000));
            }
        }
        System.out.println("Created test data: 100K integers");
    }
    
    private static void benchmarkCurrentFastIO() throws IOException {
        System.out.println("\nBenchmarking current FastIO implementation:");
        
        // Warm up
        for (int i = 0; i < 3; i++) {
            try (SimpleFastIO f = new SimpleFastIO("small_test.txt")) {
                while (true) {
                    try {
                        f.nextInt();
                    } catch (Exception e) {
                        break;
                    }
                }
            }
        }
        
        // Actual benchmark
        long[] times = new long[5];
        for (int iter = 0; iter < 5; iter++) {
            long start = System.nanoTime();
            
            int count = 0;
            try (SimpleFastIO f = new SimpleFastIO("small_test.txt")) {
                while (true) {
                    try {
                        f.nextInt();
                        count++;
                    } catch (Exception e) {
                        break;
                    }
                }
            }
            
            long end = System.nanoTime();
            times[iter] = end - start;
            
            System.out.printf("Iteration %d: %d integers in %.2f ms\n", 
                iter + 1, count, (end - start) / 1_000_000.0);
        }
        
        double avgTimeMs = Arrays.stream(times).average().orElse(0) / 1_000_000.0;
        double throughputPerSec = 100_000.0 / (avgTimeMs / 1000.0);
        System.out.printf("\nAverage time: %.2f ms\n", avgTimeMs);
        System.out.printf("Throughput: %.0f integers/sec\n", throughputPerSec);
    }
    
    // Simplified FastIO for baseline testing
    static class SimpleFastIO implements AutoCloseable {
        private final BufferedReader br;
        private StringTokenizer st;
        
        public SimpleFastIO(String inputFile) throws IOException {
            this.br = new BufferedReader(new FileReader(inputFile), 65536);
        }
        
        public String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    String line = br.readLine();
                    if (line == null) {
                        throw new NoSuchElementException("No more input available");
                    }
                    st = new StringTokenizer(line);
                } catch (IOException e) {
                    throw new UncheckedIOException("Failed to read next token", e);
                }
            }
            return st.nextToken();
        }
        
        public int nextInt() {
            return Integer.parseInt(next());
        }
        
        @Override
        public void close() {
            try {
                if (br != null) br.close();
            } catch (IOException e) {
                // Ignore
            }
        }
    }
}