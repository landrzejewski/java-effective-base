package pl.training.functionalfeatures.solutions;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public final class Mod006ParallelStreamsSolutions {

    private Mod006ParallelStreamsSolutions() {}

    /*
    Exercise 1 — Compute the sum 1 + 2 + ... + n using a PARALLEL stream and an associative reduce
    (so the result is deterministic and equals the sequential sum). Return the sum.
    */
    static long exercise1(long n) {
        return LongStream.rangeClosed(1, n).parallel().reduce(0L, Long::sum);
    }

    /*
    Exercise 2 — Count how many numbers in `input` are even vs odd using a PARALLEL stream and
    Collectors.groupingByConcurrent (with counting). Return a Map<Boolean, Long>
    ({false=oddCount, true=evenCount}).
    */
    static Map<Boolean, Long> exercise2(List<Integer> input) {
        return input.parallelStream().collect(
                Collectors.groupingByConcurrent(n -> n % 2 == 0, Collectors.counting()));
    }

    /*
    Exercise 3 — A buggy version counted elements greater than `threshold` by mutating a shared
    counter inside a parallel forEach (a data race). Re-implement it CORRECTLY with a side-effect-free
    parallel pipeline (filter + count). Return the count.
    */
    static long exercise3(List<Integer> input, int threshold) {
        return input.parallelStream().filter(n -> n > threshold).count();
    }

    public static void main(String[] args) {
        System.out.println("Mod006ParallelStreamsSolutions");
        Check.expect("exercise1", () -> exercise1(1000), 500500L);
        Check.expect("exercise2", () -> exercise2(intRange(1, 1000)), Map.of(false, 500L, true, 500L));
        Check.expect("exercise3", () -> exercise3(intRange(1, 100), 50), 50L);
    }

    private static List<Integer> intRange(int from, int to) {
        return java.util.stream.IntStream.rangeClosed(from, to).boxed().toList();
    }
}
