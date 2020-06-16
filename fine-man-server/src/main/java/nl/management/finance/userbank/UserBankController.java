package nl.management.finance.userbank;

import nl.management.finance.bank.BankDto;
import nl.management.finance.bank.BankMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.websocket.server.PathParam;
import java.util.List;
import java.util.UUID;

@Transactional
@RestController
@RequestMapping("/users/{userId}/banks")
public class UserBankController {
    private final BankMapper bankMapper;
    private final UserBankService mService;

    @Autowired
    public UserBankController(BankMapper bankMapper, UserBankService service) {
        this.bankMapper = bankMapper;
        mService = service;
    }

    @GetMapping
    public List<BankDto> getUserBanks(@PathVariable("userId") UUID userId) {
        return bankMapper.toDto(mService.getBanksByUserId(userId));
    }

    @PutMapping("{bankId}")
    public void registerBank(@PathVariable("bankId") int bankId, @PathParam("code") String code) {
        mService.registerBank(bankId, code);
    }
}
