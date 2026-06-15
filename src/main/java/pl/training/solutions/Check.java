package pl.training.solutions;

import java.util.Objects;

/*
Minimal self-check harness shared by all solution modules in this package.

Mirrors pl.training.exercises.Check. In the solutions every exercise is implemented, so running a
module's main() should print only [PASS] lines — they double as a regression test for the answer key.
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
