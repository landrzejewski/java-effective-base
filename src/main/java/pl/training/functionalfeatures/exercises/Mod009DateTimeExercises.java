package pl.training.functionalfeatures.exercises;

import java.time.LocalDate;
import java.time.LocalDateTime;

public final class Mod009DateTimeExercises {

    private Mod009DateTimeExercises() {}

    /*
    Exercise 1 — Return the number of whole days between `from` and `to` (exclusive of `from`,
    inclusive of `to`'s date difference). Use ChronoUnit.DAYS.between.
    */
    static long exercise1(LocalDate from, LocalDate to) {
        // TODO
        throw new UnsupportedOperationException("exercise1");
    }

    /*
    Exercise 2 — Parse `input` in the format dd/MM/yyyy and re-format it as ISO yyyy-MM-dd. For
    "12/06/2026" return "2026-06-12".
    */
    static String exercise2(String input) {
        // TODO
        throw new UnsupportedOperationException("exercise2");
    }

    /*
    Exercise 3 — A meeting is at `localTime` wall-clock in Europe/Warsaw. Return the corresponding
    hour-of-day in America/New_York for the SAME instant. (Hint: atZone + withZoneSameInstant.)
    On 2026-06-12 14:30 Warsaw (+02:00) the New York time (-04:00) is 08:30 → hour 8.
    */
    static int exercise3(LocalDateTime localTime) {
        // TODO
        throw new UnsupportedOperationException("exercise3");
    }

    public static void main(String[] args) {
        System.out.println("Mod009DateTimeExercises");
        Check.expect("exercise1", () -> exercise1(LocalDate.of(2026, 1, 1), LocalDate.of(2026, 6, 12)), 162L);
        Check.expect("exercise2", () -> exercise2("12/06/2026"), "2026-06-12");
        Check.expect("exercise3", () -> exercise3(LocalDateTime.of(2026, 6, 12, 14, 30)), 8);
    }
}
