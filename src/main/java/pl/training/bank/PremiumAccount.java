package pl.training.bank;

public final class PremiumAccount extends Account {

    public PremiumAccount(AccountNumber number, Money balance) {
        super(number, balance);
    }

    @Override
    public void withdraw(Money amount) {
        balance = balance.subtract(amount);
    }

}
