package pl.training.bank.service;

import pl.training.bank.BankException;
import pl.training.bank.model.AccountNumber;

public class AccountNotFoundException extends BankException {

    private AccountNumber accountNumber;

    public AccountNotFoundException(AccountNumber accountNumber) {
        this.accountNumber = accountNumber;
    }

    public AccountNumber getAccountNumber() {
        return accountNumber;
    }

}
