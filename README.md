# FastIO

Buffered Java input/output for competitive programming (USACO, Codeforces, and similar).

Java's `Scanner` is slow for large inputs. FastIO reads through a buffered
`BufferedReader` with `StringTokenizer` and writes through a buffered
`PrintWriter`, which is much faster on big test cases. It is a single class, so
you can paste it into a one-file contest submission.

Use it when contest input is large enough that `Scanner` risks a time-limit
exceeded, or when you just want a small, predictable I/O helper.

## Quick start

Requires Java 8 or newer.

```bash
git clone https://github.com/FizzWizZleDazzle/FastIO.git
cd FastIO
./fastio check          # fastio.bat check on Windows
```

Create a solution from the template, then compile and run it:

```bash
./fastio new MyProblem
./fastio run MyProblem
```

`new` copies `templates/Solution.java` (FastIO embedded) to `MyProblem.java`.
Write your logic in `solve()`.

## API

```java
try (FastIO f = new FastIO()) {
    int n = f.nextInt();
    int[] a = f.nextIntArray(n);
    long sum = 0;
    for (int x : a) sum += x;
    f.println(sum);
}
```

Input: `next`, `nextLine`, `nextInt`, `nextLong`, `nextDouble`, `nextChar`,
`nextIntArray(n)`, `nextLongArray(n)`, `nextStringArray(n)`, `nextInt2DArray(rows, cols)`.

Output: `print`, `println`, `printf`, `printArray`, `printArrayln`, `flush`.

Math: `gcd`, `lcm`, `modPow`, `modInverse`, `isPrime`.

Constants: `MOD`, `MOD2`, `INF`, `LINF`, `EPS`.

Close the `FastIO` (or use try-with-resources) so buffered output is flushed.

## Two ways to use it

- Single file: start from `templates/Solution.java`, which embeds FastIO. This
  is what you submit to a judge that wants one file.
- As a library: compile against `src/FastIO.java`, for example
  `javac -cp src examples/Example.java`.

## Layout

```
src/FastIO.java          the library, one class
templates/Solution.java  single-file submission template
examples/Example.java    read n and an array, print sum/min/max
utils/FastIOUtil.java     cross-platform helper behind the fastio scripts
fastio, fastio.bat       new/compile/run/test wrappers (Unix, Windows)
docs/GETTING_STARTED.md  beginner walkthrough
```

## Commands

| Command | Does |
|---------|------|
| `fastio check` | Verify the setup compiles |
| `fastio new <Name>` | Create `<Name>.java` from the template |
| `fastio compile <Name>` | Compile `<Name>.java` |
| `fastio run <Name>` | Compile and run |
| `fastio test <Name> in.txt out.txt` | Run against input, compare to expected |

Use `fastio.bat <command>` on Windows. Both wrap `utils/FastIOUtil.java`, so
`java FastIOUtil <command>` from `utils/` works on any platform.

## License

MIT. See [LICENSE](LICENSE).
