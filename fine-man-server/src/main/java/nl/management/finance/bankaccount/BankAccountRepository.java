package nl.management.finance.bankaccount;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BankAccountRepository extends JpaRepository<BankAccount, UUID> {
    BankAccount getByIban(String iban);
}
