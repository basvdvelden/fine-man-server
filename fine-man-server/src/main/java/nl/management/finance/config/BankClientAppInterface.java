package nl.management.finance.config;

import nl.management.finance.AuthContext;
import nl.management.finance.bankclient.*;
import nl.management.finance.bankclient.auth.Authentication;
import nl.management.finance.bankclient.bank.EBank;
import nl.management.finance.user.dao.UserBankRepository;

public class BankClientAppInterface implements IAppInterface {
    private final AuthContext context;
    private final UserBankRepository userBankRepository;
    private final RaboBankAdapter raboBankAdapter;

    public BankClientAppInterface(AuthContext context, UserBankRepository userBankRepository, RaboBankAdapter raboBankAdapter) {
        this.context = context;
        this.userBankRepository = userBankRepository;
        this.raboBankAdapter = raboBankAdapter;
    }

    @Override
    public Authentication getBankAuth() {
        return new Authentication(context.getUserBank().getAccessToken(), context.getUserBank().getExpiresAt(),
                context.getUserBank().getRefreshToken(), context.getUserBank().getTokenType());
    }

    @Override
    public IBankAdapter getBankAdapter() {
        switch(EBank.getForId(context.getBank().getId())) {
            case RABOBANK:
                return raboBankAdapter;
            default:
                throw new IllegalStateException("No adapter found for bank.");
        }
    }

    @Override
    public void updateBankAuth(Authentication auth) {
        context.getUserBank().setExpiresAt(auth.getExpiresAt());
        context.getUserBank().setRefreshToken(auth.getRefreshToken());
        context.getUserBank().setAccessToken(auth.getAccessToken());
        context.getUserBank().setTokenType(auth.getTokenType());
        userBankRepository.save(context.getUserBank());
    }
}
