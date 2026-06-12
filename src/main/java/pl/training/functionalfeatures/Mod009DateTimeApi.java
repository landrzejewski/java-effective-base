package pl.training.functionalfeatures;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.chrono.HijrahDate;
import java.time.chrono.JapaneseDate;
import java.time.chrono.ThaiBuddhistDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.Locale;

public final class Mod009DateTimeApi {

    private Mod009DateTimeApi() {}

    /*
    Why java.time (Java 8+)

    The legacy java.util.Date / Calendar API was error-prone: mutable, not thread-safe, with 0-based
    months and 1900-based years. The java.time package (JSR-310) replaces it with a clear, immutable,
    thread-safe model built around two notions of time:
    - Human time  — calendar/clock fields people read: LocalDate, LocalTime, LocalDateTime.
    - Machine time — a count of seconds/nanos from an epoch: Instant.
    Every core type is immutable; "mutating" methods return a NEW object, so values can be shared freely.
    */
    static void coreTypes() {
        System.out.println("[Section 1] core types");

        LocalDate date = LocalDate.of(2026, Month.JUNE, 12);     // a date, no time, no zone
        LocalTime time = LocalTime.of(14, 30, 0);                // a wall-clock time, no date
        LocalDateTime dateTime = LocalDateTime.of(date, time);   // both, still no zone
        Instant instant = Instant.ofEpochSecond(1_000_000_000L); // a point on the UTC timeline

        System.out.println("  LocalDate     = " + date + " (" + date.getDayOfWeek() + ")");
        System.out.println("  LocalTime     = " + time);
        System.out.println("  LocalDateTime = " + dateTime);
        System.out.println("  Instant       = " + instant);

        // Reading individual fields.
        System.out.println("  year=" + date.getYear() + ", month=" + date.getMonthValue()
                + ", day=" + date.getDayOfMonth() + ", isLeapYear=" + date.isLeapYear());
    }

    /*
    Immutable arithmetic: plus / minus / with / adjusters

    Because the types are immutable, plusX / minusX / withX return new instances. TemporalAdjusters
    expresses higher-level moves ("first day of next month", "next Monday") declaratively.
    */
    static void manipulation() {
        System.out.println("[Section 2] manipulation");

        LocalDate date = LocalDate.of(2026, 6, 12);

        System.out.println("  plusWeeks(2)        = " + date.plusWeeks(2));
        System.out.println("  minusMonths(1)      = " + date.minusMonths(1));
        System.out.println("  withDayOfMonth(1)   = " + date.withDayOfMonth(1));
        System.out.println("  firstDayOfNextMonth = " + date.with(TemporalAdjusters.firstDayOfNextMonth()));
        System.out.println("  next Monday         = " + date.with(TemporalAdjusters.next(DayOfWeek.MONDAY)));

        // The original is unchanged — proof of immutability.
        System.out.println("  original unchanged  = " + date);
    }

    /*
    Duration vs Period

    Both measure an amount of time, but on different scales:
    - Duration — time-based, seconds and nanoseconds. Use with Instant / LocalTime / LocalDateTime.
    - Period   — date-based, years, months and days. Use with LocalDate.
    Mixing them up (e.g. adding a Period to an Instant) is unsupported, so pick by what you measure.
    ChronoUnit.between(...) computes the elapsed amount in a single unit.
    */
    static void durationsAndPeriods() {
        System.out.println("[Section 3] Duration vs Period");

        Duration meeting = Duration.ofHours(1).plusMinutes(30);
        System.out.println("  duration = " + meeting + " => " + meeting.toMinutes() + " minutes");

        LocalTime start = LocalTime.of(9, 0);
        System.out.println("  start + duration = " + start.plus(meeting));

        Period sprint = Period.of(0, 0, 14);    // 14 days
        LocalDate kickoff = LocalDate.of(2026, 6, 12);
        System.out.println("  kickoff + period = " + kickoff.plus(sprint));

        // Elapsed amount between two dates.
        Period between = Period.between(LocalDate.of(2000, 1, 1), kickoff);
        System.out.println("  age = " + between.getYears() + "y " + between.getMonths()
                + "m " + between.getDays() + "d");
        long days = ChronoUnit.DAYS.between(LocalDate.of(2026, 1, 1), kickoff);
        System.out.println("  days since 2026-01-01 = " + days);
    }

    /*
    Parsing and formatting with DateTimeFormatter

    DateTimeFormatter is immutable and thread-safe (unlike the old SimpleDateFormat). Options:
    - Predefined ISO formatters: DateTimeFormatter.ISO_LOCAL_DATE, etc. (the default parse/print form).
    - Pattern-based: ofPattern("dd/MM/yyyy HH:mm"), optionally with a Locale for month/day names.
    - Localized: ofLocalizedDate(FormatStyle.LONG) renders in the locale's conventional style.
    parse(...) is the inverse of format(...).
    */
    static void parsingAndFormatting() {
        System.out.println("[Section 4] parsing and formatting");

        LocalDate date = LocalDate.of(2026, 6, 12);

        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formatted = date.format(pattern);
        System.out.println("  formatted dd/MM/yyyy = " + formatted);

        LocalDate parsed = LocalDate.parse("12/06/2026", pattern);
        System.out.println("  parsed back          = " + parsed + " (equal? " + parsed.equals(date) + ")");

        DateTimeFormatter french = DateTimeFormatter
                .ofLocalizedDate(FormatStyle.LONG).withLocale(Locale.FRANCE);
        System.out.println("  localized (fr-FR)    = " + date.format(french));

        // ISO is the default toString form.
        System.out.println("  ISO_LOCAL_DATE       = " + date.format(DateTimeFormatter.ISO_LOCAL_DATE));
    }

    /*
    Time zones and offsets

    LocalDateTime has no zone. To pin a moment to a place use:
    - ZonedDateTime — a date-time in a named ZoneId, fully DST-aware.
    - OffsetDateTime — a date-time with a fixed UTC offset (no DST rules).
    Converting an Instant to human fields, or converting between zones, always goes through a ZoneId.
    */
    static void timeZones() {
        System.out.println("[Section 5] time zones");

        LocalDateTime local = LocalDateTime.of(2026, 6, 12, 14, 30);
        ZonedDateTime warsaw = local.atZone(ZoneId.of("Europe/Warsaw"));
        ZonedDateTime newYork = warsaw.withZoneSameInstant(ZoneId.of("America/New_York"));

        System.out.println("  Warsaw   = " + warsaw);
        System.out.println("  New York = " + newYork + " (same instant, different wall clock)");

        OffsetDateTime offset = warsaw.toOffsetDateTime();
        System.out.println("  offset   = " + offset);

        // Instant <-> ZonedDateTime always needs a zone to resolve the human fields.
        Instant instant = warsaw.toInstant();
        System.out.println("  as Instant (UTC) = " + instant);
    }

    /*
    Alternative calendar systems

    Beyond the ISO-8601 calendar, java.time.chrono provides other chronologies that interoperate with
    LocalDate. Useful for localized display in regions that use a different civil calendar.
    */
    static void alternativeCalendars() {
        System.out.println("[Section 6] alternative calendars");

        LocalDate iso = LocalDate.of(2026, 6, 12);
        System.out.println("  ISO            = " + iso);
        System.out.println("  Hijrah         = " + HijrahDate.from(iso));
        System.out.println("  Japanese       = " + JapaneseDate.from(iso));
        System.out.println("  ThaiBuddhist   = " + ThaiBuddhistDate.from(iso));
    }

    /*
    Bridging to the legacy API

    When interacting with old code that still uses java.util.Date, convert at the boundary via Instant:
    - Date.from(instant) and date.toInstant().
    Keep java.time types everywhere internally; convert only at the edges.
    */
    static void legacyBridge() {
        System.out.println("[Section 7] legacy bridge");

        Instant instant = Instant.ofEpochSecond(1_700_000_000L);
        Date legacy = Date.from(instant);            // java.time -> legacy
        Instant roundTrip = legacy.toInstant();      // legacy -> java.time

        System.out.println("  instant   = " + instant);
        System.out.println("  java.util.Date = " + legacy.toInstant() + " (round-trips equal? "
                + instant.equals(roundTrip) + ")");
    }

    public static void main(String[] args) {
        coreTypes();
        manipulation();
        durationsAndPeriods();
        parsingAndFormatting();
        timeZones();
        alternativeCalendars();
        legacyBridge();
        System.out.println("Mod009DateTimeApi finished");
    }
}
