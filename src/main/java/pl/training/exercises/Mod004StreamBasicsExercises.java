package pl.training.exercises;

import java.util.List;

public final class Mod004StreamBasicsExercises {

    private Mod004StreamBasicsExercises() {}

    /*
    Exercise 1 — From `input`, keep only the EVEN numbers, multiply each by 10, and collect into a
    list (preserving order).
    */
    static List<Integer> exercise1(List<Integer> input) {
        // TODO
        throw new UnsupportedOperationException("exercise1");
    }

    /*
    Exercise 2 — Compute the PRODUCT of all numbers in `input` using reduce (identity 1).
    */
    static int exercise2(List<Integer> input) {
        // TODO
        throw new UnsupportedOperationException("exercise2");
    }

    /*
    Exercise 3 — Produce the first `count` powers of two starting at 1 using an INFINITE stream
    (Stream.iterate(1, x -> x * 2)) bounded with limit. Return them as a list. Example for count=5:
    [1, 2, 4, 8, 16].
    */
    static List<Integer> exercise3(int count) {
        // TODO
        throw new UnsupportedOperationException("exercise3");
    }

    public static void main(String[] args) {
        System.out.println("Mod004StreamBasicsExercises");
        Check.expect("exercise1", () -> exercise1(List.of(1, 2, 3, 4, 5)), List.of(20, 40));
        Check.expect("exercise2", () -> exercise2(List.of(1, 2, 3, 4, 5)), 120);
        Check.expect("exercise3", () -> exercise3(5), List.of(1, 2, 4, 8, 16));
    }
}
