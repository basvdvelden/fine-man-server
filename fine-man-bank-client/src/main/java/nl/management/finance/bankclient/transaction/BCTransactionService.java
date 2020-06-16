package nl.management.finance.bankclient.transaction;

import nl.management.finance.bankclient.IAppInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

@Service
public class BCTransactionService {
    private static final Logger LOG = LoggerFactory.getLogger(BCTransactionService.class);
    private final IAppInterface context;

    @Autowired
    public BCTransactionService(@Qualifier("external") IAppInterface context) {
        this.context = context;
    }

    public List<BCTransaction> getTransactions(String bankAccountResourceId, int size, int page) {
        return context.getBankAdapter().getBookedTransactions(bankAccountResourceId, size, page);
    }

    public List<BCTransaction> getTransactions(String bankAccountResourceId, ZonedDateTime dateFrom, ZonedDateTime dateTo) {
        return context.getBankAdapter().getBookedTransactions(bankAccountResourceId, dateFrom, dateTo);
    }

}
