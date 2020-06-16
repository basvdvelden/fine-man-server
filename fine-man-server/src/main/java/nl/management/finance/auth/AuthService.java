package nl.management.finance.auth;

import nl.management.finance.bankclient.auth.BCBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final BCBankService service;

    @Autowired
    public AuthService(BCBankService service) {
        this.service = service;
    }

    public String getConsentUrl(int bankId) {
        return service.getConsentUrl(bankId);
    }
}
