package nl.management.finance.bankclient.balance;

import nl.management.finance.bankclient.IAppInterface;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BCBalanceService {
    private IAppInterface context;

    public BCBalanceService(@Qualifier("external") IAppInterface context) {
        this.context = context;
    }

    public List<BCBalance> getBalances(String bankAccountResourceId) {
        return context.getBankAdapter().getBalances(bankAccountResourceId);
    }
}
