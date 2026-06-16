package pl.training.bank;

import pl.training.bank.model.InsufficientFundsException;
import pl.training.bank.model.Money;
import pl.training.bank.service.AccountNotFoundException;

import java.util.Currency;

public class Application {

    private final static Currency DEFAULT_CURRENCY =  Currency.getInstance("PLN");

    static void main() throws AccountNotFoundException, InsufficientFundsException {
        var bankConfiguration = new BankConfiguration();
        var bank = bankConfiguration.bank();
        var firstAccount = bank.createAccount(DEFAULT_CURRENCY);
        var secondAccount = bank.createAccount(DEFAULT_CURRENCY);
        bank.deposit(firstAccount.getNumber(), Money.of(100, DEFAULT_CURRENCY));
        bank.withdraw(firstAccount.getNumber(), Money.of(50, DEFAULT_CURRENCY));
        bank.transfer(firstAccount.getNumber(), secondAccount.getNumber(), Money.of(50, DEFAULT_CURRENCY));
    }

}
