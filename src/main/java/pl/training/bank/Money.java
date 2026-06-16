package pl.training.bank;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

public record Money(BigDecimal value, Currency currency) {

    public Money {
        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Value must be greater than zero");
        }
        value = value.setScale(currency.getDefaultFractionDigits(), RoundingMode.HALF_EVEN);
    }

    public static Money of(double value, Currency currency) {
        return new Money(new BigDecimal(value), currency);
    }

    public Money add(Money money) {
        checkCurrencyCompatibility(money.currency);
        return new Money(value.add(money.value), currency);
    }

    public Money subtract(Money money) {
        checkCurrencyCompatibility(money.currency);
        return new Money(value.subtract(money.value), currency);
    }

    public boolean isGreaterOrEqual(Money money) {
        checkCurrencyCompatibility(money.currency);
        return value.compareTo(money.value) >= 0;
    }

    private void checkCurrencyCompatibility(Currency currency) {
        if (this.currency != currency) {
            throw new IllegalArgumentException("Currencies are not compatible");
        }
    }

}
