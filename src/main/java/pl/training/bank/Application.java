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
        var reports = bankConfiguration.reports();

        var firstAccount = bank.createAccount(DEFAULT_CURRENCY, false);
        var secondAccount = bank.createAccount(DEFAULT_CURRENCY, true);
        bank.deposit(firstAccount.getNumber(), Money.of(100, DEFAULT_CURRENCY));
        bank.withdraw(firstAccount.getNumber(), Money.of(50, DEFAULT_CURRENCY));
        bank.transfer(firstAccount.getNumber(), secondAccount.getNumber(), Money.of(50, DEFAULT_CURRENCY));

        var totalBalance = reports.totalBalance(DEFAULT_CURRENCY);
        System.out.println("Total balance: " + totalBalance);
        var totalBalanceByCurrency = reports.totalBalanceByCurrency();
        System.out.println("Total balance by currency: " + totalBalanceByCurrency);
        var accountsByType = reports.accountsByType();
        System.out.println("Accounts by type: " + accountsByType);
    }

}
