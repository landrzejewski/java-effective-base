package pl.training.bank.service;

import pl.training.bank.model.Account;
import pl.training.bank.model.AccountNumber;
import pl.training.bank.model.InsufficientFundsException;
import pl.training.bank.model.Money;

import java.util.Currency;

public interface Bank {

    Account createAccount(Currency currency, boolean isPremium);

    void deposit(AccountNumber number, Money amount) throws AccountNotFoundException;

    void withdraw(AccountNumber number, Money amount) throws AccountNotFoundException, InsufficientFundsException;

    void transfer(AccountNumber fromNumber, AccountNumber toNumber, Money amount) throws AccountNotFoundException, InsufficientFundsException;

}
