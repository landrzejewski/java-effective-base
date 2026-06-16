package pl.training.bank;

import pl.training.bank.model.AccountNumber;
import pl.training.bank.persistence.HashMapAccountRepository;
import pl.training.bank.service.AccountRepository;
import pl.training.bank.service.Bank;
import pl.training.bank.service.UUIDAccountNumberSupplier;

import java.util.function.Supplier;

public class BankConfiguration {

    private Supplier<AccountNumber> accountNumberSupplier() {
        return new UUIDAccountNumberSupplier();
    }

    private AccountRepository accountRepository() {
        return new HashMapAccountRepository();
    }

    public Bank bank() {
        return new Bank(accountNumberSupplier(), accountRepository());
    }

}
