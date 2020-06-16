package nl.management.finance.bankclient.bankaccount;

public class BCBankAccount {
    private final String name;
    private final String currency;
    private final String iban;
    private final String resourceId;
    private final int bankId;

    public BCBankAccount(String name, String currency, String iban, String resourceId, int bankId) {
        this.name = name;
        this.currency = currency;
        this.iban = iban;
        this.resourceId = resourceId;
        this.bankId = bankId;
    }

    public String getName() {
        return name;
    }

    public String getCurrency() {
        return currency;
    }

    public String getIban() {
        return iban;
    }

    public String getResourceId() {
        return resourceId;
    }

    public int getBankId() {
        return bankId;
    }
}
