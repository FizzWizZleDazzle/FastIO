import java.io.*;
import java.io.BufferedReader;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.math.BigInteger;
/**
 * Ultra-optimized FastIO for USACO competitions
 * Features: Async I/O, Variable-size arrays, Mathematical utilities,
 * Memory optimization, Thread safety, Error recovery
 */
public static class FastIO implements AutoCloseable {
    private final BufferedReader br;
    private StringTokenizer st;
    private final PrintWriter pw;
    private final ExecutorService executor;
    private volatile CompletableFuture<String> nextLineFuture;
    private final AtomicBoolean closed = new AtomicBoolean(false);
    private final Object lock = new Object();
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
        this.br = new BufferedReader(new InputStreamReader(in), 65536);
        this.pw = new PrintWriter(new BufferedOutputStream(out, 65536), false);
        this.executor = Executors.newSingleThreadExecutor(r -> {
            Thread t = new Thread(r, "FastIO-Reader");
            t.setDaemon(true);
            return t;
        });
        safePrefetchNextLine();
    }

    // Constructor for file-based input/output (useful for testing)
    public FastIO(String inputFile) throws IOException {
        this(new FileInputStream(inputFile), System.out);
    }

    public FastIO(String inputFile, String outputFile) throws IOException {
        this(new FileInputStream(inputFile), new FileOutputStream(outputFile));
    }

    private void safePrefetchNextLine() {
        if (closed.get())
            return;

        synchronized (lock) {
            if (nextLineFuture == null || nextLineFuture.isDone()) {
                nextLineFuture = CompletableFuture.supplyAsync(() -> {
                    if (closed.get())
                        return null;
                    try {
                        return br.readLine();
                    } catch (IOException e) {
                        if (!closed.get()) {
                            throw new UncheckedIOException("Failed to read line", e);
                        }
                        return null;
                    }
                }, executor);
            }
        }
    }

    // Basic input methods
    public String next() {
        while (st == null || !st.hasMoreElements()) {
            try {
                String line = nextLine();
                if (line == null) {
                    throw new NoSuchElementException("No more input available");
                }
                st = new StringTokenizer(line);
            } catch (UncheckedIOException e) {
                throw new RuntimeException("Failed to read next token", e);
            }
        }
        return st.nextToken();
    }

    public String nextLine() {
        if (closed.get()) {
            throw new IllegalStateException("FastIO is closed");
        }

        try {
            String result;
            synchronized (lock) {
                if (nextLineFuture == null) {
                    result = br.readLine();
                } else {
                    result = nextLineFuture.get(5, TimeUnit.SECONDS);
                    safePrefetchNextLine();
                }
            }
            return result;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new UncheckedIOException("Read operation interrupted", new IOException(e));
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof UncheckedIOException) {
                throw (UncheckedIOException) cause;
            }
            throw new UncheckedIOException("Async read failed", new IOException(cause));
        } catch (TimeoutException e) {
            throw new UncheckedIOException("Read operation timed out", new IOException(e));
        } catch (IOException e) {
            throw new UncheckedIOException("IO error during read", e);
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

    public BigInteger nextBigInteger() {
        return new BigInteger(next());
    }

    public char nextChar() {
        return next().charAt(0);
    }

    // Enhanced array reading methods - handles variable sizes
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

    public double[] nextDoubleArray(int n) {
        double[] arr = new double[n];
        for (int i = 0; i < n; i++) {
            arr[i] = nextDouble();
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

    // Variable-size array reading - reads until end of input or line
    public List<Integer> nextIntList() {
        List<Integer> list = new ArrayList<>();
        String line = nextLine();
        if (line != null && !line.trim().isEmpty()) {
            StringTokenizer tokens = new StringTokenizer(line);
            while (tokens.hasMoreTokens()) {
                list.add(Integer.parseInt(tokens.nextToken()));
            }
        }
        return list;
    }

    public List<Long> nextLongList() {
        List<Long> list = new ArrayList<>();
        String line = nextLine();
        if (line != null && !line.trim().isEmpty()) {
            StringTokenizer tokens = new StringTokenizer(line);
            while (tokens.hasMoreTokens()) {
                list.add(Long.parseLong(tokens.nextToken()));
            }
        }
        return list;
    }

    public List<String> nextStringList() {
        List<String> list = new ArrayList<>();
        String line = nextLine();
        if (line != null && !line.trim().isEmpty()) {
            StringTokenizer tokens = new StringTokenizer(line);
            while (tokens.hasMoreTokens()) {
                list.add(tokens.nextToken());
            }
        }
        return list;
    }

    // USACO-style array reading without requiring dimensions
    // Arrays are separated by newlines, matrices are always last parameter

    /**
     * Reads all remaining integers from current line as an array
     * Perfect for when array size is not predetermined
     */
    public int[] readIntArray() {
        List<Integer> list = nextIntList();
        return list.stream().mapToInt(Integer::intValue).toArray();
    }

    /**
     * Reads all remaining longs from current line as an array
     */
    public long[] readLongArray() {
        List<Long> list = nextLongList();
        return list.stream().mapToLong(Long::longValue).toArray();
    }

    /**
     * Reads all remaining doubles from current line as an array
     */
    public double[] readDoubleArray() {
        List<Double> list = new ArrayList<>();
        String line = nextLine();
        if (line != null && !line.trim().isEmpty()) {
            StringTokenizer tokens = new StringTokenizer(line);
            while (tokens.hasMoreTokens()) {
                list.add(Double.parseDouble(tokens.nextToken()));
            }
        }
        return list.stream().mapToDouble(Double::doubleValue).toArray();
    }

    /**
     * Reads all remaining strings from current line as an array
     */
    public String[] readStringArray() {
        List<String> list = nextStringList();
        return list.toArray(new String[0]);
    }

    /**
     * Reads a 2D matrix until end of input (no dimensions required)
     * Perfect for USACO where matrix is always the last parameter
     */
    public int[][] readIntMatrix() {
        List<int[]> rows = new ArrayList<>();

        try {
            while (hasNext()) {
                String line = nextLine();
                if (line == null || line.trim().isEmpty()) {
                    break;
                }

                // Parse the line into integers
                StringTokenizer tokens = new StringTokenizer(line);
                List<Integer> rowData = new ArrayList<>();
                while (tokens.hasMoreTokens()) {
                    rowData.add(Integer.parseInt(tokens.nextToken()));
                }

                if (!rowData.isEmpty()) {
                    int[] row = rowData.stream().mapToInt(Integer::intValue).toArray();
                    rows.add(row);
                }
            }
        } catch (Exception e) {
            // End of input reached or parsing error
        }

        return rows.toArray(new int[0][]);
    }

    /**
     * Reads a 2D matrix of longs until end of input
     */
    public long[][] readLongMatrix() {
        List<long[]> rows = new ArrayList<>();

        try {
            while (hasNext()) {
                String line = nextLine();
                if (line == null || line.trim().isEmpty()) {
                    break;
                }

                StringTokenizer tokens = new StringTokenizer(line);
                List<Long> rowData = new ArrayList<>();
                while (tokens.hasMoreTokens()) {
                    rowData.add(Long.parseLong(tokens.nextToken()));
                }

                if (!rowData.isEmpty()) {
                    long[] row = rowData.stream().mapToLong(Long::longValue).toArray();
                    rows.add(row);
                }
            }
        } catch (Exception e) {
            // End of input reached
        }

        return rows.toArray(new long[0][]);
    }

    /**
     * Reads a character matrix until end of input
     * Each line becomes a row of characters
     */
    public char[][] readCharMatrix() {
        List<char[]> rows = new ArrayList<>();

        try {
            while (hasNext()) {
                String line = nextLine();
                if (line == null) {
                    break;
                }
                rows.add(line.toCharArray());
            }
        } catch (Exception e) {
            // End of input reached
        }

        return rows.toArray(new char[0][]);
    }

    /**
     * Reads a string matrix until end of input
     */
    public String[][] readStringMatrix() {
        List<String[]> rows = new ArrayList<>();

        try {
            while (hasNext()) {
                String line = nextLine();
                if (line == null || line.trim().isEmpty()) {
                    break;
                }

                StringTokenizer tokens = new StringTokenizer(line);
                List<String> rowData = new ArrayList<>();
                while (tokens.hasMoreTokens()) {
                    rowData.add(tokens.nextToken());
                }

                if (!rowData.isEmpty()) {
                    rows.add(rowData.toArray(new String[0]));
                }
            }
        } catch (Exception e) {
            // End of input reached
        }

        return rows.toArray(new String[0][]);
    }

    /**
     * Reads exactly n lines as a matrix (when you know the number of rows)
     */
    public int[][] readIntMatrix(int rows) {
        int[][] matrix = new int[rows][];
        for (int i = 0; i < rows; i++) {
            matrix[i] = readIntArray();
        }
        return matrix;
    }

    /**
     * Reads exactly n lines as a long matrix
     */
    public long[][] readLongMatrix(int rows) {
        long[][] matrix = new long[rows][];
        for (int i = 0; i < rows; i++) {
            matrix[i] = readLongArray();
        }
        return matrix;
    }

    /**
     * Reads exactly n lines as a character matrix
     */
    public char[][] readCharMatrix(int rows) {
        char[][] matrix = new char[rows][];
        for (int i = 0; i < rows; i++) {
            String line = nextLine();
            matrix[i] = line != null ? line.toCharArray() : new char[0];
        }
        return matrix;
    }

    // 2D Array methods
    public int[][] nextInt2DArray(int rows, int cols) {
        int[][] arr = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                arr[i][j] = nextInt();
            }
        }
        return arr;
    }

    public long[][] nextLong2DArray(int rows, int cols) {
        long[][] arr = new long[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                arr[i][j] = nextLong();
            }
        }
        return arr;
    }

    public char[][] nextChar2DArray(int rows, int cols) {
        char[][] arr = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            String line = nextLine();
            for (int j = 0; j < cols && j < line.length(); j++) {
                arr[i][j] = line.charAt(j);
            }
        }
        return arr;
    }

    // Variable-size 2D arrays (common in USACO)
    public List<List<Integer>> nextInt2DList(int rows) {
        List<List<Integer>> list = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            list.add(nextIntList());
        }
        return list;
    }

    // Graph input helpers
    public List<List<Integer>> readAdjacencyList(int n, int m) {
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            adj.add(new ArrayList<>());
        }
        for (int i = 0; i < m; i++) {
            int u = nextInt();
            int v = nextInt();
            adj.get(u).add(v);
            adj.get(v).add(u);
        }
        return adj;
    }

    public List<List<int[]>> readWeightedAdjacencyList(int n, int m) {
        List<List<int[]>> adj = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            adj.add(new ArrayList<>());
        }
        for (int i = 0; i < m; i++) {
            int u = nextInt();
            int v = nextInt();
            int w = nextInt();
            adj.get(u).add(new int[] { v, w });
            adj.get(v).add(new int[] { u, w });
        }
        return adj;
    }

    // Async methods for large inputs
    public CompletableFuture<int[]> nextIntArrayAsync(int n) {
        return CompletableFuture.supplyAsync(() -> nextIntArray(n), executor)
                .orTimeout(0, TimeUnit.SECONDS);
    }

    public CompletableFuture<long[]> nextLongArrayAsync(int n) {
        return CompletableFuture.supplyAsync(() -> nextLongArray(n), executor)
                .orTimeout(0, TimeUnit.SECONDS);
    }

    // п
    public CompletableFuture<int[][]> nextInt2DArrayAsync(int rows, int cols) {
        return CompletableFuture.supplyAsync(() -> nextInt2DArray(rows, cols), executor)
                .orTimeout(0, TimeUnit.SECONDS);
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

    // Fast integer log base 2 (optimized for competitive programming)
    public static int log2(int x) {
        return 31 - Integer.numberOfLeadingZeros(x);
    }

    public static int log2(long x) {
        return 63 - Long.numberOfLeadingZeros(x);
    }

    // Check if power of 2
    public static boolean isPowerOfTwo(int x) {
        return x > 0 && (x & (x - 1)) == 0;
    }

    public static boolean isPowerOfTwo(long x) {
        return x > 0 && (x & (x - 1)) == 0;
    }

    // Output methods
    public void print(Object obj) {
        if (!closed.get()) {
            pw.print(obj);
        }
    }

    public void println(Object obj) {
        if (!closed.get()) {
            pw.println(obj);
        }
    }

    public void println() {
        if (!closed.get()) {
            pw.println();
        }
    }

    public void printf(String format, Object... args) {
        if (!closed.get()) {
            pw.printf(format, args);
        }
    }

    // Array printing methods
    public void printArray(int[] arr) {
        printArray(arr, " ");
    }

    public void printArray(int[] arr, String delimiter) {
        if (closed.get() || arr == null)
            return;
        for (int i = 0; i < arr.length; i++) {
            if (i > 0)
                pw.print(delimiter);
            pw.print(arr[i]);
        }
        pw.println();
    }

    public void printArray(long[] arr) {
        printArray(arr, " ");
    }

    public void printArray(long[] arr, String delimiter) {
        if (closed.get() || arr == null)
            return;
        for (int i = 0; i < arr.length; i++) {
            if (i > 0)
                pw.print(delimiter);
            pw.print(arr[i]);
        }
        pw.println();
    }

    public void print2DArray(int[][] arr) {
        if (closed.get() || arr == null)
            return;
        for (int[] row : arr) {
            printArray(row);
        }
    }

    public void print2DArray(long[][] arr) {
        if (closed.get() || arr == null)
            return;
        for (long[] row : arr) {
            printArray(row);
        }
    }

    public void print2DArray(char[][] arr) {
        if (closed.get() || arr == null)
            return;
        for (char[] row : arr) {
            pw.println(new String(row));
        }
    }

    // List printing methods
    public void printList(List<?> list) {
        printList(list, " ");
    }

    public void printList(List<?> list, String delimiter) {
        if (closed.get() || list == null)
            return;
        for (int i = 0; i < list.size(); i++) {
            if (i > 0)
                pw.print(delimiter);
            pw.print(list.get(i));
        }
        pw.println();
    }

    public void flush() {
        if (!closed.get()) {
            pw.flush();
        }
    }

    // Check if more input is available (enhanced for matrix reading)
    public boolean hasNext() {
        if (closed.get())
            return false;

        try {
            synchronized (lock) {
                // Check if we have a tokenizer with more tokens
                if (st != null && st.hasMoreTokens()) {
                    return true;
                }

                // Check if BufferedReader has more data
                if (br.ready()) {
                    return true;
                }

                // Check if async future has data
                if (nextLineFuture != null && !nextLineFuture.isDone()) {
                    return true;
                }

                // Try to peek ahead by attempting to read a line
                try {
                    br.mark(1024); // Mark current position
                    String peekLine = br.readLine();
                    if (peekLine != null) {
                        br.reset(); // Reset to marked position
                        return true;
                    }
                } catch (IOException e) {
                    // If mark/reset fails, fall back to basic check
                    try {
                        br.reset(); // Try to reset anyway
                    } catch (IOException ignored) {
                    }
                }

                return false;
            }
        } catch (IOException e) {
            System.out.println(e);
            return false;
        }
    }

    // Debug methods for competitive programming
    public void debug(Object... objects) {
        if (!closed.get()) {
            pw.print("DEBUG: ");
            for (int i = 0; i < objects.length; i++) {
                if (i > 0)
                    pw.print(", ");
                pw.print(objects[i]);
            }
            pw.println();
            pw.flush();
        }
    }

    public void debugArray(int[] arr) {
        debug("Array: " + Arrays.toString(arr));
    }

    public void debugArray(long[] arr) {
        debug("Array: " + Arrays.toString(arr));
    }

    public void debug2DArray(int[][] arr) {
        if (!closed.get()) {
            pw.println("DEBUG 2D Array:");
            print2DArray(arr);
            pw.flush();
        }
    }

    // Proper resource cleanup
    @Override
    public void close() {
        if (closed.compareAndSet(false, true)) {
            try {
                synchronized (lock) {
                    if (nextLineFuture != null && !nextLineFuture.isDone()) {
                        nextLineFuture.cancel(true);
                    }
                }

                executor.shutdown();
                try {
                    if (!executor.awaitTermination(2, TimeUnit.SECONDS)) {
                        executor.shutdownNow();
                        if (!executor.awaitTermination(1, TimeUnit.SECONDS)) {
                            System.err.println("FastIO executor did not terminate cleanly");
                        }
                    }
                } catch (InterruptedException e) {
                    executor.shutdownNow();
                    Thread.currentThread().interrupt();
                }

                try {
                    br.close();
                } catch (IOException e) {
                    System.err.println("Error closing BufferedReader: " + e.getMessage());
                }

                pw.close();

            } catch (Exception e) {
                System.err.println("Error during FastIO cleanup: " + e.getMessage());
            }
        }
    }

    // Template main method for USACO
    public static void main(String[] args) {
        try (FastIO io = new FastIO()) {
            // USACO file I/O template
            if (args.length > 0) {
                try {
                    System.setIn(new FileInputStream(args[0] + ".in"));
                    System.setOut(new PrintStream(args[0] + ".out"));
                } catch (IOException e) {
                    System.err.println("Failed to set up file I/O: " + e.getMessage());
                }
            }

            // Your solution code here
            solve(io);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Template solve method
    public static void solve(FastIO io) {
        // Example: Read number of test cases
        int t = io.nextInt();

        while (t-- > 0) {
            // Example: Read array size and array
            int n = io.nextInt();
            int[] arr = io.nextIntArray(n);

            // Your solution logic here
            long sum = 0;
            for (int x : arr) {
                sum += x;
            }

            io.println(sum);
        }

        io.flush();
    }
}
