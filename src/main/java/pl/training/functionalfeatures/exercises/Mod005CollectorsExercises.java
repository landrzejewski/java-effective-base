package pl.training.functionalfeatures.exercises;

import java.util.List;
import java.util.Map;

public final class Mod005CollectorsExercises {

    private Mod005CollectorsExercises() {}

    record Sale(String product, int amount) {}

    /*
    Exercise 1 — Group the words in `input` by their length into a Map<Integer, List<String>>
    (encounter order preserved within each group). Use Collectors.groupingBy.
    */
    static Map<Integer, List<String>> exercise1(List<String> input) {
        // TODO
        throw new UnsupportedOperationException("exercise1");
    }

    /*
    Exercise 2 — Partition `input` into even and odd, counting how many fall in each bucket. Return a
    Map<Boolean, Long> ({false=oddCount, true=evenCount}). Use partitioningBy with a counting
    downstream collector.
    */
    static Map<Boolean, Long> exercise2(List<Integer> input) {
        // TODO
        throw new UnsupportedOperationException("exercise2");
    }

    /*
    Exercise 3 — Sum the amounts per product into a Map<String, Integer>. Use Collectors.toMap with a
    merge function that adds amounts for duplicate keys.
    */
    static Map<String, Integer> exercise3(List<Sale> sales) {
        // TODO
        throw new UnsupportedOperationException("exercise3");
    }

    public static void main(String[] args) {
        System.out.println("Mod005CollectorsExercises");
        Check.expect("exercise1", () -> exercise1(List.of("a", "bb", "cc", "ddd")),
                Map.of(1, List.of("a"), 2, List.of("bb", "cc"), 3, List.of("ddd")));
        Check.expect("exercise2", () -> exercise2(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)),
                Map.of(false, 5L, true, 5L));
        Check.expect("exercise3", () -> exercise3(List.of(
                new Sale("a", 1), new Sale("b", 2), new Sale("a", 2))),
                Map.of("a", 3, "b", 2));
    }
}
