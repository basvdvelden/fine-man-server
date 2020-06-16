package nl.management.finance.payment;

import nl.management.finance.bankaccount.BankAccount;

public class SepaCreditTransfer {
    private final String debtorAccountIban;
    private final BankAccount creditorAccount;
    private final double amount;
    private final String currency;
    private final String requestedExecutionDate;
    private final String description;
    private final String paymentReference;

    public SepaCreditTransfer(String debtorAccountIban, BankAccount creditorAccount, double amount, String currency,
                              String requestedExecutionDate, String description, String paymentReference) {
        this.debtorAccountIban = debtorAccountIban;
        this.creditorAccount = creditorAccount;
        this.amount = amount;
        this.currency = currency;
        this.requestedExecutionDate = requestedExecutionDate;
        this.description = description;
        this.paymentReference = paymentReference;
    }

    public String getDebtorAccountIban() {
        return debtorAccountIban;
    }

    public BankAccount getCreditorAccount() {
        return creditorAccount;
    }

    public double getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public String getRequestedExecutionDate() {
        return requestedExecutionDate;
    }

    public String getDescription() {
        return description;
    }

    public String getPaymentReference() {
        return paymentReference;
    }
}
