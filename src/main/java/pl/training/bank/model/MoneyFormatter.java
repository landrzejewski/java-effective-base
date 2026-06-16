package pl.training.bank.model;

@FunctionalInterface
public interface MoneyFormatter {

    String format(Money money);

}
