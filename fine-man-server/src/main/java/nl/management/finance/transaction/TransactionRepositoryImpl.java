package nl.management.finance.transaction;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class TransactionRepositoryImpl implements TransactionRepositoryCustom {
    @PersistenceContext
    EntityManager entityManager;

    public void batchInsertIgnoreDuplicate(List<Transaction> transactions) {
        if (transactions.size() < 1) {
            return;
        }
        StringBuilder sql = new StringBuilder("INSERT IGNORE INTO `transaction`(bank_account_id, entry_reference, type, " +
                "booking_date, debtor_name, ultimate_debtor, creditor_name, ultimate_creditor, amount, description, " +
                "payment_reference, initiating_party) VALUES");
        boolean first = true;
        int tempId = 1;
        for (Transaction ignored : transactions) {
            if (!first) {
                sql.append(",");
            }
            sql.append("(");

            boolean firstParam = true;
            for (int i = 1; i < 13; i++) {
                if (!firstParam) {
                    sql.append(",");
                }

                sql.append(String.format("?%d", tempId));
                firstParam = false;
                tempId += 1;
            }

            sql.append(")");
            first = false;
        }
        sql.append(";");
        javax.persistence.Query query = entityManager.createNativeQuery(sql.toString());
        tempId = 1;
        for (Transaction transaction: transactions) {
            query.setParameter(tempId, transaction.getBankAccount().getId());
            tempId += 1;
            query.setParameter(tempId, transaction.getEntryReference());
            tempId += 1;
            query.setParameter(tempId, transaction.getType());
            tempId += 1;
            query.setParameter(tempId, transaction.getBookingDate());
            tempId += 1;
            query.setParameter(tempId, transaction.getDebtorName());
            tempId += 1;
            query.setParameter(tempId, transaction.getUltimateDebtor());
            tempId += 1;
            query.setParameter(tempId, transaction.getCreditorName());
            tempId += 1;
            query.setParameter(tempId, transaction.getUltimateCreditor());
            tempId += 1;
            query.setParameter(tempId, transaction.getAmount());
            tempId += 1;
            query.setParameter(tempId, transaction.getDescription());
            tempId += 1;
            query.setParameter(tempId, transaction.getPaymentReference());
            tempId += 1;
            query.setParameter(tempId, transaction.getInitiatingParty());
            tempId += 1;
        }
        query.executeUpdate();
    }
}
