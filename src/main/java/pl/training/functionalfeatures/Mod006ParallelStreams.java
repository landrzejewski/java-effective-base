package pl.training.functionalfeatures;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public final class Mod006ParallelStreams {

    private Mod006ParallelStreams() {}

    /*
    Streams vs collections — benefits and trade-offs

    A Collection is about storage: it holds every element in memory and you decide when and how to
    iterate (external iteration). A Stream is about computation: it describes a pipeline of operations
    over a source and decides itself how to iterate (internal iteration), which is what makes laziness,
    short-circuiting and parallelism possible.

    Benefits of streams:
    - Declarative: you say WHAT to compute (filter/map/reduce), not HOW to loop.
    - Composable: intermediate operations chain into a single pass; nothing materialises in between.
    - Lazy and short-circuiting: work happens only at the terminal operation and can stop early
      (findFirst, anyMatch, limit).
    - Trivially parallelisable: the same pipeline runs across cores by adding .parallel().

    Trade-offs:
    - One-shot: a stream cannot be reused; a collection can be iterated many times.
    - No random access / indexing, no in-place mutation of the source.
    - Harder to debug step-by-step than an explicit loop; stack traces are deeper.
    - For tiny or trivial work a plain loop is faster and clearer.

    Rule of thumb: reach for a collection when you need storage, indexing or repeated traversal;
    reach for a stream when you are transforming or aggregating data in a single pass.
    */
    static void streamsVsCollections() {
        System.out.println("[Section 1] streams vs collections");

        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // External iteration over a collection — caller controls the loop.
        long evenLoop = 0;
        for (int n : numbers) {
            if (n % 2 == 0) {
                evenLoop++;
            }
        }

        // Internal iteration over a stream — the pipeline controls the loop.
        long evenStream = numbers.stream().filter(n -> n % 2 == 0).count();

        System.out.println("  even via loop   = " + evenLoop);
        System.out.println("  even via stream = " + evenStream);
    }

    /*
    Going parallel: parallelStream() vs stream().parallel()

    There are two ways to obtain a parallel stream and they are equivalent:
    - collection.parallelStream() — start parallel directly from a Collection.
    - stream().parallel()         — turn any existing sequential stream parallel.
    The matching .sequential() switches back. The LAST call wins for the whole pipeline; you cannot
    run one stage sequentially and another in parallel.

    Under the hood a parallel stream uses the fork/join framework: the source is recursively split via
    a Spliterator, each chunk is processed on a worker thread, and the partial results are combined.
    By default the work runs on the shared ForkJoinPool.commonPool(), sized to (cores - 1) plus the
    submitting thread.
    */
    static void goingParallel() {
        System.out.println("[Section 2] going parallel");

        List<Integer> numbers = IntStream.rangeClosed(1, 12).boxed().toList();

        long sequential = numbers.stream().filter(n -> n % 2 == 0).count();
        long parallelA  = numbers.parallelStream().filter(n -> n % 2 == 0).count();
        long parallelB  = numbers.stream().parallel().filter(n -> n % 2 == 0).count();

        System.out.println("  sequential          = " + sequential);
        System.out.println("  parallelStream()    = " + parallelA);
        System.out.println("  stream().parallel() = " + parallelB);

        // The common pool's parallelism is derived from the number of available cores.
        System.out.println("  available processors = " + Runtime.getRuntime().availableProcessors());
        System.out.println("  common pool parallelism = " + ForkJoinPool.getCommonPoolParallelism());
    }

    /*
    When parallelism pays off

    Parallelism is not free: splitting the source, scheduling tasks and merging results all add
    overhead. It pays off only when there is enough work to amortise that overhead. Consider:

    - N x Q must be large: number of elements (N) times cost per element (Q). A few thousand cheap
      operations will usually be SLOWER in parallel; millions of elements, or expensive per-element
      work, benefit.
    - The source must split cheaply and evenly. ArrayList, arrays and IntStream.range split in O(1)
      into balanced halves (good). LinkedList, Stream.iterate and most I/O-backed sources split
      poorly (bad), so parallelism rarely helps.
    - The terminal operation must combine cheaply. reduce/sum/count combine in O(1); collecting into
      an ordered List or a TreeMap has merge overhead.

    The measurements below use System.nanoTime() purely to illustrate the SHAPE of the difference.
    This is NOT a rigorous benchmark — JIT warm-up, GC and CPU scaling make micro-timings noisy. Use
    JMH (Java Microbenchmark Harness) for real numbers.
    */
    static void whenItPaysOff() {
        System.out.println("[Section 3] when parallelism pays off");

        // Expensive per-element work over many elements — a good candidate for parallelism.
        long n = 2_000_000L;

        long t0 = System.nanoTime();
        long seq = LongStream.rangeClosed(1, n).map(Mod006ParallelStreams::heavy).sum();
        long t1 = System.nanoTime();
        long par = LongStream.rangeClosed(1, n).parallel().map(Mod006ParallelStreams::heavy).sum();
        long t2 = System.nanoTime();

        System.out.println("  result equal? " + (seq == par));
        System.out.printf ("  sequential ~ %d ms%n", (t1 - t0) / 1_000_000);
        System.out.printf ("  parallel   ~ %d ms (indicative only, not a benchmark)%n", (t2 - t1) / 1_000_000);

        // Poorly-splittable source: LinkedList must be walked to be split, so parallelism is wasted.
        List<Integer> linked = new LinkedList<>(IntStream.rangeClosed(1, 100_000).boxed().toList());
        List<Integer> array  = new ArrayList<>(linked);
        System.out.println("  LinkedList splits poorly; ArrayList/array split in O(1) — prefer the latter for parallel work");
        System.out.println("  (sizes: linked=" + linked.size() + ", array=" + array.size() + ")");
    }

    // A deliberately non-trivial per-element computation to give parallelism something to chew on.
    private static long heavy(long x) {
        long acc = x;
        for (int i = 0; i < 20; i++) {
            acc = (acc * 31 + i) % 1_000_003;
        }
        return acc % 7;
    }

    /*
    Correctness pitfalls in parallel streams

    Parallel pipelines must obey rules that sequential ones can get away with breaking:

    - No shared mutable state. A lambda that writes to a captured variable or external collection
      races across threads. Use a proper reduction (reduce / collect) instead of side effects.
    - Reduction must be associative and have a true identity. sum with identity 0 is fine;
      subtraction is not associative and gives nondeterministic results in parallel.
    - Ordering costs money. forEach does NOT guarantee encounter order in parallel; use
      forEachOrdered when order matters (it forces synchronisation and loses some speed).
    - Prefer groupingByConcurrent over groupingBy for parallel grouping: the concurrent collector
      merges into one ConcurrentHashMap instead of merging many per-thread maps.
    - unordered() can speed up operations like distinct/limit when you genuinely don't care about
      encounter order, by relieving the pipeline of order-preservation work.
    */
    static void pitfalls() {
        System.out.println("[Section 4] parallel pitfalls");

        List<Integer> numbers = IntStream.rangeClosed(1, 1000).boxed().toList();

        // WRONG conceptually: mutating shared state from a parallel forEach is a data race.
        // RIGHT: use a reduction. sum is associative with identity 0, so it is deterministic.
        int total = numbers.parallelStream().reduce(0, Integer::sum);
        System.out.println("  deterministic parallel sum = " + total);

        // forEach (unordered) vs forEachOrdered (preserves encounter order) on a parallel stream.
        var unordered = new StringBuilder();
        List.of("a", "b", "c", "d", "e").parallelStream()
                .forEachOrdered(unordered::append);
        System.out.println("  forEachOrdered preserves order = " + unordered);

        // Concurrent grouping: one shared ConcurrentHashMap, no per-thread merge step.
        Map<Boolean, List<Integer>> byParity = numbers.parallelStream()
                .collect(Collectors.groupingByConcurrent(x -> x % 2 == 0));
        System.out.println("  groupingByConcurrent buckets: even=" + byParity.get(true).size()
                + ", odd=" + byParity.get(false).size());

        // A ConcurrentHashMap is the right accumulator when you truly need shared mutable state.
        var counts = new ConcurrentHashMap<Boolean, Long>();
        numbers.parallelStream().forEach(x -> counts.merge(x % 2 == 0, 1L, Long::sum));
        System.out.println("  ConcurrentHashMap merge counts = " + counts);
    }

    public static void main(String[] args) {
        streamsVsCollections();
        goingParallel();
        whenItPaysOff();
        pitfalls();
        System.out.println("Mod006ParallelStreams finished");
    }
}
