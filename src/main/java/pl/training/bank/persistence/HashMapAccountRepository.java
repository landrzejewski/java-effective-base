package pl.training.bank.persistence;

import pl.training.bank.model.Account;
import pl.training.bank.model.AccountNumber;
import pl.training.bank.service.AccountRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HashMapAccountRepository implements AccountRepository {

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

}
