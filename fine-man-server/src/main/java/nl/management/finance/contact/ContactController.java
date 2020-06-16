package nl.management.finance.contact;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Transactional
@RestController
@RequestMapping("/users/{userId}/contacts")
public class ContactController {
    private final ContactService service;
    private final ContactMapper mapper;

    @Autowired
    public ContactController(ContactService service, ContactMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    public void createContact(@RequestBody ContactDto dto) {
        service.createContact(mapper.toEntity(dto));
    }

    @GetMapping
    public List<ContactDto> getContacts(@PathVariable("userId") UUID userId) {
        return mapper.toDto(service.getContacts(userId));
    }

    @DeleteMapping("/{iban}")
    public void deleteContact(@PathVariable("iban") String iban) {
        service.deleteContact(iban);
    }
}
