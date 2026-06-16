package pl.training.bank.model;

import java.util.function.UnaryOperator;

@FunctionalInterface
public interface AccountFormatter {

    String format(Account account);

    default AccountFormatter andThen(UnaryOperator<String> after) {
        return account -> after.apply(format(account));
    }

}
