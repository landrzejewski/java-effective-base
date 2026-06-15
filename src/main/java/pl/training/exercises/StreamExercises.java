package pl.training.exercises;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import static java.util.stream.Collectors.*;
import static java.util.Comparator.*;

/**
 * ĆWICZENIA: Java Stream API — 15 zadań od prostych do złożonych.
 *
 * INSTRUKCJA:
 *   1. Uzupełnij ciało każdej metody ex1()..ex15() (zastąp `throw ... TODO`).
 *   2. Uruchom:  java StreamExercises.java
 *   3. Patrz na raport PASS/FAIL. Cel: 15/15.
 *
 * Każde zadanie ma w komentarzu: CEL i PODPOWIEDŹ (jakich operacji użyć).
 * Pracuj na liście EMPLOYEES. Nie zmieniaj sygnatur metod ani checkera.
 *
 * Uruchomienie (JDK 21+):  java StreamExercises.java
 */
public class StreamExercises {

    record Employee(
            int id,
            String name,
            String department,
            double salary,
            int age,
            int yearsExperience,
            boolean remote
    ) {}

    static final List<Employee> EMPLOYEES = List.of(
            new Employee(1,  "Alice Johnson",  "Engineering", 95_000,  28, 5,  true),
            new Employee(2,  "Bob Smith",      "Marketing",   65_000,  32, 8,  false),
            new Employee(3,  "Carol Davis",    "Engineering", 110_000, 35, 12, true),
            new Employee(4,  "David Wilson",   "Sales",       75_000,  29, 6,  false),
            new Employee(5,  "Eve Brown",      "Engineering", 88_000,  26, 3,  true),
            new Employee(6,  "Frank Miller",   "HR",          72_000,  41, 15, false),
            new Employee(7,  "Grace Lee",      "Marketing",   68_000,  30, 7,  true),
            new Employee(8,  "Henry Garcia",   "Engineering", 105_000, 33, 9,  false),
            new Employee(9,  "Ivy Martinez",   "Sales",       82_000,  27, 4,  true),
            new Employee(10, "Jack Anderson",  "Engineering", 92_000,  31, 7,  false),
            new Employee(11, "Kate Thompson",  "HR",          78_000,  36, 11, true),
            new Employee(12, "Liam White",     "Marketing",   71_000,  28, 5,  false),
            new Employee(13, "Mia Harris",     "Sales",       79_000,  34, 10, true),
            new Employee(14, "Noah Clark",     "Engineering", 98_000,  29, 6,  false),
            new Employee(15, "Olivia Lewis",   "Marketing",   64_000,  25, 2,  true),
            new Employee(16, "Paul Walker",    "Sales",       85_000,  38, 13, false),
            new Employee(17, "Quinn Hall",     "HR",          76_000,  33, 9,  true),
            new Employee(18, "Ruby Allen",     "Engineering", 115_000, 37, 14, false),
            new Employee(19, "Sam Young",      "Marketing",   69_000,  31, 8,  true),
            new Employee(20, "Tina King",      "Sales",       81_000,  30, 7,  false)
    );

    // ════════════════════════════════════════════════════════════════════
    //  ZADANIA  (uzupełnij ciała metod)
    // ════════════════════════════════════════════════════════════════════

    //  1. [proste] Liczba pracowników 
    // CEL: zwróć liczbę wszystkich pracowników.
    // PODPOWIEDŹ: stream().count()  (albo po prostu rozmiar listy)
    static long ex1() {
        throw new UnsupportedOperationException("TODO ćwiczenie 1");
    }

    //  2. [proste] Nazwiska z działu Engineering 
    // CEL: lista nazwisk pracowników działu "Engineering" (w kolejności z listy).
    // PODPOWIEDŹ: filter(...).map(Employee::name).toList()
    static List<String> ex2() {
        throw new UnsupportedOperationException("TODO ćwiczenie 2");
    }

    //  3. [proste] Liczba pracowników zdalnych 
    // CEL: ilu pracowników ma remote == true.
    // PODPOWIEDŹ: filter(Employee::remote).count()
    static long ex3() {
        throw new UnsupportedOperationException("TODO ćwiczenie 3");
    }

    //  4. [proste] Czy ktokolwiek zarabia więcej niż 110 000? 
    // CEL: true, jeśli istnieje pracownik z pensją > 110000 (ostro większą).
    // PODPOWIEDŹ: anyMatch(...)
    static boolean ex4() {
        throw new UnsupportedOperationException("TODO ćwiczenie 4");
    }

    //  5. [proste] Suma wszystkich pensji 
    // CEL: zwróć łączny koszt wynagrodzeń (double).
    // PODPOWIEDŹ: mapToDouble(Employee::salary).sum()
    static double ex5() {
        throw new UnsupportedOperationException("TODO ćwiczenie 5");
    }

    //  6. [średnie] Najmłodszy pracownik 
    // CEL: Optional<Employee> z najmłodszą osobą (najniższy age).
    // PODPOWIEDŹ: min(comparingInt(Employee::age))
    static Optional<Employee> ex6() {
        throw new UnsupportedOperationException("TODO ćwiczenie 6");
    }

    //  7. [średnie] Średnia pensja 
    // CEL: średnia arytmetyczna pensji (double). Dla pustej listy zwróć 0.
    // PODPOWIEDŹ: mapToDouble(...).average().orElse(0)
    static double ex7() {
        throw new UnsupportedOperationException("TODO ćwiczenie 7");
    }

    //  8. [średnie] Top 3 nazwiska wg pensji (malejąco) 
    // CEL: lista 3 nazwisk osób o najwyższych pensjach, od najwyższej.
    // PODPOWIEDŹ: sorted(comparingDouble(Employee::salary).reversed()).limit(3).map(...).toList()
    static List<String> ex8() {
        throw new UnsupportedOperationException("TODO ćwiczenie 8");
    }

    //  9. [średnie] Unikalne działy, posortowane alfabetycznie 
    // CEL: lista nazw działów bez powtórzeń, rosnąco.
    // PODPOWIEDŹ: map(...).distinct().sorted().toList()
    static List<String> ex9() {
        throw new UnsupportedOperationException("TODO ćwiczenie 9");
    }

    //  10. [średnie] Liczność per dział 
    // CEL: Map<String, Long> — ile osób w każdym dziale.
    // PODPOWIEDŹ: collect(groupingBy(Employee::department, counting()))
    static Map<String, Long> ex10() {
        throw new UnsupportedOperationException("TODO ćwiczenie 10");
    }

    //  11. [średnie] Podział remote / biuro 
    // CEL: Map<Boolean, List<Employee>> — pod kluczem true zdalni, false biurowi.
    // PODPOWIEDŹ: collect(partitioningBy(Employee::remote))
    static Map<Boolean, List<Employee>> ex11() {
        throw new UnsupportedOperationException("TODO ćwiczenie 11");
    }

    //  12. [trudne] Indeks id → nazwisko 
    // CEL: Map<Integer, String> mapująca id pracownika na jego nazwisko.
    // PODPOWIEDŹ: collect(toMap(Employee::id, Employee::name))
    static Map<Integer, String> ex12() {
        throw new UnsupportedOperationException("TODO ćwiczenie 12");
    }

    //  13. [trudne] Średnia pensja per dział, zaokrąglona 
    // CEL: Map<String, Long> — średnia pensja w dziale zaokrąglona do pełnych
    //      (Math.round). Czyli najpierw averagingDouble, potem zaokrąglenie.
    // PODPOWIEDŹ: groupingBy(dept, collectingAndThen(averagingDouble(...), Math::round))
    static Map<String, Long> ex13() {
        throw new UnsupportedOperationException("TODO ćwiczenie 13");
    }

    //  14. [trudne] Najlepiej zarabiający w każdym dziale 
    // CEL: Map<String, Employee> — dla każdego działu pracownik o najwyższej pensji.
    // PODPOWIEDŹ: groupingBy(dept, collectingAndThen(maxBy(comparingDouble(...)), Optional::get))
    static Map<String, Employee> ex14() {
        throw new UnsupportedOperationException("TODO ćwiczenie 14");
    }

    //  15. [złożone] Działy posortowane malejąco wg sumy pensji 
    // CEL: List<String> w formacie  "Dział - $suma"  (suma bez miejsc po przecinku),
    //      posortowana malejąco wg sumy pensji w dziale.
    //      Przykład elementu:  "Engineering - $703000"
    // PODPOWIEDŹ:
    //   1) groupingBy(dept, summingDouble(salary))  -> Map<String,Double>
    //   2) entrySet().stream().sorted(Map.Entry.comparingByValue().reversed())
    //   3) map(e -> e.getKey() + " - $" + String.format("%.0f", e.getValue()))
    //   4) toList()
    static List<String> ex15() {
        throw new UnsupportedOperationException("TODO ćwiczenie 15");
    }

    static int passed = 0, failed = 0;

    public static void main(String[] args) {
        Employee olivia = byId(15);
        Map<String, Employee> topExpected = Map.of(
                "Engineering", byId(18),   // Ruby Allen
                "Sales",       byId(16),   // Paul Walker
                "Marketing",   byId(12),   // Liam White
                "HR",          byId(11));  // Kate Thompson

        check("1  liczba pracowników",        20L,           StreamExercises::ex1);
        check("2  nazwiska Engineering",
                List.of("Alice Johnson", "Carol Davis", "Eve Brown", "Henry Garcia",
                        "Jack Anderson", "Noah Clark", "Ruby Allen"),
                StreamExercises::ex2);
        check("3  liczba zdalnych",           10L,           StreamExercises::ex3);
        check("4  ktoś > 110k",               Boolean.TRUE,  StreamExercises::ex4);
        checkDouble("5  suma pensji",         1_668_000.0,   StreamExercises::ex5);
        check("6  najmłodszy",                Optional.of(olivia), StreamExercises::ex6);
        checkDouble("7  średnia pensja",      83_400.0,      StreamExercises::ex7);
        check("8  top3 wg pensji",
                List.of("Ruby Allen", "Carol Davis", "Henry Garcia"), StreamExercises::ex8);
        check("9  unikalne działy",
                List.of("Engineering", "HR", "Marketing", "Sales"), StreamExercises::ex9);
        check("10 liczność per dział",
                Map.of("Engineering", 7L, "Sales", 5L, "Marketing", 5L, "HR", 3L),
                StreamExercises::ex10);
        check("11 podział remote/biuro", "10/10",
                () -> { var m = ex11(); return m.get(true).size() + "/" + m.get(false).size(); });
        check("12 indeks id→nazwisko (id=18)", "Ruby Allen",
                () -> ex12().get(18));
        check("13 średnia per dział (round)",
                Map.of("Engineering", 100_429L, "Sales", 80_400L, "Marketing", 67_400L, "HR", 75_333L),
                StreamExercises::ex13);
        check("14 najlepiej zarabiający/dział", topExpected, StreamExercises::ex14);
        check("15 działy wg sumy pensji",
                List.of("Engineering - $703000", "Sales - $402000",
                        "Marketing - $337000", "HR - $226000"),
                StreamExercises::ex15);

        System.out.printf("%n══════════════════════════════%n  WYNIK: %d/%d%s%n",
                passed, passed + failed,
                failed == 0 ? "  🎉 komplet!" : "  (pozostało: " + failed + ")");
    }

    static Employee byId(int id) {
        return EMPLOYEES.stream().filter(e -> e.id() == id).findFirst().orElseThrow();
    }

    static void check(String label, Object expected, Supplier<?> actual) {
        try {
            Object a = actual.get();
            report(label, Objects.equals(expected, a), expected, a);
        } catch (UnsupportedOperationException todo) {
            report(label, false, expected, "— nie zaimplementowano —");
        } catch (Exception ex) {
            report(label, false, expected, "wyjątek: " + ex);
        }
    }

    static void checkDouble(String label, double expected, DoubleSupplier actual) {
        try {
            double a = actual.getAsDouble();
            report(label, Math.abs(a - expected) < 1e-6, expected, a);
        } catch (UnsupportedOperationException todo) {
            report(label, false, expected, "— nie zaimplementowano —");
        } catch (Exception ex) {
            report(label, false, expected, "wyjątek: " + ex);
        }
    }

    static void report(String label, boolean ok, Object expected, Object actual) {
        if (ok) {
            passed++;
            System.out.printf("  ✔ PASS  %s%n", label);
        } else {
            failed++;
            System.out.printf("  ✗ FAIL  %s%n          oczekiwano: %s%n          otrzymano:  %s%n",
                    label, expected, actual);
        }
    }
}