package pl.training.solutions;

public final class Mod012PatternSwitchSolutions {

    private Mod012PatternSwitchSolutions() {}

    sealed interface Shape permits Square, Rectangle {}
    record Square(int side) implements Shape {}
    record Rectangle(int width, int height) implements Shape {}

    /*
    Exercise 1 — Compute the area of a Shape with a switch over TYPE PATTERNS on the sealed hierarchy.
    No default is needed because the permitted types are covered. Square(s) -> s*s,
    Rectangle(w,h) -> w*h.
    */
    static int exercise1(Shape shape) {
        return switch (shape) {
            case Square sq -> sq.side() * sq.side();
            case Rectangle r -> r.width() * r.height();
        };
    }

    /*
    Exercise 2 — Classify a Shape as "big" when its area is greater than 10, otherwise "small". Use
    GUARDED patterns (case ... when ...).
    */
    static String exercise2(Shape shape) {
        return switch (shape) {
            case Square sq when sq.side() * sq.side() > 10 -> "big";
            case Rectangle r when r.width() * r.height() > 10 -> "big";
            default -> "small";
        };
    }

    /*
    Exercise 3 — Handle null safely with a switch that has a `case null`. Return "null" for null,
    the string itself for a String, and "other" for anything else.
    */
    static String exercise3(Object o) {
        return switch (o) {
            case null -> "null";
            case String s -> s;
            default -> "other";
        };
    }

    public static void main(String[] args) {
        System.out.println("Mod012PatternSwitchSolutions");
        Check.expect("exercise1", () -> exercise1(new Square(3)), 9);
        Check.expect("exercise2", () -> exercise2(new Square(5)), "big");
        Check.expect("exercise3", () -> exercise3(null), "null");
    }
}
