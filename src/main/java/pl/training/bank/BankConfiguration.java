package pl.training.bank;

import pl.training.bank.model.AccountNumber;
import pl.training.bank.persistence.HashMapAccountRepository;
import pl.training.bank.service.AccountRepository;
import pl.training.bank.service.Bank;
import pl.training.bank.service.UUIDAccountNumberSupplier;
import pl.training.bank.service.reporting.Reports;

import java.util.function.Supplier;

public class BankConfiguration {

    private final AccountRepository accountRepository = new HashMapAccountRepository();

    private Supplier<AccountNumber> accountNumberSupplier() {
        return new UUIDAccountNumberSupplier();
    }

    public Reports reports() {
        return new Reports(accountRepository);
    }

    public Bank bank() {
        return new Bank(accountNumberSupplier(), accountRepository);
    }

}
