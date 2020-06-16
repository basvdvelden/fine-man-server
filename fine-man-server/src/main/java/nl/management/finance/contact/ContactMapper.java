package nl.management.finance.contact;

import nl.management.finance.AuthContext;
import nl.management.finance.user.models.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ContactMapper {
    private final AuthContext context;

    public ContactMapper(AuthContext context) {
        this.context = context;
    }

    public Contact toEntity(ContactDto dto) {
        Contact contact = new Contact();
        contact.setName(dto.getName());
        contact.setIban(dto.getIban());
        contact.setUser(context.getUser());

        return contact;
    }

    public List<ContactDto> toDto(List<Contact> contacts) {
        List<ContactDto> dtos = new ArrayList<>();
        for (Contact contact: contacts) {
            ContactDto dto = new ContactDto();
            dto.setIban(contact.getIban());
            dto.setName(contact.getName());
            dtos.add(dto);
        }
        return dtos;
    }
}
