package nl.management.finance.balance;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import nl.management.finance.AuthContext;
import nl.management.finance.bankaccount.BankAccount;
import nl.management.finance.bankclient.balance.BCBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BalanceService {
    public static final String DEFAULT_BALANCE_TYPE = "expected";

    private final BalanceRepository repository;
    private final BCBalanceService service;
    private final BalanceMapper mapper;
    private final AuthContext context;

    @Autowired
    public BalanceService(BalanceRepository repository, BCBalanceService service, BalanceMapper mapper, AuthContext context) {
        this.repository = repository;
        this.service = service;
        this.mapper = mapper;
        this.context = context;
    }

    public Balance getBalance(BankAccount bankAccount) {
        List<Balance> balances = mapper.toEntity(service.getBalances(bankAccount.getResourceId()));
        for (Balance balance: balances) {
            balance.setBankAccount(bankAccount);
        }
        return balances.stream().filter(b -> b.getType().equals(DEFAULT_BALANCE_TYPE)).findAny().get();
    }

    public void createBalances(List<Balance> balances) {
        repository.saveAll(balances);
    }
}
