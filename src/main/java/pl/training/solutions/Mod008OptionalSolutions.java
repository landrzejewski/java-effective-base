package pl.training.solutions;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class Mod008OptionalSolutions {

    private Mod008OptionalSolutions() {}

    /*
    Exercise 1 — Look up `key` in `map`. If present, return the value DOUBLED; if absent, return 0.
    Use Optional (ofNullable + map + orElse) rather than an explicit null check.
    */
    static int exercise1(Map<String, Integer> map, String key) {
        return Optional.ofNullable(map.get(key)).map(v -> v * 2).orElse(0);
    }

    /*
    Exercise 2 — Return the first string in `input` longer than 3 characters, or "none" if there is
    none. The search helper should return an Optional<String> (no nulls); resolve it with orElse.
    */
    static String exercise2(List<String> input) {
        return input.stream().filter(s -> s.length() > 3).findFirst().orElse("none");
    }

    /*
    Exercise 3 — Compute the average of 1..n using IntStream.rangeClosed and IntSummaryStatistics
    (or .average()). Return the average as a double. For n=10 the average is 5.5.
    */
    static double exercise3(int n) {
        return java.util.stream.IntStream.rangeClosed(1, n)
                .summaryStatistics()
                .getAverage();
    }

    public static void main(String[] args) {
        System.out.println("Mod008OptionalSolutions");
        Check.expect("exercise1", () -> exercise1(Map.of("a", 5), "a"), 10);
        Check.expect("exercise2", () -> exercise2(List.of("a", "bb", "cccc")), "cccc");
        Check.expect("exercise3", () -> exercise3(10), 5.5);
    }
}
