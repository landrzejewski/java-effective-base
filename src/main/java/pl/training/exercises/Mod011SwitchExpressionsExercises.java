package pl.training.exercises;

public final class Mod011SwitchExpressionsExercises {

    private Mod011SwitchExpressionsExercises() {}

    enum Color { RED, GREEN, BLUE }

    /*
    Exercise 1 — Using an ARROW-form switch EXPRESSION over a day number (1=Mon .. 7=Sun), return
    "weekend" for 6 or 7 and "weekday" otherwise. Use multiple labels per case.
    */
    static String exercise1(int day) {
        // TODO
        throw new UnsupportedOperationException("exercise1");
    }

    /*
    Exercise 2 — Convert a 0..100 score to a letter grade using a switch EXPRESSION over score/10.
    At least one branch must use a block body with `yield`. 90-100 -> "A", 80-89 -> "B",
    70-79 -> "C", otherwise "F".
    */
    static String exercise2(int score) {
        // TODO
        throw new UnsupportedOperationException("exercise2");
    }

    /*
    Exercise 3 — Map a Color to its hex string with a switch EXPRESSION. Because Color is an enum and
    every constant is covered, NO default branch is needed (exhaustiveness). RED -> "#FF0000",
    GREEN -> "#00FF00", BLUE -> "#0000FF".
    */
    static String exercise3(Color color) {
        // TODO
        throw new UnsupportedOperationException("exercise3");
    }

    public static void main(String[] args) {
        System.out.println("Mod011SwitchExpressionsExercises");
        Check.expect("exercise1", () -> exercise1(6), "weekend");
        Check.expect("exercise2", () -> exercise2(95), "A");
        Check.expect("exercise3", () -> exercise3(Color.RED), "#FF0000");
    }
}
