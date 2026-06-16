package pl.training.bank;

@FunctionalInterface
public interface MoneyFormatter {

    String format(Money money);

}
