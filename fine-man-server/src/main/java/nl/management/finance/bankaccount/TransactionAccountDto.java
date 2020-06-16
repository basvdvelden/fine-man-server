package nl.management.finance.bankaccount;

public class TransactionAccountDto {
    private String iban;
    private String currency;

    public TransactionAccountDto(String iban, String currency) {
        this.iban = iban;
        this.currency = currency;
    }

    public String getIban() {
        return iban;
    }

    public String getCurrency() {
        return currency;
    }
}
