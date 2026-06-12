package pl.training.exercises;

import java.util.List;
import java.util.Map;

public final class Mod006ParallelStreamsExercises {

    private Mod006ParallelStreamsExercises() {}

    /*
    Exercise 1 — Compute the sum 1 + 2 + ... + n using a PARALLEL stream and an associative reduce
    (so the result is deterministic and equals the sequential sum). Return the sum.
    */
    static long exercise1(long n) {
        // TODO
        throw new UnsupportedOperationException("exercise1");
    }

    /*
    Exercise 2 — Count how many numbers in `input` are even vs odd using a PARALLEL stream and
    Collectors.groupingByConcurrent (with counting). Return a Map<Boolean, Long>
    ({false=oddCount, true=evenCount}).
    */
    static Map<Boolean, Long> exercise2(List<Integer> input) {
        // TODO
        throw new UnsupportedOperationException("exercise2");
    }

    /*
    Exercise 3 — A buggy version counted elements greater than `threshold` by mutating a shared
    counter inside a parallel forEach (a data race). Re-implement it CORRECTLY with a side-effect-free
    parallel pipeline (filter + count). Return the count.
    */
    static long exercise3(List<Integer> input, int threshold) {
        // TODO
        throw new UnsupportedOperationException("exercise3");
    }

    public static void main(String[] args) {
        System.out.println("Mod006ParallelStreamsExercises");
        Check.expect("exercise1", () -> exercise1(1000), 500500L);
        Check.expect("exercise2", () -> exercise2(intRange(1, 1000)), Map.of(false, 500L, true, 500L));
        Check.expect("exercise3", () -> exercise3(intRange(1, 100), 50), 50L);
    }

    private static List<Integer> intRange(int from, int to) {
        return java.util.stream.IntStream.rangeClosed(from, to).boxed().toList();
    }
}
