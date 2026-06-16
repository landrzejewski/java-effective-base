package pl.training.bank.service;

import pl.training.bank.BankException;
import pl.training.bank.model.AccountNumber;

public class AccountNotFoundException extends BankException {

    private final AccountNumber accountNumber;

    public AccountNotFoundException(final AccountNumber accountNumber) {
        this.accountNumber = accountNumber;
    }

    public AccountNumber getAccountNumber() {
        return accountNumber;
    }

}
