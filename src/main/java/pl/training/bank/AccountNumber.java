package pl.training.bank;

public record AccountNumber(String number) {

    private static final int MIN_LENGTH = 16;

    public AccountNumber {
        if (number.length() != MIN_LENGTH) {
            throw new IllegalArgumentException("Invalid account number");
        }
    }

}
