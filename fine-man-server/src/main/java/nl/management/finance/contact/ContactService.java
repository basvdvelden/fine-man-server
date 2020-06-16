package nl.management.finance.contact;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ContactService {
    private final ContactRepository repository;

    public ContactService(ContactRepository repository) {
        this.repository = repository;
    }

    public void createContact(Contact contact) {
        repository.save(contact);
    }

    public List<Contact> getContacts(UUID userId) {
        return repository.getByUserUserId(userId);
    }

    public void deleteContact(String iban) {
        repository.deleteByIban(iban);
    }
}
