package pl.training.bank;

import java.util.Currency;

public class Application {

    private final static Currency DEFAULT_CURRENCY =  Currency.getInstance("PLN");

    static void main() {
        var m1 = Money.of(100, DEFAULT_CURRENCY);
        var m2 = Money.of(1, DEFAULT_CURRENCY);
        var m3 = m1.add(m2);
        System.out.println(m3);

        var formatter = MoneyFormatters.plain();

        System.out.println(formatter.format(m3));
    }

}
