package nl.management.finance.payment;

import nl.management.finance.bankaccount.BankAccountDto;

public class SepaCreditTransferDto {
    private BankAccountDto bankAccount;
    private String debtorAccountIban;
    private double amount;
    private String currency;
    private String requestedExecutionDate;
    private String description;
    private String paymentReference;

    public String getDebtorAccountIban() {
        return debtorAccountIban;
    }

    public void setDebtorAccountIban(String debtorAccountIban) {
        this.debtorAccountIban = debtorAccountIban;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getRequestedExecutionDate() {
        return requestedExecutionDate;
    }

    public void setRequestedExecutionDate(String requestedExecutionDate) {
        this.requestedExecutionDate = requestedExecutionDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPaymentReference() {
        return paymentReference;
    }

    public void setPaymentReference(String paymentReference) {
        this.paymentReference = paymentReference;
    }

    public BankAccountDto getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccountDto bankAccount) {
        this.bankAccount = bankAccount;
    }
}
