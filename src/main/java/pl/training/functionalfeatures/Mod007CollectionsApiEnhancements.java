package pl.training.functionalfeatures;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SequencedCollection;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public final class Mod007CollectionsApiEnhancements {

    private Mod007CollectionsApiEnhancements() {}

    /*
    Immutable collection factory methods (Java 9+)

    List.of / Set.of / Map.of / Map.ofEntries build compact, unmodifiable collections in one
    expression — replacing the old Arrays.asList / unmodifiableMap boilerplate.

    Key properties and pitfalls:
    - They are UNMODIFIABLE: add/remove/put/clear throw UnsupportedOperationException.
    - They reject null elements, keys and values with NullPointerException (unlike HashMap/ArrayList).
    - Set.of and Map.of reject DUPLICATE elements/keys at construction with IllegalArgumentException.
    - Map.of has overloads up to 10 key/value pairs; beyond that use Map.ofEntries(Map.entry(...)).
    - List.copyOf / Set.copyOf / Map.copyOf take a snapshot of an existing collection as an immutable
      copy (and return the argument unchanged if it is already a suitable immutable instance).
    */
    static void factoryMethods() {
        System.out.println("[Section 1] immutable factory methods");

        List<String> list = List.of("a", "b", "c");
        Set<Integer> set = Set.of(1, 2, 3);
        Map<String, Integer> map = Map.of("one", 1, "two", 2);
        Map<String, Integer> big = Map.ofEntries(
                Map.entry("a", 1), Map.entry("b", 2), Map.entry("c", 3));

        System.out.println("  list = " + list + ", set = " + set);
        System.out.println("  map  = " + map + ", ofEntries = " + big);

        // Defensive immutable copy of a mutable source.
        List<String> mutable = new ArrayList<>(List.of("x", "y"));
        List<String> snapshot = List.copyOf(mutable);
        mutable.add("z"); // does not affect the snapshot
        System.out.println("  mutable=" + mutable + ", snapshot=" + snapshot);

        try {
            list.add("d");
        } catch (UnsupportedOperationException e) {
            System.out.println("  list.add threw UnsupportedOperationException (immutable)");
        }
    }

    /*
    New default methods on Collection and List (Java 8+)

    These default methods push common loops into the collection itself:
    - Collection.removeIf(predicate) — remove all matching elements in one pass (replaces an
      Iterator + remove() loop, and avoids ConcurrentModificationException).
    - List.replaceAll(unaryOperator) — transform each element in place.
    - forEach(consumer) — internal iteration over a collection.
    Note: these mutate the collection, so the receiver must be modifiable (not a List.of(...) result).
    */
    static void collectionDefaults() {
        System.out.println("[Section 2] Collection/List default methods");

        List<Integer> numbers = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6));
        numbers.removeIf(n -> n % 2 == 0);          // drop evens
        System.out.println("  after removeIf(even) = " + numbers);

        List<String> words = new ArrayList<>(List.of("foo", "bar", "baz"));
        words.replaceAll(String::toUpperCase);      // transform in place
        System.out.println("  after replaceAll(upper) = " + words);
    }

    /*
    New default methods on Map (Java 8+)

    These replace the classic "get, check for null, put" idioms with single atomic-looking calls:
    - getOrDefault(key, fallback) — read with a default instead of null.
    - putIfAbsent(key, value)     — insert only when the key is missing.
    - computeIfAbsent(key, fn)    — lazily build and cache a value; ideal for multimaps / memoisation.
    - computeIfPresent(key, fn)   — update only when the key already exists.
    - compute(key, fn)            — recompute from the current value (may be null).
    - merge(key, value, fn)       — combine an existing value with a new one, or insert if absent.
                                     The canonical counting / accumulation tool.
    - forEach(biConsumer)         — iterate over entries without an entrySet() loop.
    */
    static void mapDefaults() {
        System.out.println("[Section 3] Map default methods");

        Map<String, Integer> stock = new LinkedHashMap<>(Map.of("apple", 3, "pear", 5));

        System.out.println("  getOrDefault(missing) = " + stock.getOrDefault("kiwi", 0));

        stock.putIfAbsent("apple", 99);             // ignored, already present
        stock.putIfAbsent("kiwi", 1);               // inserted
        System.out.println("  after putIfAbsent = " + stock);

        // Word-frequency counting with merge — the idiomatic replacement for get/null-check/put.
        Map<String, Integer> freq = new LinkedHashMap<>();
        for (String w : List.of("a", "b", "a", "c", "b", "a")) {
            freq.merge(w, 1, Integer::sum);
        }
        System.out.println("  merge word counts = " + freq);

        // Building a multimap with computeIfAbsent — the value (a List) is created on first access.
        Map<Integer, List<String>> byLength = new LinkedHashMap<>();
        for (String w : List.of("hi", "bye", "yo", "code")) {
            byLength.computeIfAbsent(w.length(), k -> new ArrayList<>()).add(w);
        }
        System.out.println("  computeIfAbsent multimap = " + byLength);

        stock.computeIfPresent("pear", (k, v) -> v + 10);   // updates existing
        stock.compute("apple", (k, v) -> v == null ? 1 : v + 1);
        System.out.println("  after compute* = " + stock);

        System.out.print("  forEach: ");
        stock.forEach((k, v) -> System.out.print(k + "=" + v + " "));
        System.out.println();
    }

    /*
    ConcurrentHashMap improvements (Java 8+)

    ConcurrentHashMap implements the same default methods but with ATOMIC, thread-safe semantics —
    merge/compute/computeIfAbsent perform the read-modify-write as one atomic operation, so they are
    the correct way to accumulate from many threads (a plain HashMap would corrupt or lose updates).

    It also adds bulk parallel operations driven by a parallelism threshold:
    - forEach(threshold, action)
    - search(threshold, fn)     — return the first non-null result.
    - reduce(threshold, transformer, reducer)
    A threshold of 1 maximises parallelism; Long.MAX_VALUE forces sequential execution.
    */
    static void concurrentHashMap() {
        System.out.println("[Section 4] ConcurrentHashMap");

        var counts = new ConcurrentHashMap<String, Long>();
        // Atomic accumulation — safe even when called concurrently from many threads.
        List.of("a", "b", "a", "a", "b").forEach(k -> counts.merge(k, 1L, Long::sum));
        System.out.println("  atomic merge counts = " + counts);

        counts.computeIfAbsent("c", k -> 0L);   // atomic insert-if-missing
        System.out.println("  after computeIfAbsent = " + counts);

        // Bulk parallel reduce: total of all values (threshold 1 = allow parallelism).
        long sum = counts.reduceValues(1, Long::sum);
        System.out.println("  reduceValues sum = " + sum);

        // Bulk parallel search: first key whose value is >= 2.
        String hot = counts.search(1, (k, v) -> v >= 2 ? k : null);
        System.out.println("  search (value>=2) = " + hot);
    }

    /*
    Sequenced Collections (Java 21)

    SequencedCollection / SequencedSet / SequencedMap give every ordered collection a uniform API for
    its endpoints and for reversal — previously you needed type-specific calls (getFirst on Deque,
    list.get(size-1), etc.).
    - getFirst() / getLast()
    - addFirst() / addLast() / removeFirst() / removeLast()
    - reversed() — a reversed VIEW (changes write through to the backing collection).
    List, Deque, LinkedHashSet and LinkedHashMap all implement these interfaces.
    */
    static void sequencedCollections() {
        System.out.println("[Section 5] Sequenced Collections (Java 21)");

        SequencedCollection<String> seq = new ArrayList<>(List.of("first", "middle", "last"));
        System.out.println("  getFirst = " + seq.getFirst() + ", getLast = " + seq.getLast());

        seq.addFirst("new-head");
        seq.addLast("new-tail");
        System.out.println("  after addFirst/addLast = " + seq);

        System.out.println("  reversed view = " + seq.reversed());
    }

    public static void main(String[] args) {
        factoryMethods();
        collectionDefaults();
        mapDefaults();
        concurrentHashMap();
        sequencedCollections();
        System.out.println("Mod007CollectionsApiEnhancements finished");
    }
}
