package pl.training.functionalfeatures.exercises;

import java.util.List;

public final class Mod001LambdasExercises {

    private Mod001LambdasExercises() {}

    @FunctionalInterface
    interface Transformer<T, R> {
        R transform(T input);
    }

    /*
    Exercise 1 — Define a custom @FunctionalInterface Transformer<T,R> with a single method
    transform(T). Using a LAMBDA (not an anonymous class), create a Transformer<String,Integer>
    that returns the length of the string, and apply it to `input`.
    */
    static int exercise1(String input) {
        // TODO
        throw new UnsupportedOperationException("exercise1");
    }

    /*
    Exercise 2 — Write a factory that returns a Function<Integer,Integer> multiplying its argument by
    `factor`. The lambda must capture `factor` (an effectively-final variable). Return the result of
    applying that function to `value`.
    */
    static int exercise2(int factor, int value) {
        // TODO
        throw new UnsupportedOperationException("exercise2");
    }

    /*
    Exercise 3 — Sort `input` by string length, ascending, WITHOUT an anonymous Comparator class —
    use a lambda (or Comparator.comparingInt). Return a new sorted list.
    */
    static List<String> exercise3(List<String> input) {
        // TODO
        throw new UnsupportedOperationException("exercise3");
    }

    public static void main(String[] args) {
        System.out.println("Mod001LambdasExercises");
        Check.expect("exercise1", () -> exercise1("hello"), 5);
        Check.expect("exercise2", () -> exercise2(3, 4), 12);
        Check.expect("exercise3", () -> exercise3(List.of("ccc", "a", "bb")), List.of("a", "bb", "ccc"));
    }
}
