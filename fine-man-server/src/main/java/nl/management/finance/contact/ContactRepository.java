package nl.management.finance.contact;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ContactRepository extends JpaRepository<Contact, Integer> {
    List<Contact> getByUserUserId(UUID userId);

    void deleteByIban(String iban);
}
