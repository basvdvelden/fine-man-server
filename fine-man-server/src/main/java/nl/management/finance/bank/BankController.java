package nl.management.finance.bank;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@RestController
@RequestMapping("banks")
public class BankController {
    private final BankService service;
    private final BankMapper mapper;

    public BankController(BankService service, BankMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("")
    public List<BankDto> getAll() {
        return mapper.toDto(service.getAll());
    }

    @GetMapping("{bankId}/consent-url")
    public String getConsentUrl(@PathVariable("bankId") int bankId) {
        return service.getConsentUrl(bankId);
    }
}
