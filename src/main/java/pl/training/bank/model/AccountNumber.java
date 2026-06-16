package pl.training.bank.model;

public record AccountNumber(String number) {

    private static final int MIN_LENGTH = 8;

    public AccountNumber {
        if (number.length() < MIN_LENGTH) {
            throw new IllegalArgumentException("Invalid account number");
        }
    }

}
