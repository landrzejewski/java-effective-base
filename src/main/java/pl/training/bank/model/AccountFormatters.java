package pl.training.bank.model;

public final class AccountFormatters {

    private static final MoneyFormatter MONEY_FORMATTER = MoneyFormatters.defaultLocale();

    private AccountFormatters() {
    }

    public static AccountFormatter simple() {
        return account -> account.getNumber().number() + ": " + MONEY_FORMATTER.format(account.getBalance());
    }

    public static AccountFormatter detailed() {
        return account -> String.format("%s | %s | %s",  account.getNumber(), MONEY_FORMATTER.format(account.getBalance()), account.getCurrency());
    }

    public static AccountFormatter csv() {
        return account -> String.join(", ", account.getNumber().number(), account.getBalance().value().toPlainString(), account.getCurrency().getCurrencyCode());
    }

}
