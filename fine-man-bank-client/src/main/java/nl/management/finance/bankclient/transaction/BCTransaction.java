package nl.management.finance.bankclient.transaction;

import nl.management.finance.bankclient.bankaccount.BCTransactionAccount;

import java.time.LocalDate;

public class BCTransaction {
    private final BCTransactionAccount bankAccount;
    private final String entryReference;
    private final String type;
    private final LocalDate bookingDate;
    private final String debtorName;
    private final String ultimateDebtor;
    private final String creditorName;
    private final String ultimateCreditor;
    private final double amount;
    private final String description;
    private final String paymentReference;
    private final String initiatingParty;

    public BCTransaction(BCTransactionAccount bankAccount, String entryReference, String type, LocalDate bookingDate,
                         String debtorName, String ultimateDebtor, String creditorName, String ultimateCreditor, double amount,
                         String remittanceInformationUnstructured, String remittanceInformationStructured,
                         String initiatingPartyName) {
        this.bankAccount = bankAccount;
        this.entryReference = entryReference;
        this.type = type;
        this.bookingDate = bookingDate;
        this.debtorName = debtorName;
        this.ultimateDebtor = ultimateDebtor;
        this.creditorName = creditorName;
        this.ultimateCreditor = ultimateCreditor;
        this.amount = amount;
        this.description = remittanceInformationUnstructured;
        this.paymentReference = remittanceInformationStructured;
        this.initiatingParty = initiatingPartyName;
    }

    public String getType() {
        return type;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public String getDebtorName() {
        return debtorName;
    }

    public String getUltimateDebtor() {
        return ultimateDebtor;
    }

    public String getCreditorName() {
        return creditorName;
    }

    public String getUltimateCreditor() {
        return ultimateCreditor;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public String getInitiatingParty() {
        return initiatingParty;
    }

    public String getEntryReference() {
        return entryReference;
    }

    public String getPaymentReference() {
        return paymentReference;
    }

    public BCTransactionAccount getBankAccount() {
        return bankAccount;
    }
}
