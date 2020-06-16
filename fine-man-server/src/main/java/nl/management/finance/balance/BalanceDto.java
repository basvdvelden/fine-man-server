package nl.management.finance.balance;

public class BalanceDto {
    private final double amount;
    private final String currency;
    private final String type;

    public BalanceDto(double amount, String currency, String type) {
        this.amount = amount;
        this.currency = currency;
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public String getType() {
        return type;
    }
}
