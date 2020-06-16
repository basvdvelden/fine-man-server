package nl.management.finance.bankclient;

import nl.management.finance.bankclient.auth.Authentication;
import org.springframework.stereotype.Component;

@Component
public interface IAppInterface {
    Authentication getBankAuth();

    IBankAdapter getBankAdapter();

    void updateBankAuth(Authentication auth);
}
