package pl.training.bank.service.reporting;

import java.util.stream.Stream;

@FunctionalInterface
public interface Aggregator<I, O> {

    O aggregate(Stream<I> stream);

}
