package pl.training.bank;

import java.text.NumberFormat;
import java.util.Locale;

public final class MoneyFormatters {

    private static final String SEPARATOR = " ";

    private MoneyFormatters() {
    }

    public static MoneyFormatter plain() {
        return money -> money.value().toPlainString() + SEPARATOR + money.currency().getCurrencyCode();
    }

    public static MoneyFormatter local(final Locale locale) {
        var formatter = NumberFormat.getCurrencyInstance(locale);
        return money -> {
            formatter.setCurrency(money.currency());
            return formatter.format(money.value());
        };
    }

    public static MoneyFormatter defaultLocale() {
        return local(Locale.getDefault());
    }

}
