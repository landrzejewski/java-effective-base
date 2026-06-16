package pl.training.bank;

import pl.training.bank.model.AccountNumber;
import pl.training.bank.persistence.HashMapAccountRepository;
import pl.training.bank.service.*;
import pl.training.bank.service.history.Operations;
import pl.training.bank.service.reporting.Reports;

import java.time.Clock;
import java.time.ZoneId;
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
        var clock = Clock.system(ZoneId.of("Europe/Warsaw"));
        return new BankOperationsProxy(new BankService(accountNumberSupplier(), accountRepository), new Operations(), clock);
    }

}
