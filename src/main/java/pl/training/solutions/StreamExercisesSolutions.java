package pl.training.solutions;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import static java.util.stream.Collectors.*;
import static java.util.Comparator.*;

/**
 * ROZWIĄZANIA WZORCOWE do StreamExercises (15 zadań).
 *
 * Klucz dla prowadzącego: każda metoda ma gotową implementację i krótki
 * komentarz wyjaśniający kluczową operację. Uruchomienie potwierdza 15/15.
 *
 * Uruchomienie (JDK 21+):  java StreamExercisesSolutions.java
 */
public class StreamExercisesSolutions {

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
    //  ROZWIĄZANIA
    // ════════════════════════════════════════════════════════════════════

    //  1. Liczba pracowników 
    // count() to operacja terminalna zwracająca long.
    static long ex1() {
        return EMPLOYEES.stream().count();
        // wariant bez strumienia: return EMPLOYEES.size();
    }

    //  2. Nazwiska z działu Engineering 
    // filter zawęża, map wyciąga pole, toList() (JDK 16+) zbiera do niemutowalnej listy.
    static List<String> ex2() {
        return EMPLOYEES.stream()
                .filter(e -> e.department().equals("Engineering"))
                .map(Employee::name)
                .toList();
    }

    //  3. Liczba pracowników zdalnych 
    static long ex3() {
        return EMPLOYEES.stream()
                .filter(Employee::remote)
                .count();
    }

    //  4. Czy ktokolwiek zarabia więcej niż 110 000? 
    // anyMatch przerywa przy pierwszym dopasowaniu (short-circuit).
    static boolean ex4() {
        return EMPLOYEES.stream().anyMatch(e -> e.salary() > 110_000);
    }

    //  5. Suma wszystkich pensji 
    // mapToDouble → strumień prymitywny z metodą sum().
    static double ex5() {
        return EMPLOYEES.stream()
                .mapToDouble(Employee::salary)
                .sum();
    }

    //  6. Najmłodszy pracownik 
    // min wymaga Comparatora; comparingInt unika autoboxingu.
    static Optional<Employee> ex6() {
        return EMPLOYEES.stream().min(comparingInt(Employee::age));
    }

    //  7. Średnia pensja 
    // average() zwraca OptionalDouble; orElse(0) obsługuje pustą listę.
    static double ex7() {
        return EMPLOYEES.stream()
                .mapToDouble(Employee::salary)
                .average()
                .orElse(0);
    }

    //  8. Top 3 nazwiska wg pensji (malejąco) 
    // reversed() odwraca kolejność komparatora, limit po sort.
    static List<String> ex8() {
        return EMPLOYEES.stream()
                .sorted(comparingDouble(Employee::salary).reversed())
                .limit(3)
                .map(Employee::name)
                .toList();
    }

    //  9. Unikalne działy, posortowane alfabetycznie 
    // distinct usuwa duplikaty (po equals), sorted bez argumentu = porządek naturalny.
    static List<String> ex9() {
        return EMPLOYEES.stream()
                .map(Employee::department)
                .distinct()
                .sorted()
                .toList();
    }

    //  10. Liczność per dział 
    // groupingBy + downstream counting() → Map<String, Long>.
    static Map<String, Long> ex10() {
        return EMPLOYEES.stream()
                .collect(groupingBy(Employee::department, counting()));
    }

    //  11. Podział remote / biuro 
    // partitioningBy zawsze tworzy oba klucze (true i false), nawet gdy pusto.
    static Map<Boolean, List<Employee>> ex11() {
        return EMPLOYEES.stream()
                .collect(partitioningBy(Employee::remote));
    }

    //  12. Indeks id → nazwisko 
    // toMap(keyFn, valueFn). Przy ryzyku kolizji kluczy dodaj funkcję scalającą.
    static Map<Integer, String> ex12() {
        return EMPLOYEES.stream()
                .collect(toMap(Employee::id, Employee::name));
    }

    //  13. Średnia pensja per dział, zaokrąglona 
    // collectingAndThen owija wynik downstream-kolektora transformacją końcową:
    // averagingDouble (double) → Math::round (long).
    static Map<String, Long> ex13() {
        return EMPLOYEES.stream()
                .collect(groupingBy(Employee::department, collectingAndThen(averagingDouble(Employee::salary), Math::round)));
    }

    //  14. Najlepiej zarabiający w każdym dziale 
    // maxBy zwraca Optional; collectingAndThen + Optional::get „rozpakowuje”
    // (bezpieczne, bo grupa nigdy nie jest pusta).
    static Map<String, Employee> ex14() {
        return EMPLOYEES.stream()
                .collect(groupingBy(Employee::department, collectingAndThen(maxBy(comparingDouble(Employee::salary)), Optional::get)));
    }

    //  15. Działy posortowane malejąco wg sumy pensji 
    // 1) zsumuj pensje per dział, 2) strumieniuj wpisy mapy i posortuj malejąco
    //    po wartości, 3) sformatuj. Map.Entry.comparingByValue() wymaga jawnego
    //    typu <String,Double> przy reversed().
    static List<String> ex15() {
        return EMPLOYEES.stream()
                .collect(groupingBy(Employee::department, summingDouble(Employee::salary)))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .map(entry -> entry.getKey() + " - $" + String.format("%.0f", entry.getValue()))
                .toList();
    }
    
    static int passed = 0, failed = 0;

    public static void main(String[] args) {
        Employee olivia = byId(15);
        Map<String, Employee> topExpected = Map.of(
                "Engineering", byId(18),
                "Sales",       byId(16),
                "Marketing",   byId(12),
                "HR",          byId(11));

        check("1  liczba pracowników",        20L,           StreamExercisesSolutions::ex1);
        check("2  nazwiska Engineering",
                List.of("Alice Johnson", "Carol Davis", "Eve Brown", "Henry Garcia",
                        "Jack Anderson", "Noah Clark", "Ruby Allen"),
                StreamExercisesSolutions::ex2);
        check("3  liczba zdalnych",           10L,           StreamExercisesSolutions::ex3);
        check("4  ktoś > 110k",               Boolean.TRUE,  StreamExercisesSolutions::ex4);
        checkDouble("5  suma pensji",         1_668_000.0,   StreamExercisesSolutions::ex5);
        check("6  najmłodszy",                Optional.of(olivia), StreamExercisesSolutions::ex6);
        checkDouble("7  średnia pensja",      83_400.0,      StreamExercisesSolutions::ex7);
        check("8  top3 wg pensji",
                List.of("Ruby Allen", "Carol Davis", "Henry Garcia"), StreamExercisesSolutions::ex8);
        check("9  unikalne działy",
                List.of("Engineering", "HR", "Marketing", "Sales"), StreamExercisesSolutions::ex9);
        check("10 liczność per dział",
                Map.of("Engineering", 7L, "Sales", 5L, "Marketing", 5L, "HR", 3L),
                StreamExercisesSolutions::ex10);
        check("11 podział remote/biuro", "10/10",
                () -> { var m = ex11(); return m.get(true).size() + "/" + m.get(false).size(); });
        check("12 indeks id→nazwisko (id=18)", "Ruby Allen",
                () -> ex12().get(18));
        check("13 średnia per dział (round)",
                Map.of("Engineering", 100_429L, "Sales", 80_400L, "Marketing", 67_400L, "HR", 75_333L),
                StreamExercisesSolutions::ex13);
        check("14 najlepiej zarabiający/dział", topExpected, StreamExercisesSolutions::ex14);
        check("15 działy wg sumy pensji",
                List.of("Engineering - $703000", "Sales - $402000",
                        "Marketing - $337000", "HR - $226000"),
                StreamExercisesSolutions::ex15);

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