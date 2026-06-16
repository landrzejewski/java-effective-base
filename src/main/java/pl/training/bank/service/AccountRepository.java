package pl.training.bank.service;

import pl.training.bank.model.Account;
import pl.training.bank.model.AccountNumber;

import java.util.Optional;
import java.util.stream.Stream;

public interface AccountRepository {

    Account save(Account account);

    Optional<Account> findByNumber(AccountNumber number);

    Stream<Account> findAll();

}
