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

## Installation

Simply copy the `FastIO.java` file into your project directory.

## Basic Usage

```java
import java.io.*;

public class Solution {
    public static void main(String[] args) {
        FastIO f = new FastIO();
        
        // Reading basic types
        int n = f.nextInt();
        long m = f.nextLong();
        double x = f.nextDouble();
        String s = f.next();
        
        // Reading arrays
        int[] arr = f.nextIntArray(n);
        
        // Reading until end of input
        int[][] matrix = f.readIntMatrix();
        
        // Output
        f.println("Result: " + (n + m));
        System.out.println(Arrays.toString(arr));

        f.close();
    }
}
```

## Advanced Usage

### Reading Variable-sized Arrays

```java
// Read an entire line as an array without knowing size
int[] dynamicArray = f.readIntArray();

// Read a 2D matrix without dimensions
int[][] matrix = f.readIntMatrix();
```

### Using with Testing Framework

```java
public static void main(String[] args) {
    if (System.getenv("TEST_MODE") != null && System.getenv("TEST_MODE").equals("true")) {
        System.out.println(Test.testFunction(
            Solution::solve,
            "input.txt", 
            "expected_output.txt"
        ));
    } else {
        solve();
    }
}

public static void solve() {
    FastIO f = new FastIO();
    // Your solution code here
    f.close();
}
```

## Performance Considerations

- FastIO uses asynchronous input reading to minimize wait times
- Buffer sizes are optimized for USACO problem constraints
- Memory usage is carefully balanced for performance

## Single File Submissions

Many competitive programming platforms, including USACO, only accept a single file submission. You can incorporate FastIO directly into your solution file:

1. Copy the entire FastIO class into your solution file
2. Make sure it's placed outside of your main class
3. Remove any import statements that are already in your solution file

See `Example.java` for a demonstration of how to include FastIO in a single file solution. This approach ensures your solution is self-contained and can be submitted to platforms that restrict submissions to a single file.

## Debugging

You can use `FastIO.debug` to debug your code as System.out.println does not print when using `testFunction`

## License

See the [LICENSE](LICENSE) file for details.

## Contributing

Contributions are welcome! Please feel free to submit a pull request.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request
