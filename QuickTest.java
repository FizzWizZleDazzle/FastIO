import java.io.*;

public class QuickTest {
    public static void main(String[] args) {
        System.out.println("Testing FastIO compilation and basic usage...");
        
        // Simple test with string input
        String input = "5\n1 2 3 4 5\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        
        try (FastIO f = new FastIO(in, out)) {
            int n = f.nextInt();
            System.out.println("Read n = " + n);
            
            int[] arr = f.nextIntArray(n);
            System.out.println("Read array: " + java.util.Arrays.toString(arr));
            
            f.println("Sum = " + java.util.Arrays.stream(arr).sum());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("FastIO basic functionality works!");
        
        // Test math utilities
        System.out.println("GCD(12, 8) = " + FastIO.gcd(12, 8));
        System.out.println("LCM(4, 6) = " + FastIO.lcm(4, 6));
        System.out.println("isPowerOfTwo(8) = " + FastIO.isPowerOfTwo(8));
        
        System.out.println("All tests completed successfully!");
    }
}