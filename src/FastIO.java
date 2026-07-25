import java.io.*;
import java.util.*;

/**
 * FastIO: buffered input/output for competitive programming.
 *
 * Reads with a 32 KB BufferedReader and StringTokenizer; writes through a
 * buffered PrintWriter. Faster than Scanner for large inputs. Single class,
 * so it can be pasted into a one-file contest submission.
 */
public class FastIO implements AutoCloseable {
    private final BufferedReader br;
    private StringTokenizer st;
    private final PrintWriter pw;

    // Common competitive-programming constants.
    public static final int INF = (int) 1e9;
    public static final long LINF = (long) 1e18;
    public static final double EPS = 1e-9;
    public static final int MOD = 1_000_000_007;
    public static final int MOD2 = 998_244_353;

    public FastIO() {
        this(System.in, System.out);
    }

    public FastIO(InputStream in, OutputStream out) {
        this.br = new BufferedReader(new InputStreamReader(in), 1 << 15);
        this.pw = new PrintWriter(new BufferedOutputStream(out, 1 << 15), false);
    }

    public FastIO(String inputFile) throws IOException {
        this(new FileInputStream(inputFile), System.out);
    }

    public FastIO(String inputFile, String outputFile) throws IOException {
        this(new FileInputStream(inputFile), new FileOutputStream(outputFile));
    }

    // Input.
    public String next() {
        while (st == null || !st.hasMoreTokens()) {
            String line = nextLine();
            if (line == null) throw new NoSuchElementException("no more input");
            st = new StringTokenizer(line);
        }
        return st.nextToken();
    }

    public String nextLine() {
        try {
            return br.readLine();
        } catch (IOException e) {
            throw new UncheckedIOException("failed to read input", e);
        }
    }

    public int nextInt()       { return Integer.parseInt(next()); }
    public long nextLong()     { return Long.parseLong(next()); }
    public double nextDouble() { return Double.parseDouble(next()); }
    public char nextChar()     { return next().charAt(0); }

    public int[] nextIntArray(int n) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = nextInt();
        return a;
    }

    public long[] nextLongArray(int n) {
        long[] a = new long[n];
        for (int i = 0; i < n; i++) a[i] = nextLong();
        return a;
    }

    public String[] nextStringArray(int n) {
        String[] a = new String[n];
        for (int i = 0; i < n; i++) a[i] = next();
        return a;
    }

    public int[][] nextInt2DArray(int rows, int cols) {
        int[][] a = new int[rows][cols];
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++) a[i][j] = nextInt();
        return a;
    }

    // Output.
    public void print(Object o)   { pw.print(o); }
    public void println(Object o) { pw.println(o); }
    public void println()         { pw.println(); }
    public void printf(String fmt, Object... args) { pw.printf(fmt, args); }

    public void printArray(int[] a) {
        for (int i = 0; i < a.length; i++) {
            if (i > 0) pw.print(' ');
            pw.print(a[i]);
        }
    }

    public void printArrayln(int[] a) {
        printArray(a);
        pw.println();
    }

    public void printArray(long[] a) {
        for (int i = 0; i < a.length; i++) {
            if (i > 0) pw.print(' ');
            pw.print(a[i]);
        }
    }

    public void printArrayln(long[] a) {
        printArray(a);
        pw.println();
    }

    public void flush() { pw.flush(); }

    // Math utilities.
    public static long gcd(long a, long b) { return b == 0 ? a : gcd(b, a % b); }
    public static long lcm(long a, long b) { return a / gcd(a, b) * b; }

    public static long modPow(long base, long exp, long mod) {
        long r = 1;
        base %= mod;
        while (exp > 0) {
            if ((exp & 1) == 1) r = r * base % mod;
            base = base * base % mod;
            exp >>= 1;
        }
        return r;
    }

    public static long modInverse(long a, long mod) { return modPow(a, mod - 2, mod); }

    public static boolean isPrime(long n) {
        if (n < 2) return false;
        for (long i = 2; i * i <= n; i++)
            if (n % i == 0) return false;
        return true;
    }

    @Override
    public void close() {
        pw.flush();
        pw.close();
        try {
            br.close();
        } catch (IOException ignored) {
        }
    }
}
