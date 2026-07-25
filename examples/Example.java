import java.util.*;

/**
 * Basic FastIO example: read n and n integers, print their sum, min, and max.
 * Compile against the library: javac -cp ../src Example.java
 */
public class Example {

    public static void main(String[] args) {
        try (FastIO f = new FastIO()) {
            int n = f.nextInt();
            int[] a = f.nextIntArray(n);

            long sum = 0;
            int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
            for (int x : a) {
                sum += x;
                min = Math.min(min, x);
                max = Math.max(max, x);
            }

            f.println(sum);
            f.println(min + " " + max);
        }
    }
}
