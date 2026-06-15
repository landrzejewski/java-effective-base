package pl.training.solutions;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public final class Mod001LambdasSolutions {

    private Mod001LambdasSolutions() {}

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
        Transformer<String, Integer> length = s -> s.length();
        return length.transform(input);
    }

    /*
    Exercise 2 — Write a factory that returns a Function<Integer,Integer> multiplying its argument by
    `factor`. The lambda must capture `factor` (an effectively-final variable). Return the result of
    applying that function to `value`.
    */
    static int exercise2(int factor, int value) {
        Function<Integer, Integer> multiplier = x -> x * factor;
        return multiplier.apply(value);
    }

    /*
    Exercise 3 — Sort `input` by string length, ascending, WITHOUT an anonymous Comparator class —
    use a lambda (or Comparator.comparingInt). Return a new sorted list.
    */
    static List<String> exercise3(List<String> input) {
        return input.stream()
                .sorted(Comparator.comparingInt(String::length))
                .toList();
    }

    public static void main(String[] args) {
        System.out.println("Mod001LambdasSolutions");
        Check.expect("exercise1", () -> exercise1("hello"), 5);
        Check.expect("exercise2", () -> exercise2(3, 4), 12);
        Check.expect("exercise3", () -> exercise3(List.of("ccc", "a", "bb")), List.of("a", "bb", "ccc"));
    }
}
