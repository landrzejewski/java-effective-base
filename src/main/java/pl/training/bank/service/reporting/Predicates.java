package pl.training.bank.service.reporting;

import pl.training.bank.model.Account;
import pl.training.bank.model.Money;
import pl.training.bank.model.PremiumAccount;

import java.util.Currency;
import java.util.function.Predicate;

public final class Predicates {

    private Predicates() {
    }

    public static Predicate<Account> all() {
        return account -> true;
    }

    public static Predicate<Account> premium() {
        return account -> account instanceof PremiumAccount;
    }

    public static Predicate<Account> inCurrency(final Currency currency) {
        return account -> currency ==  account.getCurrency();
    }

    public static Predicate<Account> balanceOver(final Money amount) {
        return inCurrency(amount.currency())
                .and(account -> account.getBalance().isGreaterOrEqual(amount));
    }

}
