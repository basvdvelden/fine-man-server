package nl.management.finance.bankclient.payment;

import java.util.UUID;

public class BCSepaCreditTransfer {
    private final String debtorAccountIban;
    private final String creditorName;
    private final String creditorIban;
    private final String creditorCurrency;
    private final double amount;
    private final String currency;
    private final String requestedExecutionDate;
    private final String description;
    private final String paymentReference;
    private final String endToEndId;

    public BCSepaCreditTransfer(String debtorAccountIban, String creditorName, String creditorIban, String creditorCurrency,
                                double amount, String currency, String requestedExecutionDate, String description,
                                String paymentReference) {
        this.debtorAccountIban = debtorAccountIban;
        this.creditorName = creditorName;
        this.creditorIban = creditorIban;
        this.creditorCurrency = creditorCurrency;
        this.amount = amount;
        this.currency = currency;
        this.requestedExecutionDate = requestedExecutionDate;
        this.description = description;
        this.paymentReference = paymentReference;
        this.endToEndId = UUID.randomUUID().toString().substring(0, 35);
    }

    public String getDebtorAccountIban() {
        return debtorAccountIban;
    }

    public String getCreditorName() {
        return creditorName;
    }

    public String getCreditorIban() {
        return creditorIban;
    }

    public String getCreditorCurrency() {
        return creditorCurrency;
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

    public String getEndToEndId() {
        return endToEndId;
    }
}
