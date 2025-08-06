# Getting Started with FastIO

Welcome to FastIO! This guide will help you quickly set up and start using FastIO for USACO competitive programming on any platform.

## What is FastIO?

FastIO is a cross-platform, high-performance Java I/O library specifically designed for competitive programming contests like USACO. It provides lightning-fast input/output operations that are significantly faster than Java's built-in Scanner class.

## Why Use FastIO?

1. **Speed**: FastIO can be 5-6x faster than Scanner for reading large inputs
2. **Cross-Platform**: Works seamlessly on Windows, macOS, and Linux
3. **Well-Organized**: Clean project structure with templates, examples, and utilities
4. **Simplicity**: Clean, intuitive API that's easy to learn and use
5. **USACO-Optimized**: Built specifically for competitive programming patterns
6. **Single-File Ready**: Perfect for contests that require single-file submissions

## Installation & Setup

### Step 1: Download FastIO
```bash
git clone https://github.com/FizzWizZleDazzle/FastIO.git
cd FastIO
```

### Step 2: Verify Installation

**On Windows:**
```cmd
fastio.bat check
```

**On Unix/Linux/macOS:**
```bash
./fastio check
```

If you see all green checkmarks (✓), you're ready to go!

## Your First Problem

Let's solve a simple problem: read an array of numbers and output their sum.

### Step 1: Create a new solution

**Windows:**
```cmd
fastio.bat new ArraySum
```

**Unix/Linux/macOS:**
```bash
./fastio new ArraySum
```

This creates `ArraySum.java` from the template.

### Step 2: Edit ArraySum.java

Replace the `solve()` method with:
```java
public static void solve() {
    FastIO f = new FastIO();
    
    int n = f.nextInt();           // Read array size
    int[] arr = f.nextIntArray(n); // Read n integers
    
    int sum = 0;
    for (int x : arr) {
        sum += x;
    }
    
    f.println(sum);                // Output the sum
    f.close();
}
```

### Step 3: Test your solution

**Windows:**
```cmd
fastio.bat run ArraySum
```

**Unix/Linux/macOS:**
```bash
./fastio run ArraySum
```

## Understanding the Project Structure

After installation, your FastIO directory looks like this:

```
FastIO/
├── src/                    # Core library files
│   └── FastIO.java        # The main FastIO class
├── templates/             # Solution templates
│   └── Solution.java      # Default template for new problems
├── examples/              # Example solutions and test files
│   ├── Example.java       # Basic usage example
│   ├── Demo.java          # Feature demonstration
│   ├── sample_input.txt   # Test input
│   └── sample_output.txt  # Expected output
├── utils/                 # Cross-platform utilities
│   └── FastIOUtil.java    # Java-based utility system
├── docs/                  # Documentation
├── fastio                 # Unix utility script
├── fastio.bat            # Windows utility script
└── README.md             # Complete documentation
```

## Key Concepts

### 1. Template-Based Development

FastIO uses a template system. When you run `fastio new MyProblem`, it creates a new file based on `templates/Solution.java`:

```java
import java.util.*;
import java.io.*;

public class MyProblem {
    public static void main(String[] args) {
        solve();
    }
    
    public static void solve() {
        FastIO f = new FastIO();
        
        // Your solution goes here
        
        f.close();
    }
    
    // FastIO class is embedded here for single-file submission
    static class FastIO {
        // ... (complete FastIO implementation)
    }
}
```

### 2. Cross-Platform Commands

| Task | Windows | Unix/Linux/macOS |
|------|---------|------------------|
| Check setup | `fastio.bat check` | `./fastio check` |
| New problem | `fastio.bat new Problem1` | `./fastio new Problem1` |
| Compile | `fastio.bat compile Problem1` | `./fastio compile Problem1` |
| Run | `fastio.bat run Problem1` | `./fastio run Problem1` |
| Test | `fastio.bat test Problem1 in.txt out.txt` | `./fastio test Problem1 in.txt out.txt` |

### 3. Common Input Patterns

**Reading single values:**
```java
int n = f.nextInt();
long l = f.nextLong();
double d = f.nextDouble();
String s = f.next();        // single word
String line = f.nextLine(); // entire line
```

**Reading arrays:**
```java
int n = f.nextInt();
int[] arr = f.nextIntArray(n);
long[] larr = f.nextLongArray(n);
String[] sarr = f.nextStringArray(n);
```

**Reading multiple test cases:**
```java
int t = f.nextInt();
while (t-- > 0) {
    // solve each test case
    int n = f.nextInt();
    // ... solution logic
    f.println(answer);
}
```

### 4. Output Methods

```java
f.print(value);              // no newline
f.println(value);            // with newline
f.printf("%.2f", value);     // formatted output
f.printArray(arr);           // space-separated array
f.printArrayln(arr);         // array with newline
```

## Testing Your Solutions

### Manual Testing

Create test files and run:

**Windows:**
```cmd
echo 5 > input.txt
echo 1 2 3 4 5 >> input.txt
echo 15 > expected.txt
fastio.bat test ArraySum input.txt expected.txt
```

**Unix/Linux/macOS:**
```bash
echo "5" > input.txt
echo "1 2 3 4 5" >> input.txt
echo "15" > expected.txt
./fastio test ArraySum input.txt expected.txt
```

### Using Sample Files

The repository includes sample test files in `examples/`:
```bash
# Test the default template
fastio.bat test Solution examples/sample_input.txt examples/sample_output.txt
```

## Advanced Features

### Mathematical Utilities

FastIO includes built-in mathematical functions:

```java
long g = FastIO.gcd(12, 18);              // GCD
long l = FastIO.lcm(12, 18);              // LCM
long p = FastIO.modPow(2, 10, 1000000007); // Modular exponentiation
boolean prime = FastIO.isPrime(17);        // Primality test
```

### Constants

```java
FastIO.MOD = 1000000007;    // Common modulus
FastIO.INF = Integer.MAX_VALUE; // Infinity
FastIO.EPS = 1e-9;         // Small epsilon for double comparisons
```

## Tips for USACO Success

1. **Always close FastIO**: Call `f.close()` at the end of your solution
2. **Use templates**: Start with `fastio new ProblemName` for consistent structure
3. **Test thoroughly**: Use the testing framework to validate your solutions
4. **Single-file submissions**: The template creates self-contained files perfect for USACO
5. **Practice patterns**: Get comfortable with array reading and multiple test cases

## Common Troubleshooting

### "Java not found"
- Install Java JDK 8 or higher
- Add Java to your system PATH

### "Permission denied" (Unix/Linux/macOS)
```bash
chmod +x fastio
```

### "FastIOUtil compilation failed"
- Make sure you're in the FastIO directory
- Check that `utils/FastIOUtil.java` exists

### Scripts not working on Windows
- Use `fastio.bat` instead of `fastio`
- Make sure you're using Command Prompt or PowerShell

## Next Steps

1. **Read the full documentation**: Check out `README.md` for complete API reference
2. **Explore examples**: Look at files in the `examples/` directory
3. **Practice**: Try solving some USACO Bronze problems using FastIO
4. **Customize**: Modify the template in `templates/Solution.java` to match your style

## Getting Help

- **Read the docs**: `README.md` has complete API documentation
- **Check examples**: `examples/` directory has working code samples
- **Test setup**: Run `fastio check` to verify everything is working

---

**You're all set! Start solving problems with lightning-fast I/O! ⚡**