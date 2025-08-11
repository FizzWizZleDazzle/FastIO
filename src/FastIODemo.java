import java.io.*;

/**
 * Demonstration of FastIO vs FastIONIO usage and compatibility
 * This example shows how both implementations can be used interchangeably
 * for basic competitive programming patterns.
 */
public class FastIODemo {
    
    public static void main(String[] args) {
        System.out.println("FastIO vs FastIONIO Demo");
        System.out.println("========================");
        
        try {
            // Create sample input file
            createSampleInput();
            
            // Test original FastIO
            System.out.println("Testing Original FastIO:");
            testOriginalFastIO();
            
            // Test FastIONIO
            System.out.println("\nTesting FastIONIO:");
            testFastIONIO();
            
            // Compare outputs
            System.out.println("\nOutput Comparison:");
            compareOutputs();
            
            // Clean up
            cleanup();
            
        } catch (Exception e) {
            System.err.println("Demo failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void createSampleInput() throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter("demo_input.txt"))) {
            // Sample competitive programming input
            pw.println("5");  // Array size
            pw.println("1 2 3 4 5");  // Array elements
            pw.println("Hello World");  // String input
            pw.println("42");  // Another integer
            pw.println("1000000000000");  // Large long
        }
        System.out.println("Created sample input file");
    }
    
    private static void testOriginalFastIO() throws IOException {
        try (FastIO f = new FastIO("demo_input.txt", "output_original.txt")) {
            // Read array
            int n = f.nextInt();
            int[] arr = f.nextIntArray(n);
            
            // Read string
            String str = f.next();
            String word = f.next();
            
            // Read integer and long
            int num = f.nextInt();
            long bigNum = f.nextLong();
            
            // Write results
            f.println("Array size: " + n);
            f.printArray(arr);
            f.println("String: " + str + " " + word);
            f.println("Number: " + num);
            f.println("Big number: " + bigNum);
            f.println("Sum of array: " + sum(arr));
        }
        System.out.println("  ✓ Original FastIO completed");
    }
    
    private static void testFastIONIO() throws IOException {
        try (FastIONIO f = new FastIONIO("demo_input.txt", "output_nio.txt")) {
            // Read array
            int n = f.nextInt();
            int[] arr = f.nextIntArray(n);
            
            // Read string
            String str = f.next();
            String word = f.next();
            
            // Read integer and long
            int num = f.nextInt();
            long bigNum = f.nextLong();
            
            // Write results
            f.println("Array size: " + n);
            f.printArray(arr);
            f.println("String: " + str + " " + word);
            f.println("Number: " + num);
            f.println("Big number: " + bigNum);
            f.println("Sum of array: " + sum(arr));
        }
        System.out.println("  ✓ FastIONIO completed");
    }
    
    private static void compareOutputs() throws IOException {
        String original = readFile("output_original.txt");
        String nio = readFile("output_nio.txt");
        
        if (original.equals(nio)) {
            System.out.println("  ✓ Outputs are identical - APIs are compatible!");
        } else {
            System.out.println("  ⚠ Outputs differ:");
            System.out.println("Original:\n" + original);
            System.out.println("NIO:\n" + nio);
        }
    }
    
    private static String readFile(String filename) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
        }
        return sb.toString();
    }
    
    private static int sum(int[] arr) {
        int sum = 0;
        for (int x : arr) {
            sum += x;
        }
        return sum;
    }
    
    private static void cleanup() {
        new File("demo_input.txt").delete();
        new File("output_original.txt").delete();
        new File("output_nio.txt").delete();
        System.out.println("Cleaned up temporary files");
    }
}