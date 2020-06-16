package nl.management.finance.bankaccount;

import io.reactivex.rxjava3.core.Completable;
import nl.management.finance.AuthContext;
import nl.management.finance.balance.BalanceService;
import nl.management.finance.bankclient.bankaccount.BCBankAccountService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class BankAccountService {
    private final BankAccountRepository repository;
    private final BCBankAccountService service;
    private final BankAccountMapper mapper;
    private final BalanceService balanceService;
    private final AuthContext context;

    public BankAccountService(BankAccountRepository repository, BCBankAccountService service, BankAccountMapper mapper,
                              BalanceService balanceService, AuthContext context) {
        this.repository = repository;
        this.service = service;
        this.mapper = mapper;
        this.balanceService = balanceService;
        this.context = context;
    }

    public List<BankAccount> getBankAccounts() {
        List<BankAccount> bankAccounts = mapper.toEntity(service.getBankAccounts(), context.getUser(),
                context.getUserBank().getBank());
        for (BankAccount bankAccount: bankAccounts) {
            bankAccount.setBalances(List.of(balanceService.getBalance(bankAccount)));
        }
        createOrUpdate(bankAccounts);
//        Completable.fromAction(() -> {
//            balanceService.createBalances(bankAccounts.stream().reduce((ba1, ba2) -> List.of(ba1.getBalances(), ba2.getBalances())).collect(Collectors.toList()));
//        })
        return bankAccounts;
    }

    private void createOrUpdate(List<BankAccount> bankAccounts) {
        for (BankAccount bankAccount: bankAccounts) {
            BankAccount fromDb = repository.getByIban(bankAccount.getIban());
            if (fromDb != null) {
                bankAccount.setId(fromDb.getId());
            }
        }
        repository.saveAll(bankAccounts);
    }
}
