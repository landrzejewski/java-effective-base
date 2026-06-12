package pl.training.solutions;

import java.util.List;
import java.util.stream.Stream;

public final class Mod004StreamBasicsSolutions {

    private Mod004StreamBasicsSolutions() {}

    /*
    Exercise 1 — From `input`, keep only the EVEN numbers, multiply each by 10, and collect into a
    list (preserving order).
    */
    static List<Integer> exercise1(List<Integer> input) {
        return input.stream()
                .filter(n -> n % 2 == 0)
                .map(n -> n * 10)
                .toList();
    }

    /*
    Exercise 2 — Compute the PRODUCT of all numbers in `input` using reduce (identity 1).
    */
    static int exercise2(List<Integer> input) {
        return input.stream().reduce(1, (a, b) -> a * b);
    }

    /*
    Exercise 3 — Produce the first `count` powers of two starting at 1 using an INFINITE stream
    (Stream.iterate(1, x -> x * 2)) bounded with limit. Return them as a list. Example for count=5:
    [1, 2, 4, 8, 16].
    */
    static List<Integer> exercise3(int count) {
        return Stream.iterate(1, x -> x * 2)
                .limit(count)
                .toList();
    }

    public static void main(String[] args) {
        System.out.println("Mod004StreamBasicsSolutions");
        Check.expect("exercise1", () -> exercise1(List.of(1, 2, 3, 4, 5)), List.of(20, 40));
        Check.expect("exercise2", () -> exercise2(List.of(1, 2, 3, 4, 5)), 120);
        Check.expect("exercise3", () -> exercise3(5), List.of(1, 2, 4, 8, 16));
    }
}
