package nl.management.finance.balance;

import nl.management.finance.bankaccount.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BalanceRepository extends JpaRepository<Balance, Integer> {
    List<Balance> getByBankAccount(BankAccount bankAccount);
}
