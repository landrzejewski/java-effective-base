package pl.training.exercises;

import java.util.List;

public final class Mod002BuiltInInterfacesExercises {

    private Mod002BuiltInInterfacesExercises() {}

    /*
    Exercise 1 — Using Predicate combinators (and / or / negate), build a predicate that matches
    numbers which are EVEN and GREATER THAN 5. Return how many elements of `input` match it.
    */
    static long exercise1(List<Integer> input) {
        // TODO
        throw new UnsupportedOperationException("exercise1");
    }

    /*
    Exercise 2 — Compose two functions f(x)=x+1 and g(x)=x*2 with andThen so the result is g(f(x)),
    and apply it to `x`. (f.andThen(g) means: first f, then g.)
    */
    static int exercise2(int x) {
        // TODO
        throw new UnsupportedOperationException("exercise2");
    }

    /*
    Exercise 3 — If `x` is negative, return a default produced LAZILY by a Supplier<Integer> (value
    42); otherwise return x squared computed by an IntUnaryOperator. The Supplier must only be
    invoked when actually needed.
    */
    static int exercise3(int x) {
        // TODO
        throw new UnsupportedOperationException("exercise3");
    }

    public static void main(String[] args) {
        System.out.println("Mod002BuiltInInterfacesExercises");
        Check.expect("exercise1", () -> exercise1(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)), 3L);
        Check.expect("exercise2", () -> exercise2(3), 8);
        Check.expect("exercise3", () -> exercise3(5), 25);
    }
}
