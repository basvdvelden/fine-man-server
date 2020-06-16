package nl.management.finance.transaction;

import nl.management.finance.bankaccount.BankAccount;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "bank_account_id")
    private BankAccount bankAccount;

    @Column(nullable = false, unique = true)
    private String entryReference;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private LocalDate bookingDate;

    @Column
    private String debtorName;

    @Column(nullable = false)
    private String ultimateDebtor;

    @Column
    private String creditorName;

    @Column(nullable = false)
    private String ultimateCreditor;

    @Column(nullable = false)
    private double amount;

    @Column
    private String description;

    @Column
    private String paymentReference;

    @Column(nullable = false)
    private String initiatingParty;

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", bankAccount=" + bankAccount +
                ", checkId='" + entryReference + '\'' +
                ", type='" + type + '\'' +
                ", bookingDate='" + bookingDate + '\'' +
                ", debtorName='" + debtorName + '\'' +
                ", ultimateDebtor='" + ultimateDebtor + '\'' +
                ", creditorName='" + creditorName + '\'' +
                ", ultimateCreditor='" + ultimateCreditor + '\'' +
                ", amount='" + amount + '\'' +
                ", description='" + description + '\'' +
                ", paymentReference='" + paymentReference + '\'' +
                ", initiatingParty='" + initiatingParty + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getEntryReference() {
        return entryReference;
    }

    public void setEntryReference(String checkId) {
        this.entryReference = checkId;
    }

    public String getPaymentReference() {
        return paymentReference;
    }

    public void setPaymentReference(String paymentReference) {
        this.paymentReference = paymentReference;
    }
}
