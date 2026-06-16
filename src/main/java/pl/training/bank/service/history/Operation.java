package pl.training.bank.service.history;

import pl.training.bank.model.AccountNumber;
import pl.training.bank.model.Money;

import java.time.LocalDateTime;

public sealed interface Operation {

    AccountNumber number();

    LocalDateTime timestamp();

    record AccountCreated(AccountNumber number, LocalDateTime timestamp) implements Operation {
    }

    record Deposit(AccountNumber number, Money amount, LocalDateTime timestamp) implements Operation {
    }

    record Withdraw(AccountNumber number, Money amount, LocalDateTime timestamp) implements Operation {
    }

    record Transfer(AccountNumber number, AccountNumber toNumber, Money amount, LocalDateTime timestamp) implements Operation {
    }

}
