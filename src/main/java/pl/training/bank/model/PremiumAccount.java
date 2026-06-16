package pl.training.bank.model;

public final class PremiumAccount extends Account {

    public PremiumAccount(AccountNumber number, Money balance) {
        super(number, balance);
    }

    @Override
    public void withdraw(final Money amount) {
        balance = balance.subtract(amount);
    }

}
