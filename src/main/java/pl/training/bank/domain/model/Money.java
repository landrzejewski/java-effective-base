package pl.training.bank.domain.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

public record Money(BigDecimal value, Currency currency) {

    public Money {
        value = value.setScale(currency.getDefaultFractionDigits(), RoundingMode.HALF_EVEN);
    }

    public static Money of(final double value, final Currency currency) {
        return new Money(new BigDecimal(value), currency);
    }

    public Money add(final Money money) {
        checkCurrencyCompatibility(money.currency);
        return new Money(value.add(money.value), currency);
    }

    public Money subtract(final Money money) {
        checkCurrencyCompatibility(money.currency);
        return new Money(value.subtract(money.value), currency);
    }

    public boolean isGreaterOrEqual(final Money money) {
        checkCurrencyCompatibility(money.currency);
        return value.compareTo(money.value) >= 0;
    }

    private void checkCurrencyCompatibility(final Currency currency) {
        if (!hasCurrency(currency)) {
            throw new IllegalArgumentException("Currencies are not compatible");
        }
    }

    public boolean hasCurrency(final Currency currency) {
       return this.currency == currency;
    }

}
