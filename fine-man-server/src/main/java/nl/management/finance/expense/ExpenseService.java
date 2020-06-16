package nl.management.finance.expense;

import nl.management.finance.transaction.Transaction;
import nl.management.finance.transaction.TransactionService;
import nl.management.finance.userbank.UserBank;
import nl.management.finance.userbank.UserBankService;
import nl.management.finance.bankaccount.BankAccount;
import nl.management.finance.bankaccount.BankAccountService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ExpenseService {
    private final ExpenseRepository repository;
    private final TransactionService transactionService;
    private final UserBankService userBankService;
    private final BankAccountService bankAccountService;


    public ExpenseService(ExpenseRepository repository, TransactionService transactionService,
                          UserBankService userBankService, BankAccountService bankAccountService) {
        this.repository = repository;
        this.transactionService = transactionService;
        this.userBankService = userBankService;
        this.bankAccountService = bankAccountService;
    }

    public List<Expense> getExpenses(UUID userId) {
        return repository.getAllByUserUserId(userId);
    }
}
