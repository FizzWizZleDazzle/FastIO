import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.*;

/**
 * DirectByteBuffer-based FastIO implementation for performance evaluation
 * This class uses NIO DirectByteBuffer for potentially faster I/O operations
 * compared to the traditional BufferedReader/PrintWriter approach.
 */
public class FastIONIO implements AutoCloseable {
    // Buffer sizes - using same size as original FastIO for fair comparison
    private static final int INPUT_BUFFER_SIZE = 65536;
    private static final int OUTPUT_BUFFER_SIZE = 65536;
    
    // Input components
    private final ReadableByteChannel inputChannel;
    private final ByteBuffer inputBuffer;
    private boolean inputEOF = false;
    
    // Output components  
    private final WritableByteChannel outputChannel;
    private final ByteBuffer outputBuffer;
    
    // Constants for competitive programming
    public static final int INF = (int) 1e9;
    public static final long LINF = (long) 1e18;
    public static final double EPS = 1e-9;
    public static final int MOD = 1000000007;
    public static final int MOD2 = 998244353;
    
    // Constructors
    public FastIONIO() {
        this(System.in, System.out);
    }
    
    public FastIONIO(InputStream in, OutputStream out) {
        // Wrap streams in channels
        this.inputChannel = Channels.newChannel(in);
        this.outputChannel = Channels.newChannel(out);
        
        // Allocate direct byte buffers for off-heap memory
        this.inputBuffer = ByteBuffer.allocateDirect(INPUT_BUFFER_SIZE);
        this.outputBuffer = ByteBuffer.allocateDirect(OUTPUT_BUFFER_SIZE);
        
        // Start with empty input buffer
        inputBuffer.limit(0);
    }
    
    public FastIONIO(String inputFile) throws IOException {
        this(new FileInputStream(inputFile), System.out);
    }
    
    public FastIONIO(String inputFile, String outputFile) throws IOException {
        this(new FileInputStream(inputFile), new FileOutputStream(outputFile));
    }
    
    /**
     * Ensure we have at least one byte available in the input buffer
     */
    private void ensureInput() throws IOException {
        if (inputBuffer.remaining() == 0 && !inputEOF) {
            fillInputBuffer();
        }
    }
    
    /**
     * Fill the input buffer from the channel
     */
    private void fillInputBuffer() throws IOException {
        inputBuffer.clear();
        int bytesRead = inputChannel.read(inputBuffer);
        if (bytesRead == -1) {
            inputEOF = true;
            inputBuffer.limit(0);
        } else {
            inputBuffer.flip();
        }
    }
    
    /**
     * Read the next byte from input, -1 if EOF
     */
    private int readByte() throws IOException {
        ensureInput();
        if (inputBuffer.remaining() == 0) {
            return -1;
        }
        return inputBuffer.get() & 0xFF;
    }
    
    /**
     * Skip whitespace characters
     */
    private void skipWhitespace() throws IOException {
        int b;
        while ((b = readByte()) != -1 && (b <= 32)) {
            // Skip whitespace (space, tab, newline, etc.)
        }
        if (b != -1) {
            // Put back the non-whitespace byte
            inputBuffer.position(inputBuffer.position() - 1);
        }
    }
    
    /**
     * Read next token (sequence of non-whitespace characters)
     */
    public String next() {
        try {
            skipWhitespace();
            StringBuilder sb = new StringBuilder();
            int b;
            while ((b = readByte()) != -1 && b > 32) {
                sb.append((char) b);
            }
            if (sb.length() == 0) {
                throw new NoSuchElementException("No more input available");
            }
            return sb.toString();
        } catch (IOException e) {
            throw new UncheckedIOException("Error reading next token", e);
        }
    }
    
    /**
     * Read next line
     */
    public String nextLine() {
        try {
            StringBuilder sb = new StringBuilder();
            int b;
            while ((b = readByte()) != -1) {
                if (b == '\n') {
                    break;
                } else if (b != '\r') {
                    sb.append((char) b);
                }
            }
            return sb.toString();
        } catch (IOException e) {
            throw new UncheckedIOException("Error reading next line", e);
        }
    }
    
    /**
     * Read next integer with optimized byte-level parsing
     */
    public int nextInt() {
        try {
            skipWhitespace();
            int result = 0;
            boolean negative = false;
            int b = readByte();
            
            if (b == -1) {
                throw new NoSuchElementException("No more input available");
            }
            
            if (b == '-') {
                negative = true;
                b = readByte();
            }
            
            if (b < '0' || b > '9') {
                throw new NumberFormatException("Invalid integer format");
            }
            
            do {
                result = result * 10 + (b - '0');
                b = readByte();
            } while (b >= '0' && b <= '9');
            
            // Put back the non-digit byte
            if (b != -1) {
                inputBuffer.position(inputBuffer.position() - 1);
            }
            
            return negative ? -result : result;
        } catch (IOException e) {
            throw new UncheckedIOException("Error reading integer", e);
        }
    }
    
    /**
     * Read next long with optimized byte-level parsing
     */
    public long nextLong() {
        try {
            skipWhitespace();
            long result = 0;
            boolean negative = false;
            int b = readByte();
            
            if (b == -1) {
                throw new NoSuchElementException("No more input available");
            }
            
            if (b == '-') {
                negative = true;
                b = readByte();
            }
            
            if (b < '0' || b > '9') {
                throw new NumberFormatException("Invalid long format");
            }
            
            do {
                result = result * 10 + (b - '0');
                b = readByte();
            } while (b >= '0' && b <= '9');
            
            // Put back the non-digit byte
            if (b != -1) {
                inputBuffer.position(inputBuffer.position() - 1);
            }
            
            return negative ? -result : result;
        } catch (IOException e) {
            throw new UncheckedIOException("Error reading long", e);
        }
    }
    
    /**
     * Read next double
     */
    public double nextDouble() {
        return Double.parseDouble(next());
    }
    
    /**
     * Read next character
     */
    public char nextChar() {
        return next().charAt(0);
    }
    
    // Array reading methods
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
    
    // Output methods with direct byte buffer
    
    /**
     * Ensure output buffer has space for at least n bytes
     */
    private void ensureOutputSpace(int n) throws IOException {
        if (outputBuffer.remaining() < n) {
            flush();
        }
    }
    
    /**
     * Write a byte to output buffer
     */
    private void writeByte(byte b) throws IOException {
        ensureOutputSpace(1);
        outputBuffer.put(b);
    }
    
    /**
     * Write bytes to output buffer
     */
    private void writeBytes(byte[] bytes) throws IOException {
        int offset = 0;
        while (offset < bytes.length) {
            int available = outputBuffer.remaining();
            if (available == 0) {
                flush();
                available = outputBuffer.remaining();
            }
            int toWrite = Math.min(available, bytes.length - offset);
            outputBuffer.put(bytes, offset, toWrite);
            offset += toWrite;
        }
    }
    
    /**
     * Print object without newline
     */
    public void print(Object obj) {
        try {
            writeBytes(obj.toString().getBytes());
        } catch (IOException e) {
            throw new UncheckedIOException("Error writing output", e);
        }
    }
    
    /**
     * Print object with newline
     */
    public void println(Object obj) {
        try {
            writeBytes(obj.toString().getBytes());
            writeByte((byte) '\n');
        } catch (IOException e) {
            throw new UncheckedIOException("Error writing output", e);
        }
    }
    
    /**
     * Print newline only
     */
    public void println() {
        try {
            writeByte((byte) '\n');
        } catch (IOException e) {
            throw new UncheckedIOException("Error writing output", e);
        }
    }
    
    /**
     * Print formatted output
     */
    public void printf(String format, Object... args) {
        print(String.format(format, args));
    }
    
    /**
     * Print integer array with space separation
     */
    public void printArray(int[] arr) {
        if (arr == null) return;
        for (int i = 0; i < arr.length; i++) {
            if (i > 0) print(" ");
            print(arr[i]);
        }
        println();
    }
    
    /**
     * Print long array with space separation
     */
    public void printArray(long[] arr) {
        if (arr == null) return;
        for (int i = 0; i < arr.length; i++) {
            if (i > 0) print(" ");
            print(arr[i]);
        }
        println();
    }
    
    /**
     * Flush output buffer to channel
     */
    public void flush() {
        try {
            outputBuffer.flip();
            while (outputBuffer.hasRemaining()) {
                outputChannel.write(outputBuffer);
            }
            outputBuffer.clear();
        } catch (IOException e) {
            throw new UncheckedIOException("Error flushing output", e);
        }
    }
    
    /**
     * Check if more input is available
     */
    public boolean hasNext() {
        try {
            ensureInput();
            return inputBuffer.remaining() > 0 && !inputEOF;
        } catch (IOException e) {
            return false;
        }
    }
    
    // Mathematical utility methods (same as original FastIO)
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
    
    @Override
    public void close() {
        try {
            flush();
            if (inputChannel != null) {
                inputChannel.close();
            }
            if (outputChannel != null) {
                outputChannel.close();
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Error closing FastIONIO", e);
        }
    }
}