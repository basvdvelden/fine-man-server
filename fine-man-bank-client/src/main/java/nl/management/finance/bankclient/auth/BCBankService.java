package nl.management.finance.bankclient.auth;

import nl.management.finance.bankclient.BCProperties;
import nl.management.finance.bankclient.IAppInterface;
import nl.management.finance.bankclient.bank.EBank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class BCBankService {
    private static final String SCOPES = "ais.balances.read ais.transactions.read-90days ais.transactions.read-history";
    private final BCProperties properties;
    private final IAppInterface context;

    @Autowired
    public BCBankService(BCProperties properties, @Qualifier("external") IAppInterface context) {
        this.properties = properties;
        this.context = context;
    }

    public String getConsentUrl(int bankId) {
        switch(EBank.getForId(bankId)) {
            case RABOBANK:
                return String.format(properties.getRaboConsentUrl(), properties.getRaboApiUrl(), properties.getRaboClientId(),
                        SCOPES);
            default:
                throw new IllegalStateException(String.format("Invalid bank id: %d", bankId));
        }
    }

    public Authentication authenticate(String code) {
        return context.getBankAdapter().authenticate(code);
    }
}
