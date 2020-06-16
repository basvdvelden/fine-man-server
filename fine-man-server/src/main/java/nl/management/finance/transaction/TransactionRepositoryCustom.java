package nl.management.finance.transaction;

import java.util.List;

public interface TransactionRepositoryCustom {
    void batchInsertIgnoreDuplicate(List<Transaction> transactions);
}
