package pl.training.bank.service;

import pl.training.bank.model.Account;
import pl.training.bank.model.AccountNumber;
import pl.training.bank.model.InsufficientFundsException;
import pl.training.bank.model.Money;
import pl.training.bank.service.history.Operation;
import pl.training.bank.service.history.Operation.Deposit;
import pl.training.bank.service.history.Operation.Withdraw;
import pl.training.bank.service.history.Operations;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Currency;

public final class BankOperationsProxy implements Bank {

    private final Bank bank;
    private final Operations operations;
    private final Clock clock;

    public BankOperationsProxy(Bank bank, Operations operations, Clock clock) {
        this.bank = bank;
        this.operations = operations;
        this.clock = clock;
    }

    @Override
    public Account createAccount(Currency currency, boolean isPremium) {
        var account = bank.createAccount(currency, isPremium);
        var operation = new Operation.AccountCreated(account.getNumber(), LocalDateTime.now(clock));
        operations.record(operation);
        return account;
    }

    @Override
    public void deposit(AccountNumber number, Money amount) throws AccountNotFoundException {
        bank.deposit(number, amount);
        var operation = new Deposit(number, amount, LocalDateTime.now(clock));
        operations.record(operation);
    }

    @Override
    public void withdraw(AccountNumber number, Money amount) throws AccountNotFoundException, InsufficientFundsException {
        bank.withdraw(number, amount);
        var operation = new Withdraw(number, amount, LocalDateTime.now(clock));
        operations.record(operation);
    }

    @Override
    public void transfer(AccountNumber fromNumber, AccountNumber toNumber, Money amount) throws AccountNotFoundException, InsufficientFundsException {
        bank.transfer(fromNumber, toNumber, amount);
        var operation = new Operation.Transfer(fromNumber, toNumber, amount, LocalDateTime.now(clock));
        operations.record(operation);
    }

}
