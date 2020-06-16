package nl.management.finance.bankclient.payment;

public class RaboSepaCreditTransferAccount {
    private String currency = "EUR";
    private String iban;

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    @Override
    public String toString() {
        return String.format("[\niban=%s\n]", iban);
    }

    public String getCurrency() {
        return currency;
    }
}
