import java.io.*;
import java.util.*;

/**
 * Starter template for USACO problems using FastIO
 * Simply copy this template and implement your solution in the solve() method.
 */
public class Demo {
    
    public static void main(String[] args) {
        // Enable testing mode by setting TEST_MODE environment variable
        if (System.getenv("TEST_MODE") != null && System.getenv("TEST_MODE").equals("true")) {
            // Get input and output file names from environment or use defaults
            String inputFile = System.getenv("TEST_INPUT");
            String outputFile = System.getenv("TEST_OUTPUT");
            
            if (inputFile == null) inputFile = "input.txt";
            if (outputFile == null) outputFile = "output.txt";
            
            // Run with test input/output files
            System.out.println(Test.testFunction(
                Demo::solve,
                inputFile,
                outputFile
            ));
        } else {
            // Run normally (for actual submission)
            solve();
        }
    }
    
    /**
     * Your solution goes here.
     * This template provides FastIO for efficient input/output operations.
     */
    public static void solve() {
        FastIO f = new FastIO();
        
        // Example: Read array size and array, then output sum
        int n = f.nextInt();
        int[] arr = f.nextIntArray(n);
        
        int sum = 0;
        for (int x : arr) {
            sum += x;
        }
        
        f.println(sum);
        f.close();
    }
    
    // FastIO class included directly for single-file submissions
    static class FastIO implements AutoCloseable {
        private final BufferedReader br;
        private StringTokenizer st;
        private final PrintWriter pw;
        
        // USACO-specific constants
        public static final int INF = (int) 1e9;
        public static final long LINF = (long) 1e18;
        public static final double EPS = 1e-9;
        public static final int MOD = 1000000007;
        public static final int MOD2 = 998244353;
        
        public FastIO() {
            this(System.in, System.out);
        }
        
        public FastIO(InputStream in, OutputStream out) {
            this.br = new BufferedReader(new InputStreamReader(in), 32768);
            this.pw = new PrintWriter(new BufferedOutputStream(out, 32768), false);
        }
        
        public FastIO(String inputFile) throws IOException {
            this(new FileInputStream(inputFile), System.out);
        }
        
        public FastIO(String inputFile, String outputFile) throws IOException {
            this(new FileInputStream(inputFile), new FileOutputStream(outputFile));
        }
        
        // Basic input methods
        public String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    throw new RuntimeException("Failed to read input", e);
                }
            }
            return st.nextToken();
        }
        
        public String nextLine() {
            try {
                return br.readLine();
            } catch (IOException e) {
                throw new RuntimeException("Failed to read line", e);
            }
        }
        
        public int nextInt() {
            return Integer.parseInt(next());
        }
        
        public long nextLong() {
            return Long.parseLong(next());
        }
        
        public double nextDouble() {
            return Double.parseDouble(next());
        }
        
        public char nextChar() {
            return next().charAt(0);
        }
        
        // Array input methods
        public int[] nextIntArray(int n) {
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) {
                arr[i] = nextInt();
            }
            return arr;
        }
        
        public long[] nextLongArray(int n) {
            long[] arr = new long[n];
            for (int i = 0; i < n; i++) {
                arr[i] = nextLong();
            }
            return arr;
        }
        
        public String[] nextStringArray(int n) {
            String[] arr = new String[n];
            for (int i = 0; i < n; i++) {
                arr[i] = next();
            }
            return arr;
        }
        
        // Read entire line as array (when size is unknown)
        public int[] readIntArray() {
            String line = nextLine();
            if (line == null || line.trim().isEmpty()) {
                return new int[0];
            }
            StringTokenizer tokens = new StringTokenizer(line);
            List<Integer> list = new ArrayList<>();
            while (tokens.hasMoreTokens()) {
                list.add(Integer.parseInt(tokens.nextToken()));
            }
            return list.stream().mapToInt(Integer::intValue).toArray();
        }
        
        // 2D array methods
        public int[][] nextInt2DArray(int rows, int cols) {
            int[][] arr = new int[rows][cols];
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    arr[i][j] = nextInt();
                }
            }
            return arr;
        }
        
        public char[][] nextChar2DArray(int rows) {
            char[][] arr = new char[rows][];
            for (int i = 0; i < rows; i++) {
                arr[i] = nextLine().toCharArray();
            }
            return arr;
        }
        
        // Mathematical utility methods
        public static int gcd(int a, int b) {
            return b == 0 ? a : gcd(b, a % b);
        }
        
        public static long gcd(long a, long b) {
            return b == 0 ? a : gcd(b, a % b);
        }
        
        public static int lcm(int a, int b) {
            return a / gcd(a, b) * b;
        }
        
        public static long lcm(long a, long b) {
            return a / gcd(a, b) * b;
        }
        
        public static long modPow(long base, long exp, long mod) {
            long result = 1;
            base %= mod;
            while (exp > 0) {
                if ((exp & 1) == 1) {
                    result = (result * base) % mod;
                }
                base = (base * base) % mod;
                exp >>= 1;
            }
            return result;
        }
        
        public static long modInv(long a, long mod) {
            return modPow(a, mod - 2, mod);
        }
        
        public static boolean isPowerOfTwo(int x) {
            return x > 0 && (x & (x - 1)) == 0;
        }
        
        public static boolean isPowerOfTwo(long x) {
            return x > 0 && (x & (x - 1)) == 0;
        }
        
        // Output methods
        public void print(Object obj) {
            pw.print(obj);
        }
        
        public void println(Object obj) {
            pw.println(obj);
        }
        
        public void println() {
            pw.println();
        }
        
        public void printf(String format, Object... args) {
            pw.printf(format, args);
        }
        
        // Array printing methods
        public void printArray(int[] arr) {
            printArray(arr, " ");
        }
        
        public void printArray(int[] arr, String delimiter) {
            if (arr == null) return;
            for (int i = 0; i < arr.length; i++) {
                if (i > 0) pw.print(delimiter);
                pw.print(arr[i]);
            }
            pw.println();
        }
        
        public void printArray(long[] arr) {
            printArray(arr, " ");
        }
        
        public void printArray(long[] arr, String delimiter) {
            if (arr == null) return;
            for (int i = 0; i < arr.length; i++) {
                if (i > 0) pw.print(delimiter);
                pw.print(arr[i]);
            }
            pw.println();
        }
        
        public void print2DArray(int[][] arr) {
            if (arr == null) return;
            for (int[] row : arr) {
                printArray(row);
            }
        }
        
        public void flush() {
            pw.flush();
        }
        
        @Override
        public void close() {
            try {
                br.close();
            } catch (IOException e) {
                // Ignore
            }
            pw.close();
        }
    }
    
    // Simple Test class for basic testing (optional - remove if not needed)
    static class Test {
        public static class TestResult {
            public final boolean passed;
            public final String actualOutput;
            public final String errorMessage;
            
            public TestResult(boolean passed, String actualOutput, String errorMessage) {
                this.passed = passed;
                this.actualOutput = actualOutput != null ? actualOutput : "";
                this.errorMessage = errorMessage;
            }
            
            @Override
            public String toString() {
                return String.format("Test %s | Output: '%s'%s",
                    passed ? "PASSED" : "FAILED",
                    actualOutput.replace("\n", "\\n"),
                    errorMessage != null ? " | Error: " + errorMessage : "");
            }
        }
        
        public static TestResult testFunction(Runnable func, String input, String expectedOutput) {
            if (func == null) {
                return new TestResult(false, "", "Function is null");
            }
            
            // Check if input is a file path
            String inputData = input;
            if (input.endsWith(".txt")) {
                try {
                    inputData = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(input)));
                } catch (Exception e) {
                    return new TestResult(false, "", "Failed to read input file: " + input);
                }
            }
            
            // Check if expected output is a file path
            String expectedData = expectedOutput;
            if (expectedOutput.endsWith(".txt")) {
                try {
                    expectedData = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(expectedOutput)));
                } catch (Exception e) {
                    return new TestResult(false, "", "Failed to read expected output file: " + expectedOutput);
                }
            }
            
            ByteArrayInputStream testIn = new ByteArrayInputStream(inputData.getBytes());
            ByteArrayOutputStream testOut = new ByteArrayOutputStream();
            
            InputStream originalIn = System.in;
            PrintStream originalOut = System.out;
            
            System.setIn(testIn);
            System.setOut(new PrintStream(testOut));
            
            String errorMessage = null;
            try {
                func.run();
            } catch (Exception e) {
                errorMessage = e.getClass().getSimpleName() + ": " + e.getMessage();
            } finally {
                System.setIn(originalIn);
                System.setOut(originalOut);
            }
            
            String actualOutput = testOut.toString().trim();
            String expectedTrimmed = expectedData != null ? expectedData.trim() : "";
            boolean passed = actualOutput.equals(expectedTrimmed) && errorMessage == null;
            
            return new TestResult(passed, actualOutput, errorMessage);
        }
    }
}