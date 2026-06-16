package pl.training.bank.service.reporting;

import pl.training.bank.model.Account;
import pl.training.bank.model.AccountFormatter;
import pl.training.bank.model.Money;
import pl.training.bank.model.PremiumAccount;
import pl.training.bank.service.AccountRepository;

import java.util.*;
import java.util.function.Predicate;

import static java.lang.System.lineSeparator;
import static java.math.BigDecimal.ZERO;
import static java.util.stream.Collectors.*;

public final class Reports {

    private final AccountRepository accountRepository;

    public Reports(final AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Money totalBalance(Currency currency) {
        return accountRepository.findAll()
                .map(Account::getBalance)
                .filter(money -> money.hasCurrency(currency))
                .reduce(new Money(ZERO, currency), Money::add);
    }

    public Map<Currency, Money> totalBalanceByCurrency() {
        return accountRepository.findAll()
                .collect(toMap(Account::getCurrency, Account::getBalance, Money::add));
    }

    public Map<Boolean, List<Account>> accountsByType() {
        return accountRepository.findAll()
                .collect(partitioningBy(account -> account instanceof PremiumAccount));
    }

    public Optional<Account> richestAccount(Currency currency) {
        return accountRepository.findAll()
                .filter(account -> account.getCurrency() == currency)
                .max(Comparator.comparing(account -> account.getBalance().value()));
    }

    public String custom(final Predicate<Account> predicate, final AccountFormatter accountFormatter) {
        var body = accountRepository.findBy(predicate)
                .map(accountFormatter::format)
                .collect(joining(lineSeparator()));
        return """
                --- Report ---
                %s
                """.formatted(body);
    }

}
