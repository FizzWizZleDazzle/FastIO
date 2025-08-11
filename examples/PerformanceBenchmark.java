import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Performance benchmark for FastIO library
 * Tests throughput of various I/O operations to measure optimization improvements
 */
public class PerformanceBenchmark {
    
    private static final int WARM_UP_ITERATIONS = 5;
    private static final int BENCHMARK_ITERATIONS = 10;
    
    public static void main(String[] args) {
        System.out.println("FastIO Performance Benchmark");
        System.out.println("============================");
        
        try {
            // Create test data files
            createTestData();
            
            // Warm up JVM
            System.out.println("Warming up JVM...");
            for (int i = 0; i < WARM_UP_ITERATIONS; i++) {
                benchmarkIntegerReading(true);
                benchmarkStringReading(true);
                benchmarkArrayReading(true);
            }
            
            System.out.println("\nRunning benchmarks...");
            
            // Run actual benchmarks
            benchmarkIntegerReading(false);
            benchmarkStringReading(false);
            benchmarkArrayReading(false);
            benchmarkLargeInput(false);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void createTestData() throws IOException {
        System.out.println("Creating test data files...");
        
        // Create integer test file (1M integers)
        try (PrintWriter pw = new PrintWriter(new FileWriter("test_integers.txt"))) {
            Random rand = new Random(12345); // Fixed seed for consistent results
            for (int i = 0; i < 1000000; i++) {
                pw.println(rand.nextInt(1000000));
            }
        }
        
        // Create string test file (100K strings)
        try (PrintWriter pw = new PrintWriter(new FileWriter("test_strings.txt"))) {
            Random rand = new Random(12345);
            for (int i = 0; i < 100000; i++) {
                pw.println("string" + rand.nextInt(100000));
            }
        }
        
        // Create array test file (10K lines of 100 integers each)
        try (PrintWriter pw = new PrintWriter(new FileWriter("test_arrays.txt"))) {
            Random rand = new Random(12345);
            for (int i = 0; i < 10000; i++) {
                for (int j = 0; j < 100; j++) {
                    if (j > 0) pw.print(" ");
                    pw.print(rand.nextInt(1000));
                }
                pw.println();
            }
        }
        
        // Create large input file (10M integers)
        try (PrintWriter pw = new PrintWriter(new FileWriter("test_large.txt"))) {
            Random rand = new Random(12345);
            for (int i = 0; i < 10000000; i++) {
                pw.println(rand.nextInt(1000000));
            }
        }
    }
    
    private static void benchmarkIntegerReading(boolean warmup) throws IOException {
        if (!warmup) System.out.println("\n1. Integer Reading Benchmark (1M integers):");
        
        long[] times = new long[BENCHMARK_ITERATIONS];
        
        for (int iter = 0; iter < BENCHMARK_ITERATIONS; iter++) {
            long start = System.nanoTime();
            
            try (FastIO f = new FastIO("test_integers.txt")) {
                int count = 0;
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
        }
        
        if (!warmup) {
            double avgTimeMs = Arrays.stream(times).average().orElse(0) / 1_000_000.0;
            double throughputPerSec = 1_000_000.0 / (avgTimeMs / 1000.0);
            System.out.printf("   Average time: %.2f ms\n", avgTimeMs);
            System.out.printf("   Throughput: %.0f integers/sec\n", throughputPerSec);
        }
    }
    
    private static void benchmarkStringReading(boolean warmup) throws IOException {
        if (!warmup) System.out.println("\n2. String Reading Benchmark (100K strings):");
        
        long[] times = new long[BENCHMARK_ITERATIONS];
        
        for (int iter = 0; iter < BENCHMARK_ITERATIONS; iter++) {
            long start = System.nanoTime();
            
            try (FastIO f = new FastIO("test_strings.txt")) {
                int count = 0;
                while (true) {
                    try {
                        f.next();
                        count++;
                    } catch (Exception e) {
                        break;
                    }
                }
            }
            
            long end = System.nanoTime();
            times[iter] = end - start;
        }
        
        if (!warmup) {
            double avgTimeMs = Arrays.stream(times).average().orElse(0) / 1_000_000.0;
            double throughputPerSec = 100_000.0 / (avgTimeMs / 1000.0);
            System.out.printf("   Average time: %.2f ms\n", avgTimeMs);
            System.out.printf("   Throughput: %.0f strings/sec\n", throughputPerSec);
        }
    }
    
    private static void benchmarkArrayReading(boolean warmup) throws IOException {
        if (!warmup) System.out.println("\n3. Array Reading Benchmark (10K lines of 100 integers each):");
        
        long[] times = new long[BENCHMARK_ITERATIONS];
        
        for (int iter = 0; iter < BENCHMARK_ITERATIONS; iter++) {
            long start = System.nanoTime();
            
            try (FastIO f = new FastIO("test_arrays.txt")) {
                int lineCount = 0;
                while (true) {
                    try {
                        int[] arr = f.readIntArray();
                        if (arr.length == 0) break;
                        lineCount++;
                    } catch (Exception e) {
                        break;
                    }
                }
            }
            
            long end = System.nanoTime();
            times[iter] = end - start;
        }
        
        if (!warmup) {
            double avgTimeMs = Arrays.stream(times).average().orElse(0) / 1_000_000.0;
            double throughputPerSec = 1_000_000.0 / (avgTimeMs / 1000.0); // 10K lines * 100 integers
            System.out.printf("   Average time: %.2f ms\n", avgTimeMs);
            System.out.printf("   Throughput: %.0f integers/sec\n", throughputPerSec);
        }
    }
    
    private static void benchmarkLargeInput(boolean warmup) throws IOException {
        if (!warmup) System.out.println("\n4. Large Input Benchmark (10M integers):");
        
        long[] times = new long[3]; // Fewer iterations for large test
        
        for (int iter = 0; iter < 3; iter++) {
            long start = System.nanoTime();
            
            try (FastIO f = new FastIO("test_large.txt")) {
                int count = 0;
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
        }
        
        if (!warmup) {
            double avgTimeMs = Arrays.stream(times).average().orElse(0) / 1_000_000.0;
            double throughputPerSec = 10_000_000.0 / (avgTimeMs / 1000.0);
            System.out.printf("   Average time: %.2f ms\n", avgTimeMs);
            System.out.printf("   Throughput: %.0f integers/sec\n", throughputPerSec);
        }
    }
    
    // Copy of FastIO class for benchmarking (inline to avoid classpath issues)
    static class FastIO implements AutoCloseable {
        private final BufferedReader br;
        private StringTokenizer st;
        private final PrintWriter pw;
        
        public FastIO() {
            this(System.in, System.out);
        }
        
        public FastIO(InputStream in, OutputStream out) {
            this.br = new BufferedReader(new InputStreamReader(in), 65536);
            this.pw = new PrintWriter(new BufferedOutputStream(out, 65536), false);
        }
        
        public FastIO(String inputFile) throws IOException {
            this(new FileInputStream(inputFile), System.out);
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
        
        public int[] readIntArray() {
            try {
                String line = br.readLine();
                if (line == null || line.trim().isEmpty()) {
                    return new int[0];
                }
                
                StringTokenizer tokens = new StringTokenizer(line);
                List<Integer> list = new ArrayList<>();
                while (tokens.hasMoreTokens()) {
                    list.add(Integer.parseInt(tokens.nextToken()));
                }
                return list.stream().mapToInt(Integer::intValue).toArray();
            } catch (IOException e) {
                return new int[0];
            }
        }
        
        @Override
        public void close() {
            try {
                if (br != null) br.close();
                if (pw != null) pw.close();
            } catch (IOException e) {
                // Ignore
            }
        }
    }
}