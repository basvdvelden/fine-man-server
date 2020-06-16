package nl.management.finance.transaction;

import nl.management.finance.bankaccount.BankAccountDto;
import nl.management.finance.bankaccount.TransactionAccountDto;

import java.time.LocalDate;

public class TransactionDto {
    private TransactionAccountDto bankAccount;
    private String entryReference;
    private String type;
    private LocalDate bookingDate;
    private String debtorName;
    private String ultimateDebtor;
    private String creditorName;
    private String ultimateCreditor;
    private double amount;
    private String description;
    private String paymentReference;
    private String initiatingParty;

    public TransactionDto(TransactionAccountDto bankAccount, String entryReference, String type, LocalDate bookingDate, String debtorName,
                          String ultimateDebtor, String creditorName, String ultimateCreditor, double amount,
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

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getDebtorName() {
        return debtorName;
    }

    public void setDebtorName(String debtorName) {
        this.debtorName = debtorName;
    }

    public String getUltimateDebtor() {
        return ultimateDebtor;
    }

    public void setUltimateDebtor(String ultimateDebtor) {
        this.ultimateDebtor = ultimateDebtor;
    }

    public String getCreditorName() {
        return creditorName;
    }

    public void setCreditorName(String creditorName) {
        this.creditorName = creditorName;
    }

    public String getUltimateCreditor() {
        return ultimateCreditor;
    }

    public void setUltimateCreditor(String ultimateCreditor) {
        this.ultimateCreditor = ultimateCreditor;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInitiatingParty() {
        return initiatingParty;
    }

    public void setInitiatingParty(String initiatingParty) {
        this.initiatingParty = initiatingParty;
    }

    public String getEntryReference() {
        return entryReference;
    }

    public void setEntryReference(String entryReference) {
        this.entryReference = entryReference;
    }

    public String getPaymentReference() {
        return paymentReference;
    }

    public TransactionAccountDto getBankAccount() {
        return bankAccount;
    }
}
