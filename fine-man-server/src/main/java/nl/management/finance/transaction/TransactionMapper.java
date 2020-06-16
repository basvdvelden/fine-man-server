package nl.management.finance.transaction;

import nl.management.finance.bankaccount.BankAccountMapper;
import nl.management.finance.bankclient.transaction.BCTransaction;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TransactionMapper {
    private final BankAccountMapper bankAccountMapper;

    public TransactionMapper(BankAccountMapper bankAccountMapper) {
        this.bankAccountMapper = bankAccountMapper;
    }

    public List<TransactionDto> toDto(List<Transaction> transactions) {
        List<TransactionDto> result = new ArrayList<>();
        for (Transaction transaction: transactions) {
            result.add(new TransactionDto(
                    bankAccountMapper.toTransactionAccountDto(transaction.getBankAccount()),
                    transaction.getEntryReference(),
                    transaction.getType(),
                    transaction.getBookingDate(),
                    transaction.getDebtorName(),
                    transaction.getUltimateDebtor(),
                    transaction.getCreditorName(),
                    transaction.getUltimateCreditor(),
                    transaction.getAmount(),
                    transaction.getDescription(),
                    transaction.getPaymentReference(),
                    transaction.getInitiatingParty()
            ));
        }

        return result;
    }

    public List<Transaction> toEntity(List<BCTransaction> bcTransactions) {
        List<Transaction> transactions = new ArrayList<>();
        for (BCTransaction bcTransaction: bcTransactions) {
            transactions.add(toEntity(bcTransaction));
        }
        return transactions;
    }

    private Transaction toEntity(BCTransaction bcTransaction) {
        Transaction transaction = new Transaction();
        transaction.setBankAccount(bankAccountMapper.toEntity(bcTransaction.getBankAccount()));
        transaction.setEntryReference(bcTransaction.getEntryReference());
        transaction.setAmount(bcTransaction.getAmount());
        transaction.setBookingDate(bcTransaction.getBookingDate());
        transaction.setCreditorName(bcTransaction.getCreditorName());
        transaction.setDebtorName(bcTransaction.getDebtorName());
        transaction.setDescription(bcTransaction.getDescription());
        transaction.setPaymentReference(bcTransaction.getPaymentReference());
        transaction.setInitiatingParty(bcTransaction.getInitiatingParty());
        transaction.setType(bcTransaction.getType());
        transaction.setUltimateCreditor(bcTransaction.getUltimateCreditor());
        transaction.setUltimateDebtor(bcTransaction.getUltimateDebtor());
        return transaction;
    }
}
