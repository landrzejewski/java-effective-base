import java.util.Optional;
import java.util.function.Function;

public sealed interface Either<E, V> permits Failure, Success {

    static <E, V> Either<E, V> error(final E error) {
        return new Failure<>(error);
    }

    static <E, V> Either<E, V> success(final V value) {
        return new Success<>(value);
    }

    default <B> Either<E, B> map(final Function<V, B> mapper) {
        return switch (this) {
            case Success<E, V>(V v) -> success(mapper.apply(v));
            case Failure<E, V>(E e) -> error(e);
        };
    }

    default <B> Either<B, V> mapError(final Function<E, B> mapper) {
        return switch (this) {
            case Success<E, V>(V v) -> success(v);
            case Failure<E, V>(E e) -> error(mapper.apply(e));
        };
    }

    default <B> Either<E, B> flatMap(final Function<V, Either<E, B>> mapper) {
        return switch (this) {
            case Success<E, V>(V v) -> mapper.apply(v);
            case Failure<E, V>(E e) -> error(e);
        };
    }

    default Optional<V> toOptional() {
        return switch (this) {
            case Success<E, V>(V v) -> Optional.of(v);
            case Failure<E, V> _ -> Optional.empty();
        };
    }

}

record Failure<E, V>(E error) implements Either<E, V> {}

record Success<E, V>(V value) implements Either<E, V> {}

public Either<String, Double> divide(final double value, final double factor) {
    if (factor == 0) {
        return Either.error("Divide by zero");
    }
    return Either.success(value / factor);
}

void main() {
    var finalResult = divide(10, 0)
            .flatMap(result -> Either.success(result * 2))
            .mapError(IllegalStateException::new);

    switch (finalResult) {
        case Success(Double v) -> System.out.println("Final result is: " + v);
        case Failure(IllegalStateException e) -> System.out.println("Error: " + e);
    }
}

