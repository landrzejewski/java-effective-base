package pl.training.functionalfeatures.exercises;

import java.util.List;
import java.util.Map;

public final class Mod008OptionalExercises {

    private Mod008OptionalExercises() {}

    /*
    Exercise 1 — Look up `key` in `map`. If present, return the value DOUBLED; if absent, return 0.
    Use Optional (ofNullable + map + orElse) rather than an explicit null check.
    */
    static int exercise1(Map<String, Integer> map, String key) {
        // TODO
        throw new UnsupportedOperationException("exercise1");
    }

    /*
    Exercise 2 — Return the first string in `input` longer than 3 characters, or "none" if there is
    none. The search helper should return an Optional<String> (no nulls); resolve it with orElse.
    */
    static String exercise2(List<String> input) {
        // TODO
        throw new UnsupportedOperationException("exercise2");
    }

    /*
    Exercise 3 — Compute the average of 1..n using IntStream.rangeClosed and IntSummaryStatistics
    (or .average()). Return the average as a double. For n=10 the average is 5.5.
    */
    static double exercise3(int n) {
        // TODO
        throw new UnsupportedOperationException("exercise3");
    }

    public static void main(String[] args) {
        System.out.println("Mod008OptionalExercises");
        Check.expect("exercise1", () -> exercise1(Map.of("a", 5), "a"), 10);
        Check.expect("exercise2", () -> exercise2(List.of("a", "bb", "cccc")), "cccc");
        Check.expect("exercise3", () -> exercise3(10), 5.5);
    }
}
