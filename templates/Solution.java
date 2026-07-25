import java.io.*;
import java.util.*;

/**
 * Single-file USACO template. FastIO is embedded so this compiles and submits
 * on its own. Write your solution in solve().
 */
public class Solution {

    public static void main(String[] args) {
        solve();
    }

    public static void solve() {
        try (FastIO f = new FastIO()) {
            // Example: read n and n integers, print their sum.
            int n = f.nextInt();
            int[] a = f.nextIntArray(n);
            long sum = 0;
            for (int x : a) sum += x;
            f.println(sum);
        }
    }

    static class FastIO implements AutoCloseable {
        private final BufferedReader br;
        private StringTokenizer st;
        private final PrintWriter pw;

        public static final int INF = (int) 1e9;
        public static final long LINF = (long) 1e18;
        public static final double EPS = 1e-9;
        public static final int MOD = 1_000_000_007;

        FastIO() {
            this(System.in, System.out);
        }

        FastIO(InputStream in, OutputStream out) {
            this.br = new BufferedReader(new InputStreamReader(in), 1 << 15);
            this.pw = new PrintWriter(new BufferedOutputStream(out, 1 << 15), false);
        }

        String next() {
            while (st == null || !st.hasMoreTokens()) {
                String line = nextLine();
                if (line == null) throw new NoSuchElementException("no more input");
                st = new StringTokenizer(line);
            }
            return st.nextToken();
        }

        String nextLine() {
            try {
                return br.readLine();
            } catch (IOException e) {
                throw new UncheckedIOException("failed to read input", e);
            }
        }

        int nextInt()       { return Integer.parseInt(next()); }
        long nextLong()     { return Long.parseLong(next()); }
        double nextDouble() { return Double.parseDouble(next()); }

        int[] nextIntArray(int n) {
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = nextInt();
            return a;
        }

        long[] nextLongArray(int n) {
            long[] a = new long[n];
            for (int i = 0; i < n; i++) a[i] = nextLong();
            return a;
        }

        void print(Object o)   { pw.print(o); }
        void println(Object o) { pw.println(o); }
        void println()         { pw.println(); }

        static long gcd(long a, long b) { return b == 0 ? a : gcd(b, a % b); }
        static long lcm(long a, long b) { return a / gcd(a, b) * b; }

        static long modPow(long base, long exp, long mod) {
            long r = 1;
            base %= mod;
            while (exp > 0) {
                if ((exp & 1) == 1) r = r * base % mod;
                base = base * base % mod;
                exp >>= 1;
            }
            return r;
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
}
