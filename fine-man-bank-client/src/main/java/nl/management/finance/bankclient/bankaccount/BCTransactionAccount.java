package nl.management.finance.bankclient.bankaccount;

public class BCTransactionAccount {
    private String iban;
    private String currency;

    public BCTransactionAccount(String iban, String currency) {
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
