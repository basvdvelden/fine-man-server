package nl.management.finance.transaction;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import nl.management.finance.AuthContext;
import nl.management.finance.BCServiceFactory;
import nl.management.finance.bankaccount.BankAccount;
import nl.management.finance.bankaccount.BankAccountService;
import nl.management.finance.bankclient.transaction.BCTransactionService;
import nl.management.finance.userbank.UserBank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class TransactionService {
    private static final Logger LOG = LoggerFactory.getLogger(TransactionService.class);
    private static final int MAX_SIZE = 499;
    private final TransactionRepository repository;
    private final TransactionMapper mapper;
    private final BCTransactionService service;
    private final BankAccountService bankAccountService;
    private final AuthContext context;
    private final BCServiceFactory BCServiceFactory;

    public TransactionService(TransactionRepository repository, TransactionMapper mapper, BCTransactionService service, BankAccountService bankAccountService, AuthContext context, BCServiceFactory BCServiceFactory) {
        this.repository = repository;
        this.mapper = mapper;
        this.service = service;
        this.bankAccountService = bankAccountService;
        this.context = context;
        this.BCServiceFactory = BCServiceFactory;
    }

    public List<Transaction> getTransactions(String bankAccountResourceId, int size, int page) {
        // TODO:
//        // Freeze the request scoped context so it's usable outside of the request context.
//        AuthContext context = this.context.freeze();
//        Completable.fromAction(() -> syncTransactions(context, BCServiceFactory.createTransactionService(context)))
//                .subscribeOn(Schedulers.io()).subscribe();
        return mapper.toEntity(service.getTransactions(bankAccountResourceId, size, page));
    }

    // TODO:
//    private void syncTransactions(AuthContext context, BCTransactionService service) {
//        List<BankAccount> bankAccounts = bankAccountService.getByUserBank(context.getUserBank());
//
//        List<Transaction> allTransactions = new ArrayList<>();
//        for (BankAccount bankAccount : bankAccounts) {
//            LocalDate latestBookingDate = repository.getMaxBookingDateByBankAccount(bankAccount);
//            ZonedDateTime dateFrom = null;
//            ZonedDateTime dateTo = null;
//            if (latestBookingDate != null) {
//                dateFrom = ZonedDateTime.of(latestBookingDate.minusWeeks(1).atStartOfDay(), ZoneId.of("UTC"));
//            } else {
//                dateTo = ZonedDateTime.now();
//            }
//
//            List<Transaction> transactions = mapper.toEntity(service.getTransactions(bankAccount.getResourceId(), dateFrom, dateTo));
//            while (transactions.size() % MAX_SIZE == 0) {
//                Stream<ZonedDateTime> dateStream = transactions.stream().map(Transaction::getBookingDate)
//                        .map(d -> ZonedDateTime.of(d.atStartOfDay(), ZoneId.of("UTC")));
//                dateFrom = dateFrom != null ? dateStream.max(ZonedDateTime::compareTo).get() : null;
//                dateTo = dateTo != null ? dateStream.min(ZonedDateTime::compareTo).get() : null;
//
//                transactions = mapper.toEntity(service.getTransactions(bankAccount.getResourceId(), dateFrom, dateTo));
//                allTransactions.addAll(transactions);
//            }
//            LOG.info("Starting batch insert (duplicates ignored) of transactions...");
//            repository.batchInsertIgnoreDuplicate(allTransactions);
//            LOG.info("Transaction batch inserted.");
//        }
//    }
}
