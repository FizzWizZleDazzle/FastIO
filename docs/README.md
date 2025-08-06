# FastIO

A high-performance Input/Output system optimized for USACO (USA Computing Olympiad) competitions in Java.

## Features

- **Asynchronous I/O**: Prefetches input lines for maximum performance
- **Variable-size Arrays**: Read arrays without knowing their size in advance
- **USACO-specific Utilities**: Constants and helper methods designed for competitive programming
- **Memory Optimization**: Efficient buffer sizes and data structures
- **Thread Safety**: Robust concurrent operation support
- **Error Recovery**: Graceful handling of I/O exceptions with detailed diagnostics
- **Testing Framework**: Built-in utilities for validating solutions against test cases

## Quick Start

### Option 1: Use the Ready-to-Go Template (Recommended)

1. **Copy the template**:
   ```bash
   cp Solution.java MyProblem.java
   ```

2. **Edit your solution**:
   ```java
   public static void solve() {
       FastIO f = new FastIO();
       
       // Your solution here
       int n = f.nextInt();
       int[] arr = f.nextIntArray(n);
       f.println(Arrays.stream(arr).sum());
       
       f.close();
   }
   ```

3. **Compile and run**:
   ```bash
   # Using the utility script
   ./fastio.sh run MyProblem
   
   # Or manually
   javac MyProblem.java && java MyProblem
   ```

### Option 2: Include FastIO in Your Existing Code

Simply copy the `FastIO.java` file into your project directory and import it:

```java
import java.io.*;

public class MyProblem {
    public static void main(String[] args) {
        FastIO f = new FastIO();
        
        // Your solution here
        int n = f.nextInt();
        int[] arr = f.nextIntArray(n);
        f.println(Arrays.stream(arr).sum());
        
        f.close();
    }
}
```

## Utility Script

We provide a handy script to make development easier:

```bash
# Check if everything is set up correctly
./fastio.sh check

# Create a new problem from template
./fastio.sh new Problem1

# Compile and run
./fastio.sh run Problem1

# Test with input/output files
./fastio.sh test Problem1 input.txt expected_output.txt
```

## Basic Usage Examples

### Reading Different Data Types
```java
FastIO f = new FastIO();

// Basic types
int n = f.nextInt();
long m = f.nextLong();
double x = f.nextDouble();
String s = f.next();
String line = f.nextLine();

// Arrays with known size
int[] arr = f.nextIntArray(n);
long[] largeArr = f.nextLongArray(n);
String[] words = f.nextStringArray(n);

// Arrays with unknown size (reads entire line)
int[] dynamicArray = f.readIntArray();

// 2D arrays
int[][] matrix = f.nextInt2DArray(rows, cols);
char[][] grid = f.nextChar2DArray(rows);

f.close();
```

### Output Operations
```java
FastIO f = new FastIO();

// Basic output
f.println("Hello World");
f.print(42);
f.printf("%.2f\n", 3.14159);

// Array output
int[] arr = {1, 2, 3, 4, 5};
f.printArray(arr);        // "1 2 3 4 5"
f.printArray(arr, ", ");  // "1, 2, 3, 4, 5"

// 2D array output
f.print2DArray(matrix);

f.close();
```

### Mathematical Utilities
```java
// Common competitive programming functions
int g = FastIO.gcd(12, 8);              // 4
long l = FastIO.lcm(4, 6);              // 12
long p = FastIO.modPow(2, 10, 1000);    // 24
boolean isPow2 = FastIO.isPowerOfTwo(8); // true

// Constants
int maxInt = FastIO.INF;        // 10^9
long maxLong = FastIO.LINF;     // 10^18
int mod = FastIO.MOD;           // 1000000007
```

## Testing Your Solutions

### Using the Built-in Test Framework

```java
public static void main(String[] args) {
    if (System.getenv("TEST_MODE") != null && System.getenv("TEST_MODE").equals("true")) {
        System.out.println(Test.testFunction(
            Solution::solve,
            "5\n1 2 3 4 5",  // Input
            "15"             // Expected output
        ));
    } else {
        solve();
    }
}
```

Run with testing:
```bash
TEST_MODE=true java Solution
```

### Using Input/Output Files

```bash
# Create test files
echo "5\n1 2 3 4 5" > input.txt
echo "15" > expected_output.txt

# Test using the script
./fastio.sh test Solution input.txt expected_output.txt
```

## Single File Submissions

Many competitive programming platforms, including USACO, only accept single file submissions. The `Solution.java` template is designed for this:

1. **Everything is self-contained** - No external dependencies
2. **Embedded FastIO class** - All functionality included
3. **Ready for copy-paste** - Just copy the entire file content

The template includes a simplified FastIO class with all essential features while keeping the file size manageable for submissions.

## Installation

### Requirements
- Java 8 or higher
- Java Development Kit (JDK) with `javac` compiler

### Setup
1. Clone or download this repository
2. Make sure Java is installed: `javac -version`
3. Run the setup check: `./fastio.sh check`
4. Start coding with: `cp Solution.java YourProblem.java`

### IDE Setup (Optional)

For competitive programming, you might want to:

1. **Set up a template** in your IDE pointing to `Solution.java`
2. **Configure compiler settings** to use Java 8+ compliance
3. **Set up run configurations** with proper classpath
4. **Install competitive programming plugins** if available

## Performance Considerations

- FastIO uses optimized buffer sizes for USACO problem constraints  
- Asynchronous input reading minimizes wait times
- Memory usage is carefully balanced for performance
- Mathematical utilities use efficient algorithms suitable for competitive programming

## Debugging

Use `FastIO.debug()` for debugging output that works with the testing framework:

```java
f.debug("Variable x =", x, "array =", Arrays.toString(arr));
```

This is better than `System.out.println()` which may not work properly with the test framework.

## Common Patterns for USACO

### Graph Input
```java
FastIO f = new FastIO();
int n = f.nextInt(); // nodes
int m = f.nextInt(); // edges

List<List<Integer>> adj = new ArrayList<>();
for (int i = 0; i <= n; i++) {
    adj.add(new ArrayList<>());
}

for (int i = 0; i < m; i++) {
    int u = f.nextInt();
    int v = f.nextInt();
    adj.get(u).add(v);
    adj.get(v).add(u);
}
```

### Grid Input
```java
FastIO f = new FastIO();
int n = f.nextInt();
int m = f.nextInt();
char[][] grid = f.nextChar2DArray(n);
```

### Multiple Test Cases
```java
FastIO f = new FastIO();
int t = f.nextInt();
while (t-- > 0) {
    // Solve each test case
    int n = f.nextInt();
    // ... solution code
    f.println(result);
}
f.close();
```

## License

See the [LICENSE](LICENSE) file for details.

## Contributing

Contributions are welcome! Please feel free to submit a pull request.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request
