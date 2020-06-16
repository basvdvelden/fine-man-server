package nl.management.finance.userbank;

import nl.management.finance.AuthContext;
import nl.management.finance.bank.Bank;
import nl.management.finance.bank.BankService;
import nl.management.finance.bankclient.auth.Authentication;
import nl.management.finance.bankclient.auth.BCBankService;
import nl.management.finance.user.dao.UserBankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserBankService {
    private final UserBankRepository repository;
    private final BCBankService authService;
    private final BankService bankService;
    private final AuthContext context;

    @Autowired
    public UserBankService(UserBankRepository repository, BCBankService authService, BankService bankService, AuthContext context) {
        this.repository = repository;
        this.authService = authService;
        this.bankService = bankService;
        this.context = context;
    }

    public void registerBank(int bankId, String code) {
        Authentication auth = authService.authenticate(code);

        UserBank userBank = new UserBank();
        userBank.setUser(context.getUser());
        userBank.setBank(bankService.getById(bankId));
        userBank.setAccessToken(auth.getAccessToken());
        userBank.setRefreshToken(auth.getRefreshToken());
        userBank.setExpiresAt(auth.getExpiresAt());
        userBank.setTokenType(auth.getTokenType());

        UserBank optionalExisting = repository.findByUserUserIdAndBankId(context.getUser().getUserId(), bankId);
        if (optionalExisting != null) {
            userBank.setId(optionalExisting.getId());
        }

        this.repository.save(userBank);
    }

    public List<Bank> getBanksByUserId(UUID userId) {
        List<UserBank> userBanks = this.repository.getByUserUserId(userId);
        return userBanks.stream().map(UserBank::getBank).collect(Collectors.toList());
    }
}
