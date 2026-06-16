package pl.training.functionalfeatures.exercises;

import java.util.List;

public final class Mod010PatternMatchingExercises {

    private Mod010PatternMatchingExercises() {}

    /*
    Exercise 1 — Replace the classic cast-and-check idiom with a type pattern: if `o` is a String,
    return its length; otherwise return -1.
    */
    static int exercise1(Object o) {
        // TODO
        throw new UnsupportedOperationException("exercise1");
    }

    /*
    Exercise 2 — Using a single combined condition with && and a pattern variable, return true only
    when `o` is a String LONGER than 3 characters.
    */
    static boolean exercise2(Object o) {
        // TODO
        throw new UnsupportedOperationException("exercise2");
    }

    /*
    Exercise 3 — Using a pattern with a generic type (List<?>), return the size of `o` when it is any
    List, otherwise -1.
    */
    static int exercise3(Object o) {
        // TODO
        throw new UnsupportedOperationException("exercise3");
    }

    public static void main(String[] args) {
        System.out.println("Mod010PatternMatchingExercises");
        Check.expect("exercise1", () -> exercise1("hello"), 5);
        Check.expect("exercise2", () -> exercise2("abcd"), true);
        Check.expect("exercise3", () -> exercise3(List.of(1, 2, 3)), 3);
    }
}
