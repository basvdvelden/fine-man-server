package nl.management.finance.bankclient.bankaccount;

import nl.management.finance.bankclient.IAppInterface;
import nl.management.finance.bankclient.IBankAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BCBankAccountService {
    private final IAppInterface context;

    @Autowired
    public BCBankAccountService(@Qualifier("external") IAppInterface context) {
        this.context = context;
    }

    public List<BCBankAccount> getBankAccounts() {
        return context.getBankAdapter().getAccounts();
    }
}
