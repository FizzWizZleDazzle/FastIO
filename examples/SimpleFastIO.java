import java.io.*;
import java.util.*;

/**
 * Simplified FastIO for testing manual parsing optimizations
 */
public class SimpleFastIO implements AutoCloseable {
    private final BufferedReader br;
    private StringTokenizer st;
    private final PrintWriter pw;
    
    public SimpleFastIO() {
        this(System.in, System.out);
    }
    
    public SimpleFastIO(InputStream in, OutputStream out) {
        this.br = new BufferedReader(new InputStreamReader(in), 524288);
        this.pw = new PrintWriter(new BufferedOutputStream(out, 262144), false);
    }
    
    public SimpleFastIO(String inputFile) throws IOException {
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
                throw new RuntimeException("Failed to read next token", e);
            }
        }
        return st.nextToken();
    }
    
    public String nextLine() throws IOException {
        return br.readLine();
    }
    
    // Manual parsing for better performance
    public int nextInt() {
        String token = next();
        return parseIntFast(token);
    }
    
    public long nextLong() {
        String token = next();
        return parseLongFast(token);
    }
    
    // Fast integer parsing - avoids Integer.parseInt overhead
    private static int parseIntFast(String s) {
        if (s == null || s.isEmpty()) {
            throw new NumberFormatException("Cannot parse empty string");
        }
        
        int result = 0;
        boolean negative = false;
        int start = 0;
        
        if (s.charAt(0) == '-') {
            negative = true;
            start = 1;
        } else if (s.charAt(0) == '+') {
            start = 1;
        }
        
        for (int i = start; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c < '0' || c > '9') {
                throw new NumberFormatException("Invalid character in number: " + c);
            }
            result = result * 10 + (c - '0');
        }
        
        return negative ? -result : result;
    }
    
    // Fast long parsing - avoids Long.parseLong overhead
    private static long parseLongFast(String s) {
        if (s == null || s.isEmpty()) {
            throw new NumberFormatException("Cannot parse empty string");
        }
        
        long result = 0;
        boolean negative = false;
        int start = 0;
        
        if (s.charAt(0) == '-') {
            negative = true;
            start = 1;
        } else if (s.charAt(0) == '+') {
            start = 1;
        }
        
        for (int i = start; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c < '0' || c > '9') {
                throw new NumberFormatException("Invalid character in number: " + c);
            }
            result = result * 10 + (c - '0');
        }
        
        return negative ? -result : result;
    }
    
    @Override
    public void close() throws IOException {
        br.close();
        pw.close();
    }
}