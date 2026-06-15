package pl.training.functionalfeatures;

import java.net.SocketTimeoutException;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Comparator.*;
import static java.util.stream.Collectors.*;

public class StreamExamples {

    record Employee(
            int id,
            String name,
            String department,
            double salary,
            int age,
            int yearsExperience,
            boolean remote
    ) {
    }

    static List<Employee> createEmployees() {
        return List.of(
                new Employee(1, "Alice Johnson", "Engineering", 95_000, 28, 5, true),
                new Employee(2, "Bob Smith", "Marketing", 65_000, 32, 8, false),
                new Employee(3, "Carol Davis", "Engineering", 110_000, 35, 12, true),
                new Employee(4, "David Wilson", "Sales", 75_000, 29, 6, false),
                new Employee(5, "Eve Brown", "Engineering", 88_000, 26, 3, true),
                new Employee(6, "Frank Miller", "HR", 72_000, 41, 15, false),
                new Employee(7, "Grace Lee", "Marketing", 68_000, 30, 7, true),
                new Employee(8, "Henry Garcia", "Engineering", 105_000, 33, 9, false),
                new Employee(9, "Ivy Martinez", "Sales", 82_000, 27, 4, true),
                new Employee(10, "Jack Anderson", "Engineering", 92_000, 31, 7, false),
                new Employee(11, "Kate Thompson", "HR", 78_000, 36, 11, true),
                new Employee(12, "Liam White", "Marketing", 71_000, 28, 5, false),
                new Employee(13, "Mia Harris", "Sales", 79_000, 34, 10, true),
                new Employee(14, "Noah Clark", "Engineering", 98_000, 29, 6, false),
                new Employee(15, "Olivia Lewis", "Marketing", 64_000, 25, 2, true),
                new Employee(16, "Paul Walker", "Sales", 85_000, 38, 13, false),
                new Employee(17, "Quinn Hall", "HR", 76_000, 33, 9, true),
                new Employee(18, "Ruby Allen", "Engineering", 115_000, 37, 14, false),
                new Employee(19, "Sam Young", "Marketing", 69_000, 31, 8, true),
                new Employee(20, "Tina King", "Sales", 81_000, 30, 7, false)
        );
    }

    public static void main(String[] args) {
        var employees = createEmployees();

        //  1. forEach — efekty uboczne na końcu pipeline'u 
        System.out.println("1. Wszystkie nazwiska:");
        employees.forEach(e -> System.out.println("   " + e.name()));

        //  2. filter po polach rekordu 
        System.out.println("\n2. Najlepiej zarabiający (> $80 000):");
        employees.stream()
                .filter(e -> e.salary() > 80_000)
                .forEach(e -> System.out.printf("   %s — $%.0f%n", e.name(), e.salary()));

        //  3. map → rekord-„krotka” (Java nie ma krotek, używamy record/Map.entry) 
        System.out.println("\n3. Pary (nazwisko, pensja):");
        record NameSalary(String name, double salary) {
        }   // lokalny record (JDK 16+)
        List<NameSalary> pairs = employees.stream()
                .map(e -> new NameSalary(e.name(), e.salary()))
                .toList();
        System.out.println("   " + pairs);

        //  4. findFirst — pierwszy pasujący 
        System.out.println("\n4. Pierwszy pracownik zdalny:");
        employees.stream()
                .filter(Employee::remote)
                .findFirst()
                .ifPresent(e -> System.out.printf("   %s (%s)%n", e.name(), e.department()));

        //  5. min/max + DoubleSummaryStatistics 
        // Strumień prymitywny liczy min/max/avg jednym przebiegiem.
        System.out.println("\n5. Statystyki:");
        var youngest = employees.stream().min(comparingInt(Employee::age));
        var oldest = employees.stream().max(comparingInt(Employee::age));
        System.out.printf("   Wiek: %d - %d%n",
                youngest.map(Employee::age).orElse(0),
                oldest.map(Employee::age).orElse(0));

        DoubleSummaryStatistics salaryStats =
                employees.stream().mapToDouble(Employee::salary).summaryStatistics();
        System.out.printf("   Pensje: $%.0f - $%.0f (śr. $%.2f)%n",
                salaryStats.getMin(), salaryStats.getMax(), salaryStats.getAverage());

        //  6. Suma + licznik za jednym przebiegiem 
        // teeing łączy dwa kolektory i scala ich wyniki.
        System.out.println("\n6. Średnia pensja (teeing = jeden przebieg, dwa kolektory):");
        double avg = employees.stream().collect(teeing(
                summingDouble(Employee::salary),
                counting(),
                (sum, cnt) -> cnt == 0 ? 0 : sum / cnt));
        System.out.printf("   Średnia: $%.2f%n", avg);

        //  7. groupingBy — grupowanie po dziale 
        System.out.println("\n7. Pracownicy wg działu:");
        Map<String, List<Employee>> byDept =
                employees.stream().collect(groupingBy(Employee::department));
        byDept.forEach((dept, members) -> System.out.printf("   %s: %s%n",
                dept,
                members.stream().map(Employee::name).collect(joining(", "))));

        //  8. Stream.concat — sklejenie dwóch przefiltrowanych strumieni 
        System.out.println("\n8. Seniorzy (10+ lat) LUB wysokie pensje (90k+):");
        var seniors = employees.stream().filter(e -> e.yearsExperience() >= 10);
        var highEarners = employees.stream().filter(e -> e.salary() >= 90_000);
        Stream.concat(seniors, highEarners)
                .forEach(e -> System.out.printf("   %s — %d lat, $%.0f%n",
                        e.name(), e.yearsExperience(), e.salary()));

        //  9. Indeksowanie → IntStream.range + limit 
        // Java nie ma enumerate; indeksujemy przez IntStream.
        System.out.println("\n9. Pierwszych 5 z indeksem:");
        IntStream.range(0, Math.min(5, employees.size()))
                .forEach(i -> System.out.printf("   [%d] %s%n", i, employees.get(i).name()));

        //  10. sort + ranking przez IntStream.rangeClosed 
        System.out.println("\n10. Top 5 wg pensji:");
        var topPaid = employees.stream()
                .sorted(comparingDouble(Employee::salary).reversed())
                .limit(5)
                .toList();
        IntStream.rangeClosed(1, topPaid.size())
                .forEach(rank -> System.out.printf("   #%d: %s — $%.0f%n",
                        rank, topPaid.get(rank - 1).name(), topPaid.get(rank - 1).salary()));

        //  11. partitioningBy — podział na dwa zbiory wg predykatu 
        // Zwraca Map<Boolean, List<...>> (zawsze obie wartości klucza).
        System.out.println("\n11. Zdalni vs biuro:");
        Map<Boolean, List<Employee>> byRemote =
                employees.stream().collect(partitioningBy(Employee::remote));
        System.out.printf("   Zdalni: %d, Biuro: %d%n",
                byRemote.get(true).size(), byRemote.get(false).size());

        //  12. groupingBy + summarizingDouble — statystyki pensji per dział 
        // Downstream-kolektor liczy min/max/avg/count w obrębie grupy.
        System.out.println("\n12. Statystyki pensji wg działu:");
        Map<String, DoubleSummaryStatistics> deptStats = employees.stream()
                .collect(groupingBy(Employee::department, summarizingDouble(Employee::salary)));
        deptStats.forEach((dept, s) -> System.out.printf(
                "   %s: count=%d, $%.0f - $%.0f, śr. $%.0f%n",
                dept, s.getCount(), s.getMin(), s.getMax(), s.getAverage()));

        //  13. allMatch / anyMatch / noneMatch 
        System.out.println("\n13. Zapytania logiczne:");
        System.out.println("   Wszyscy pełnoletni (18+): " + employees.stream().allMatch(e -> e.age() >= 18));
        System.out.println("   Ktokolwiek zdalny: " + employees.stream().anyMatch(Employee::remote));
        System.out.println("   Brak milionerów: " + employees.stream().noneMatch(e -> e.salary() >= 1_000_000));

        //  14. skip + limit — paginacja 
        System.out.println("\n14. Paginacja (strona 2, 5/str.):");
        int page = 2, pageSize = 5;
        employees.stream()
                .skip((long) (page - 1) * pageSize)
                .limit(pageSize)
                .forEach(e -> System.out.printf("   %s — %s%n", e.name(), e.department()));

        //  15. Złożony pipeline: wiele filtrów → sort → toList 
        System.out.println("\n15. Engineering, zdalni, > 90k, wg doświadczenia:");
        employees.stream()
                .filter(e -> e.department().equals("Engineering"))
                .filter(Employee::remote)
                .filter(e -> e.salary() > 90_000)
                .sorted(comparingInt(Employee::yearsExperience).reversed())
                .forEach(e -> System.out.printf("   %s — %d lat, $%.0f%n",
                        e.name(), e.yearsExperience(), e.salary()));

        //  16. Ten sam strumień → różne kolekcje 
        System.out.println("\n16. Te same dane, różne kolekcje:");
        String[] names = {"alice", "bob", "alice", "charlie", "bob"};
        List<String> asList = Arrays.stream(names).toList();
        Set<String> asSet = Arrays.stream(names).collect(toSet());
        System.out.println("   List (kolejność + duplikaty): " + asList);
        System.out.println("   Set  (tylko unikalne):        " + asSet);

        // Dodatkowe wzorce specyficzne dla Java Stream API:
        javaSpecificCollectors(employees);
        comparatorsAndSorting(employees);
        primitiveStreamsAndInfinite();
        unzipScanPeekAnalogs();
    }

    //  Kolektory: deklaratywne agregacje na strumieniach 
    static void javaSpecificCollectors(List<Employee> employees) {
        System.out.println("\n Kolektory specyficzne dla Javy ");

        // toMap — indeks id → nazwisko (z funkcją scalającą na wypadek kolizji klucza)
        Map<Integer, String> byId = employees.stream()
                .collect(toMap(Employee::id, Employee::name, (a, b) -> a));
        System.out.println("toMap id→nazwisko, id=3: " + byId.get(3));

        // groupingBy + counting — liczność per dział
        Map<String, Long> headcount = employees.stream()
                .collect(groupingBy(Employee::department, counting()));
        System.out.println("Liczność per dział: " + headcount);

        // groupingBy + mapping(downstream) + joining — nazwiska per dział
        Map<String, String> namesByDept = employees.stream()
                .collect(groupingBy(Employee::department,
                        mapping(Employee::name, joining(", "))));
        System.out.println("Nazwiska per dział: " + namesByDept);

        // groupingBy + averagingDouble — średnia pensja per dział, posortowane (TreeMap)
        Map<String, Double> avgByDept = employees.stream()
                .collect(groupingBy(Employee::department, TreeMap::new,
                        averagingDouble(Employee::salary)));
        System.out.println("Średnia pensja per dział (sorted): " + avgByDept);

        // grupowanie wielopoziomowe: dział → (zdalny? → nazwiska)
        Map<String, Map<Boolean, List<String>>> nested = employees.stream()
                .collect(groupingBy(Employee::department,
                        partitioningBy(Employee::remote,
                                mapping(Employee::name, toList()))));
        System.out.println("Zagnieżdżone (dział→zdalny?→nazwiska): " + nested);

        // filtering (JDK 9+) downstream — zachowuje klucze grup bez pasujących elementów,
        // w odróżnieniu od filter() przed groupingBy
        Map<String, List<String>> remotePerDept = employees.stream()
                .collect(groupingBy(Employee::department,
                        filtering(Employee::remote, mapping(Employee::name, toList()))));
        System.out.println("Zdalni per dział (filtering): " + remotePerDept);

        // collectingAndThen — kolekcja + transformacja końcowa (tu: niemutowalna lista)
        List<String> engineers = employees.stream()
                .filter(e -> e.department().equals("Engineering"))
                .map(Employee::name)
                .collect(collectingAndThen(toList(), List::copyOf));
        System.out.println("Inżynierowie (immutable): " + engineers);

        // flatMap — „spłaszczenie”: wszystkie unikalne słowa z nazwisk
        Set<String> tokens = employees.stream()
                .flatMap(e -> Arrays.stream(e.name().toLowerCase().split(" ")))
                .collect(toCollection(TreeSet::new));
        System.out.println("Unikalne tokeny z nazwisk: " + tokens.size() + " → " + tokens);

        // reduce (3-arg) — bezpieczny dla równoległości odpowiednik fold:
        // identity, akumulator, kombinator
        double totalPayroll = employees.parallelStream()
                .reduce(0.0, (sum, e) -> sum + e.salary(), Double::sum);
        System.out.printf("Suma pensji (parallel reduce): $%.0f%n", totalPayroll);
    }

    //  Łączenie komparatorów (thenComparing/reversed) 
    static void comparatorsAndSorting(List<Employee> employees) {
        System.out.println("\n Sortowanie wielokluczowe ");

        // dział rosnąco, w obrębie działu pensja malejąco, dalej nazwisko
        var sorted = employees.stream()
                .sorted(comparing(Employee::department)
                        .thenComparing(comparingDouble(Employee::salary).reversed())
                        .thenComparing(Employee::name))
                .limit(6)
                .toList();
        sorted.forEach(e -> System.out.printf("   %-12s %-22s $%.0f%n",
                e.department(), e.name(), e.salary()));
    }

    //  Strumienie prymitywne i nieskończone 
    static void primitiveStreamsAndInfinite() {
        System.out.println("\n Strumienie prymitywne i nieskończone ");

        // IntStream + summaryStatistics — sum/avg/min/max/count za jednym razem
        var stats = IntStream.rangeClosed(1, 100).summaryStatistics();
        System.out.printf("1..100 → suma=%d, śr.=%.1f%n", stats.getSum(), stats.getAverage());

        // Stream.iterate (3-arg, JDK 9+) — leniwy strumień z warunkiem stopu (jak pętla for)
        List<Integer> powersOfTwo = Stream.iterate(1, n -> n <= 256, n -> n * 2).toList();
        System.out.println("Potęgi 2 (iterate z warunkiem): " + powersOfTwo);

        // nieskończony Stream.iterate + limit
        List<Long> fib = Stream.iterate(new long[]{0, 1}, a -> new long[]{a[1], a[0] + a[1]})
                .limit(10)
                .map(a -> a[0])
                .toList();
        System.out.println("Fibonacci(10): " + fib);

        // takeWhile / dropWhile (JDK 9+) — warunkowe pobieranie/pomijanie prefiksu
        List<Integer> nums = List.of(2, 4, 6, 7, 8, 10);
        System.out.println("takeWhile parzyste: " + nums.stream().takeWhile(n -> n % 2 == 0).toList());
        System.out.println("dropWhile parzyste: " + nums.stream().dropWhile(n -> n % 2 == 0).toList());

        // boxed — z prymitywnego do Stream<Integer> gdy potrzebny kolektor
        Map<Boolean, List<Integer>> evenOdd = IntStream.rangeClosed(1, 10)
                .boxed()
                .collect(partitioningBy(n -> n % 2 == 0));
        System.out.println("Parzyste/nieparzyste: " + evenOdd);
    }

    //  unzip / suma narastająca / lookahead 
    static void unzipScanPeekAnalogs() {
        System.out.println("\n unzip / scan / peekable ");

        // unzip — rozbicie strumienia par na dwie listy przez teeing + mapping.
        record Pair(int n, char c) {
        }
        var pairs = List.of(new Pair(1, 'a'), new Pair(2, 'b'), new Pair(3, 'c'));
        Map.Entry<List<Integer>, List<Character>> unzipped = pairs.stream().collect(teeing(
                mapping(Pair::n, toList()),
                mapping(Pair::c, toList()),
                Map::entry));
        System.out.println("unzip: nums=" + unzipped.getKey() + ", chars=" + unzipped.getValue());

        // scan (suma narastająca) — w JDK 21 brak czystego odpowiednika.
        // Wariant ze stanem (UWAGA: nie nadaje się do parallel()). Czysto → Gatherers (JDK 24+).
        int[] acc = {0};
        List<Integer> runningSum = Stream.of(1, 2, 3, 4)
                .map(x -> acc[0] += x)
                .toList();
        System.out.println("running sum (stateful map): " + runningSum + "   // JDK 24+: Gatherers.scan");

        // peekable — Stream nie ma podglądu następnego elementu.
        // Najbliżej: praca na liście po indeksie albo Gatherers.windowSliding(2) (JDK 24+).
        // Uwaga: Stream.peek() to hak diagnostyczny w trakcie pipeline'u, nie lookahead.
        System.out.println("peekable: brak odpowiednika 1:1 — patrz windowSliding w pliku obok");

        System.out.println("\nadvanced_iterator_patterns — sekcja wykonana");
    }
    
}