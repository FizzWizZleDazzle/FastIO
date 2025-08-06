import java.io.*;

public class SimpleTest {
    public static void main(String[] args) {
        // Test basic FastIO functionality
        testBasicIO();
        testArrayReading();
        testMathUtilities();
        System.out.println("All tests passed!");
    }
    
    private static void testBasicIO() {
        String input = "42 3.14 hello\n1 2 3 4 5\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        
        FastIO f = new FastIO(in, out);
        
        int n = f.nextInt();
        double d = f.nextDouble();
        String s = f.next();
        
        f.println("n=" + n + " d=" + d + " s=" + s);
        
        int[] arr = f.readIntArray();
        f.printArray(arr);
        
        f.close();
        
        String output = out.toString();
        assert output.contains("n=42 d=3.14 s=hello");
        assert output.contains("1 2 3 4 5");
        
        System.out.println("✓ Basic I/O test passed");
    }
    
    private static void testArrayReading() {
        System.out.println("Starting array reading test...");
        String input = "1 2 3\n4 5 6\n7 8 9\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        
        FastIO f = new FastIO(in, System.out);
        
        try {
            int[] arr1 = f.readIntArray();
            System.out.println("Read arr1: " + java.util.Arrays.toString(arr1));
            
            int[] arr2 = f.readIntArray();
            System.out.println("Read arr2: " + java.util.Arrays.toString(arr2));
            
            int[] arr3 = f.readIntArray();
            System.out.println("Read arr3: " + java.util.Arrays.toString(arr3));
            
            assert arr1.length == 3 && arr1[0] == 1 && arr1[2] == 3;
            assert arr2.length == 3 && arr2[0] == 4 && arr2[2] == 6;
            assert arr3.length == 3 && arr3[0] == 7 && arr3[2] == 9;
        } catch (Exception e) {
            System.out.println("Exception in array reading: " + e.getMessage());
            e.printStackTrace();
        } finally {
            f.close();
        }
        
        System.out.println("✓ Array reading test passed");
    }
    
    private static void testMathUtilities() {
        assert FastIO.gcd(12, 8) == 4;
        assert FastIO.lcm(4, 6) == 12;
        assert FastIO.modPow(2, 10, 1000) == 24;
        assert FastIO.isPowerOfTwo(8) == true;
        assert FastIO.isPowerOfTwo(10) == false;
        assert FastIO.log2(8) == 3;
        
        System.out.println("✓ Math utilities test passed");
    }
}