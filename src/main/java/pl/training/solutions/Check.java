package pl.training.solutions;

import java.util.Objects;

/*
Minimal self-check harness shared by all exercise modules in this package.

Each exercise returns a value; main() compares it against the expected value with expect(...).
Unsolved stubs throw UnsupportedOperationException — expect(...) catches it and prints [TODO] so a
single unfinished exercise never aborts the whole run.
*/
public final class Check {

    private Check() {}

    public static void expect(String name, Object actual, Object expected) {
        if (Objects.equals(actual, expected)) {
            System.out.println("  [PASS] " + name + " = " + actual);
        } else {
            System.out.println("  [FAIL] " + name + " expected " + expected + " but was " + actual);
        }
    }

    // Overload that runs a supplier, catching the stub's UnsupportedOperationException as [TODO].
    public static void expect(String name, ThrowingSupplier supplier, Object expected) {
        try {
            expect(name, supplier.get(), expected);
        } catch (UnsupportedOperationException e) {
            System.out.println("  [TODO] " + name + " not implemented yet");
        } catch (RuntimeException e) {
            System.out.println("  [FAIL] " + name + " threw " + e);
        }
    }

    @FunctionalInterface
    public interface ThrowingSupplier {
        Object get();
    }
}
