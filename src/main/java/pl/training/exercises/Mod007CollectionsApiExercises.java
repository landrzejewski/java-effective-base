package pl.training.exercises;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class Mod007CollectionsApiExercises {

    private Mod007CollectionsApiExercises() {}

    /*
    Exercise 1 — Take an immutable snapshot of `source` with List.copyOf, then mutate `source` by
    adding 99. Return the snapshot — it must be UNAFFECTED by the later mutation (proving copyOf takes
    an independent copy).
    */
    static List<Integer> exercise1(List<Integer> source) {
        // TODO
        throw new UnsupportedOperationException("exercise1");
    }

    /*
    Exercise 2 — Count word frequencies in `words` into a Map<String, Integer> using Map.merge
    (insert 1, or add 1 to the existing count). Preserve first-seen order with a LinkedHashMap.
    */
    static Map<String, Integer> exercise2(List<String> words) {
        // TODO
        throw new UnsupportedOperationException("exercise2");
    }

    /*
    Exercise 3 — Using the Sequenced Collections API (Java 21), return a REVERSED view of `input` as a
    new list. (Hint: SequencedCollection.reversed().)
    */
    static List<String> exercise3(List<String> input) {
        // TODO
        throw new UnsupportedOperationException("exercise3");
    }

    public static void main(String[] args) {
        System.out.println("Mod007CollectionsApiExercises");
        Check.expect("exercise1", () -> exercise1(new ArrayList<>(List.of(1, 2))), List.of(1, 2));
        Check.expect("exercise2", () -> exercise2(List.of("a", "b", "a", "c", "b", "a")),
                Map.of("a", 3, "b", 2, "c", 1));
        Check.expect("exercise3", () -> exercise3(List.of("a", "b", "c")), List.of("c", "b", "a"));
    }
}
