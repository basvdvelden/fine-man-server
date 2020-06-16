package nl.management.finance.balance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@Transactional
@RestController
@RequestMapping("/users/{userId}/bank-accounts/{iban}/balances")
public class BalanceController {
    private final BalanceService service;
    private final BalanceMapper mapper;

    @Autowired
    public BalanceController(BalanceService service, BalanceMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }
}
