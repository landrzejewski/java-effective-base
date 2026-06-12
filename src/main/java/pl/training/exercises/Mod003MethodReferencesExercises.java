package pl.training.exercises;

import java.util.List;

public final class Mod003MethodReferencesExercises {

    private Mod003MethodReferencesExercises() {}

    record Point(int x, int y) {
        int coordSum() { return x + y; }
    }

    /*
    Exercise 1 — Parse each string in `input` to an int using a STATIC method reference
    (Integer::parseInt) and return their sum.
    */
    static int exercise1(List<String> input) {
        // TODO
        throw new UnsupportedOperationException("exercise1");
    }

    /*
    Exercise 2 — Upper-case each string using an INSTANCE-method-reference-on-the-type
    (String::toUpperCase) and join the results into one string.
    */
    static String exercise2(List<String> input) {
        // TODO
        throw new UnsupportedOperationException("exercise2");
    }

    /*
    Exercise 3 — Zip `xs` and `ys` (same length) into Point objects using a CONSTRUCTOR reference
    (Point::new) and return the total of all coordinates.
    */
    static int exercise3(List<Integer> xs, List<Integer> ys) {
        // TODO
        throw new UnsupportedOperationException("exercise3");
    }

    public static void main(String[] args) {
        System.out.println("Mod003MethodReferencesExercises");
        Check.expect("exercise1", () -> exercise1(List.of("1", "2", "3")), 6);
        Check.expect("exercise2", () -> exercise2(List.of("a", "b", "c")), "ABC");
        Check.expect("exercise3", () -> exercise3(List.of(1, 2), List.of(3, 4)), 10);
    }
}
