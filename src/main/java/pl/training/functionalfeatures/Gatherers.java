package pl.training.functionalfeatures;

import java.util.HashSet;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Gatherer;
import java.util.stream.Stream;

/*
 *  Gatherer to mechanizm definiowania WŁASNYCH operacji POŚREDNICH (intermediate)
 *  na strumieniach
 *
 *  MODEL: Gatherer<T, A, R>  —  T = wejście, A = stan, R = wyjście.
 *  Składa się z (maks.) czterech funkcji:
 *
 *    initializer : Supplier<A>                       tworzy prywatny, mutowalny stan    (opcjonalny)
 *    integrator  : Integrator<A,T,R>                 przetwarza 1 element, emituje       (WYMAGANY)
 *    combiner    : BinaryOperator<A>                 łączy stany przy równoległości      (opcjonalny)
 *    finisher    : BiConsumer<A,Downstream<R>>       domknięcie po wszystkich elementach (opcjonalny)
 *
 *  Integrator zwraca boolean:  true = kontynuuj, false = short-circuit (przerwij etap).
 *  Emisja: down.push(element) — zwraca false, gdy odbiorca dalej nie chce danych
 *  (np. stoi za nim limit). Ten wynik NALEŻY propagować.
 *
 *  Fabryki integratora:
 *    Integrator.of(...)        — może sam przerywać (zwracać false z logiki)
 *    Integrator.ofGreedy(...)  — deklaruje, że nigdy nie przerywa sam z siebie (optymalizacje)
 *
 *  Fabryki gatherera:
 *    Gatherer.ofSequential(...) — bez combinera => etap ZAWSZE sekwencyjny (większość przypadków)
 *    Gatherer.of(...)           — z combinerem  => etap może działać równolegle
 * ============================================================================
 */
public class Gatherers {

    static void builtIns() {
        // windowFixed(n): rozłączne okna stałego rozmiaru; ostatnie bywa krótsze.
        System.out.println("windowFixed(3)   = " + Stream.of(1, 2, 3, 4, 5, 6, 7)
                .gather(java.util.stream.Gatherers.windowFixed(3)).toList());          // [[1,2,3],[4,5,6],[7]]

        // windowSliding(n): okno przesuwne o krok 1 — np. różnice między sąsiadami.
        System.out.println("windowSliding(3) = " + Stream.of(1, 2, 3, 4, 5)
                .gather(java.util.stream.Gatherers.windowSliding(3)).toList());        // [[1,2,3],[2,3,4],[3,4,5]]

        // fold(init, acc): redukcja do JEDNEGO elementu (stateful, ściśle sekwencyjny;
        // nie wymaga tożsamości ani asocjatywności — inaczej niż reduce).
        System.out.println("fold             = " + Stream.of("a", "b", "c")
                .gather(java.util.stream.Gatherers.fold(() -> "", (acc, x) -> acc + x)).findFirst());  // Optional[abc]

        // scan(init, acc): jak fold, ale emituje KAŻDY stan pośredni (skan prefiksowy).
        System.out.println("scan (sumy)      = " + Stream.of(1, 2, 3, 4)
                .gather(java.util.stream.Gatherers.scan(() -> 0, Integer::sum)).toList());             // [1,3,6,10]

        // mapConcurrent(N, mapper): mapuje współbieżnie na wątkach wirtualnych
        // (max N naraz), ZACHOWUJĄC kolejność. Najlepsze do równoległego I/O.
        System.out.println("mapConcurrent(2) = " + Stream.of("a", "b", "c", "d")
                .gather(java.util.stream.Gatherers.mapConcurrent(2, Gatherers::slowFetch)).toList()); // [A,B,C,D]
    }

    static String slowFetch(String s) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException ignored) {
        }
        return s.toUpperCase();
    }

    static <T, K> Gatherer<T, ?, T> distinctBy(Function<? super T, ? extends K> key) {
        return Gatherer.ofSequential(
                HashSet<K>::new,                                  // initializer: stan
                Gatherer.Integrator.ofGreedy((seen, el, down) -> {
                    if (seen.add(key.apply(el))) return down.push(el);  // pierwszy raz -> emituj
                    return true;                                        // duplikat -> pomiń, jedź dalej
                })
        );
    }

    static <T> Gatherer<T, ?, T> takeWhileInclusive(Predicate<? super T> p) {
        Gatherer.Integrator<Void, T, T> integrator = (state, el, down) -> {
            boolean cont = down.push(el);      // najpierw emituj bieżący
            return cont && p.test(el);         // gdy predykat już nie zachodzi -> koniec PO tym elemencie
        };
        return Gatherer.ofSequential(integrator);
    }

    static Gatherer<Integer, ?, Double> runningAverage() {
        class Acc {
            long sum;
            long count;
        }
        return Gatherer.ofSequential(
                Acc::new,
                Gatherer.Integrator.ofGreedy((a, n, down) -> {
                    a.sum += n;
                    a.count++;
                    return down.push((double) a.sum / a.count);
                })
        );
    }

    record Person(String name, String city) {
    }

    static void customs() {
        System.out.println("distinctBy(city)        = " + Stream.of(
                        new Person("Anna", "Poznań"),
                        new Person("Bartek", "Poznań"),
                        new Person("Celina", "Wrocław"))
                .gather(distinctBy(Person::city)).map(Person::name).toList()); // [Anna, Celina]

        System.out.println("takeWhileInclusive(<4)  = " + Stream.of(1, 2, 3, 4, 5, 1)
                .gather(takeWhileInclusive(n -> n < 4)).toList());             // [1, 2, 3, 4]

        System.out.println("runningAverage          = " + Stream.of(2, 4, 6)
                .gather(runningAverage()).toList());                           // [2.0, 3.0, 4.0]

        /* KOMPOZYCJA — łańcuch gather().gather() */
        System.out.println("gather().gather()       = " + Stream.of(1, 2, 3, 4, 5, 6)
                .gather(takeWhileInclusive(n -> n < 5))   // 1,2,3,4,5
                .gather(java.util.stream.Gatherers.windowFixed(2))         // [[1,2],[3,4],[5]]
                .toList());
    }

    public static void main(String[] args) {
        builtIns();
        customs();
    }
}