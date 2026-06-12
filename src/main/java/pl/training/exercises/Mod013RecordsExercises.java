package pl.training.exercises;

public final class Mod013RecordsExercises {

    private Mod013RecordsExercises() {}

    record Point(int x, int y) {}
    record Line(Point start, Point end) {}

    // A tiny sealed ADT for an arithmetic expression tree.
    sealed interface Expr permits Num, Add {}
    record Num(int value) implements Expr {}
    record Add(Expr left, Expr right) implements Expr {}

    /*
    Exercise 1 — If `o` is a Point, deconstruct it with a RECORD PATTERN (Point(int x, int y)) and
    return x + y; otherwise return -1.
    */
    static int exercise1(Object o) {
        // TODO
        throw new UnsupportedOperationException("exercise1");
    }

    /*
    Exercise 2 — Deconstruct a Line with a NESTED record pattern
    (Line(Point(var x1, var y1), Point(var x2, var y2))) and return the sum of all four coordinates.
    */
    static int exercise2(Line line) {
        // TODO
        throw new UnsupportedOperationException("exercise2");
    }

    /*
    Exercise 3 — Recursively count the number of Num leaves in an Expr tree. Use a switch over the
    sealed hierarchy and an UNNAMED pattern (Num(_)) since the leaf's value is irrelevant to the count.
    */
    static int exercise3(Expr expr) {
        // TODO
        throw new UnsupportedOperationException("exercise3");
    }

    public static void main(String[] args) {
        System.out.println("Mod013RecordsExercises");
        Check.expect("exercise1", () -> exercise1(new Point(3, 4)), 7);
        Check.expect("exercise2", () -> exercise2(new Line(new Point(1, 2), new Point(3, 4))), 10);
        Check.expect("exercise3", () -> exercise3(new Add(new Num(2), new Add(new Num(3), new Num(4)))), 3);
    }
}
