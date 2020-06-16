package nl.management.finance.bankclient.balance;

public class BCBalance {
    private final String type;
    private final String currency;
    private final Double amount;

    public BCBalance(String type, String currency, Double amount) {
        this.type = type;
        this.currency = currency;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public Double getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }
}
