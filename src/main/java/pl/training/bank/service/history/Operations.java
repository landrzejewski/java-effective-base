package pl.training.bank.service.history;

import java.util.ArrayList;
import java.util.List;

public final class Operations {

    private final List<Operation> operations = new ArrayList<>();

    public void record(final Operation operation) {
        operations.add(operation);
    }

}
