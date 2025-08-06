# Getting Started with FastIO

Welcome! This guide will get you up and running with FastIO for USACO competitions in just a few minutes.

## 1. Quick Setup Check

```bash
./fastio.sh check
```

If you see all green checkmarks, you're ready to go!

## 2. Your First Problem

Let's solve a simple problem: read an array of numbers and output their sum.

**Step 1:** Create a new solution
```bash
./fastio.sh new ArraySum
```

**Step 2:** Edit `ArraySum.java` and replace the solve() method:
```java
public static void solve() {
    FastIO f = new FastIO();
    
    int n = f.nextInt();           // Read array size
    int[] arr = f.nextIntArray(n); // Read n integers
    
    int sum = 0;
    for (int x : arr) {
        sum += x;
    }
    
    f.println(sum);  // Output the sum
    f.close();
}
```

**Step 3:** Test it
```bash
# Create test input
echo -e "5\n1 2 3 4 5" > input.txt
echo "15" > output.txt

# Run the test
./fastio.sh test ArraySum input.txt output.txt
```

You should see: `Test PASSED | Output: '15'`

## 3. Common Patterns

### Reading Multiple Types
```java
int n = f.nextInt();
long m = f.nextLong();
double x = f.nextDouble();
String s = f.next();
String line = f.nextLine();
```

### Arrays and Matrices
```java
int[] arr = f.nextIntArray(n);                    // Known size
int[] dynamic = f.readIntArray();                 // Unknown size (read whole line)
int[][] matrix = f.nextInt2DArray(rows, cols);    // 2D array
char[][] grid = f.nextChar2DArray(rows);          // Character grid
```

### Output
```java
f.println("Hello World");
f.printArray(arr);        // Print array with spaces
f.print2DArray(matrix);   // Print 2D array
```

### Math Utilities
```java
int g = FastIO.gcd(12, 8);              // 4
long l = FastIO.lcm(4, 6);              // 12
long p = FastIO.modPow(2, 10, 1000);    // 24
boolean isPow2 = FastIO.isPowerOfTwo(8); // true
```

## 4. For USACO Submissions

The `Solution.java` template is perfect for single-file submissions:

1. Copy the entire file content
2. Paste it into the USACO submission box
3. Replace the solve() method with your solution
4. Submit!

## 5. Tips

- Use `f.debug("Variable:", value)` for debugging instead of `System.out.println()`
- The template includes all USACO constants: `FastIO.INF`, `FastIO.MOD`, etc.
- Always call `f.close()` at the end of your solve() method
- Test locally before submitting: `./fastio.sh test YourSolution input.txt output.txt`

## 6. Need Help?

- Check the full README.md for comprehensive documentation
- Run `./fastio.sh help` for script usage
- Look at Example.java for a complete working example

Happy coding! 🎯