package pl.training.functionalfeatures.solutions;

import java.util.List;
import java.util.function.Function;
import java.util.function.IntUnaryOperator;
import java.util.function.Predicate;
import java.util.function.Supplier;

public final class Mod002BuiltInInterfacesSolutions {

    private Mod002BuiltInInterfacesSolutions() {}

    /*
    Exercise 1 — Using Predicate combinators (and / or / negate), build a predicate that matches
    numbers which are EVEN and GREATER THAN 5. Return how many elements of `input` match it.
    */
    static long exercise1(List<Integer> input) {
        Predicate<Integer> even = n -> n % 2 == 0;
        Predicate<Integer> gt5 = n -> n > 5;
        Predicate<Integer> both = even.and(gt5);
        return input.stream().filter(both).count();
    }

    /*
    Exercise 2 — Compose two functions f(x)=x+1 and g(x)=x*2 with andThen so the result is g(f(x)),
    and apply it to `x`. (f.andThen(g) means: first f, then g.)
    */
    static int exercise2(int x) {
        Function<Integer, Integer> f = n -> n + 1;
        Function<Integer, Integer> g = n -> n * 2;
        return f.andThen(g).apply(x);
    }

    /*
    Exercise 3 — If `x` is negative, return a default produced LAZILY by a Supplier<Integer> (value
    42); otherwise return x squared computed by an IntUnaryOperator. The Supplier must only be
    invoked when actually needed.
    */
    static int exercise3(int x) {
        Supplier<Integer> defaultValue = () -> 42;
        IntUnaryOperator square = n -> n * n;
        return x < 0 ? defaultValue.get() : square.applyAsInt(x);
    }

    public static void main(String[] args) {
        System.out.println("Mod002BuiltInInterfacesSolutions");
        Check.expect("exercise1", () -> exercise1(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)), 3L);
        Check.expect("exercise2", () -> exercise2(3), 8);
        Check.expect("exercise3", () -> exercise3(5), 25);
    }
}
