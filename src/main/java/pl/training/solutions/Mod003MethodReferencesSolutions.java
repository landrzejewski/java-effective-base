package pl.training.solutions;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class Mod003MethodReferencesSolutions {

    private Mod003MethodReferencesSolutions() {}

    record Point(int x, int y) {
        int coordSum() { return x + y; }
    }

    /*
    Exercise 1 — Parse each string in `input` to an int using a STATIC method reference
    (Integer::parseInt) and return their sum.
    */
    static int exercise1(List<String> input) {
        return input.stream().mapToInt(Integer::parseInt).sum();
    }

    /*
    Exercise 2 — Upper-case each string using an INSTANCE-method-reference-on-the-type
    (String::toUpperCase) and join the results into one string.
    */
    static String exercise2(List<String> input) {
        return input.stream().map(String::toUpperCase).collect(Collectors.joining());
    }

    /*
    Exercise 3 — Zip `xs` and `ys` (same length) into Point objects using a CONSTRUCTOR reference
    (Point::new) and return the total of all coordinates.
    */
    static int exercise3(List<Integer> xs, List<Integer> ys) {
        return IntStream.range(0, xs.size())
                .mapToObj(i -> new Point(xs.get(i), ys.get(i)))   // Point::new applied per index
                .mapToInt(Point::coordSum)
                .sum();
    }

    public static void main(String[] args) {
        System.out.println("Mod003MethodReferencesSolutions");
        Check.expect("exercise1", () -> exercise1(List.of("1", "2", "3")), 6);
        Check.expect("exercise2", () -> exercise2(List.of("a", "b", "c")), "ABC");
        Check.expect("exercise3", () -> exercise3(List.of(1, 2), List.of(3, 4)), 10);
    }
}
