package nl.management.finance.bankaccount;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@RestController
@RequestMapping("/users/{userId}/banks/{bankId}/bank-accounts")
public class BankAccountController {
    private static final Logger LOG = LoggerFactory.getLogger(BankAccountController.class);
    private final BankAccountMapper mapper;
    private final BankAccountService service;

    @Autowired
    public BankAccountController(BankAccountMapper mapper, BankAccountService service) {
        this.mapper = mapper;
        this.service = service;
    }

    @GetMapping
    public List<BankAccountDto> getBankAccounts() {
        return mapper.toDto(service.getBankAccounts());
    }
}
