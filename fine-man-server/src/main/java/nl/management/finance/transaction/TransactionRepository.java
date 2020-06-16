package nl.management.finance.transaction;

import nl.management.finance.bankaccount.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID>, TransactionRepositoryCustom {

    @Query("select max(bookingDate) from Transaction where bankAccount = :bankAccount")
    LocalDate getMaxBookingDateByBankAccount(BankAccount bankAccount);
}
