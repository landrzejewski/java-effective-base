package pl.training.bank.domain.model;

import java.text.NumberFormat;
import java.util.Locale;

public final class MoneyFormatters {

    private static final String SEPARATOR = " ";

    private MoneyFormatters() {
    }

    public static Formatter<Money> plain() {
        return money -> money.value().toPlainString() + SEPARATOR + money.currency().getCurrencyCode();
    }

    public static Formatter<Money> local(final Locale locale) {
        var formatter = NumberFormat.getCurrencyInstance(locale);
        return money -> {
            formatter.setCurrency(money.currency());
            return formatter.format(money.value());
        };
    }

    public static Formatter<Money> defaultLocale() {
        return local(Locale.getDefault());
    }

}
