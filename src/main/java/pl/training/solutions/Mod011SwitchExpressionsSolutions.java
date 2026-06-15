package pl.training.solutions;

public final class Mod011SwitchExpressionsSolutions {

    private Mod011SwitchExpressionsSolutions() {}

    enum Color { RED, GREEN, BLUE }

    /*
    Exercise 1 — Using an ARROW-form switch EXPRESSION over a day number (1=Mon .. 7=Sun), return
    "weekend" for 6 or 7 and "weekday" otherwise. Use multiple labels per case.
    */
    static String exercise1(int day) {
        return switch (day) {
            case 6, 7 -> "weekend";
            default -> "weekday";
        };
    }

    /*
    Exercise 2 — Convert a 0..100 score to a letter grade using a switch EXPRESSION over score/10.
    At least one branch must use a block body with `yield`. 90-100 -> "A", 80-89 -> "B",
    70-79 -> "C", otherwise "F".
    */
    static String exercise2(int score) {
        return switch (score / 10) {
            case 10, 9 -> "A";
            case 8 -> "B";
            case 7 -> {
                // block body demonstrating yield
                String grade = "C";
                yield grade;
            }
            default -> "F";
        };
    }

    /*
    Exercise 3 — Map a Color to its hex string with a switch EXPRESSION. Because Color is an enum and
    every constant is covered, NO default branch is needed (exhaustiveness). RED -> "#FF0000",
    GREEN -> "#00FF00", BLUE -> "#0000FF".
    */
    static String exercise3(Color color) {
        return switch (color) {
            case RED -> "#FF0000";
            case GREEN -> "#00FF00";
            case BLUE -> "#0000FF";
        };
    }

    public static void main(String[] args) {
        System.out.println("Mod011SwitchExpressionsSolutions");
        Check.expect("exercise1", () -> exercise1(6), "weekend");
        Check.expect("exercise2", () -> exercise2(95), "A");
        Check.expect("exercise3", () -> exercise3(Color.RED), "#FF0000");
    }
}
