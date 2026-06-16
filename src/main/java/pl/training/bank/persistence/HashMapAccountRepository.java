package pl.training.bank.persistence;

import pl.training.bank.domain.model.Account;
import pl.training.bank.domain.model.AccountNumber;
import pl.training.bank.domain.model.Page;
import pl.training.bank.domain.model.PageRequest;
import pl.training.bank.domain.AccountRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public final class HashMapAccountRepository implements AccountRepository {

    private final Map<AccountNumber, Account> accounts = new HashMap<>();

    @Override
    public Account save(final Account account) {
        accounts.put(account.getNumber(), account);
        return account;
    }

    @Override
    public Optional<Account> findByNumber(final AccountNumber number) {
        return Optional.ofNullable(accounts.get(number));
    }

    @Override
    public Page<Account> findAll(final PageRequest pageRequest) {
        var items = accounts.values().stream()
                .skip(pageRequest.offest())
                .limit(pageRequest.size())
                .toList();
        var totalPages = pageRequest.size() == 0 ? 0 : (long) Math.ceil((double) accounts.size() / pageRequest.size());
        return new Page<>(items, totalPages);
    }

    @Override
    public Stream<Account> findAll() {
        return accounts.values().stream();
    }

    @Override
    public Stream<Account> findBy(final Predicate<Account> predicate) {
        return findAll().filter(predicate);
    }


}
