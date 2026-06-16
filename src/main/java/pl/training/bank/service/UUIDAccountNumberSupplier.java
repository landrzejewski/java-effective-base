package pl.training.bank.service;

import pl.training.bank.model.AccountNumber;

import java.util.UUID;
import java.util.function.Supplier;

public class UUIDAccountNumberSupplier implements Supplier<AccountNumber> {

    @Override
    public AccountNumber get() {
        return new AccountNumber(UUID.randomUUID().toString());
    }

}
