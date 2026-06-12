package pl.training.exercises;

public final class Mod012PatternSwitchExercises {

    private Mod012PatternSwitchExercises() {}

    sealed interface Shape permits Square, Rectangle {}
    record Square(int side) implements Shape {}
    record Rectangle(int width, int height) implements Shape {}

    /*
    Exercise 1 — Compute the area of a Shape with a switch over TYPE PATTERNS on the sealed hierarchy.
    No default is needed because the permitted types are covered. Square(s) -> s*s,
    Rectangle(w,h) -> w*h.
    */
    static int exercise1(Shape shape) {
        // TODO
        throw new UnsupportedOperationException("exercise1");
    }

    /*
    Exercise 2 — Classify a Shape as "big" when its area is greater than 10, otherwise "small". Use
    GUARDED patterns (case ... when ...).
    */
    static String exercise2(Shape shape) {
        // TODO
        throw new UnsupportedOperationException("exercise2");
    }

    /*
    Exercise 3 — Handle null safely with a switch that has a `case null`. Return "null" for null,
    the string itself for a String, and "other" for anything else.
    */
    static String exercise3(Object o) {
        // TODO
        throw new UnsupportedOperationException("exercise3");
    }

    public static void main(String[] args) {
        System.out.println("Mod012PatternSwitchExercises");
        Check.expect("exercise1", () -> exercise1(new Square(3)), 9);
        Check.expect("exercise2", () -> exercise2(new Square(5)), "big");
        Check.expect("exercise3", () -> exercise3(null), "null");
    }
}
