package nl.management.finance.bankaccount;

import nl.management.finance.balance.BalanceService;
import nl.management.finance.bank.Bank;
import nl.management.finance.bank.BankMapper;
import nl.management.finance.bankclient.bankaccount.BCBankAccount;
import nl.management.finance.bankclient.bankaccount.BCTransactionAccount;
import nl.management.finance.user.UserMapper;
import nl.management.finance.user.models.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BankAccountMapper {
    private final UserMapper userMapper;
    private final BankMapper bankMapper;

    public BankAccountMapper(UserMapper userMapper, BankMapper bankMapper) {
        this.userMapper = userMapper;
        this.bankMapper = bankMapper;
    }

    public List<BankAccountDto> toDto(List<BankAccount> bankAccounts) {
        List<BankAccountDto> dtos = new ArrayList<>();
        for (BankAccount bankAccount: bankAccounts) {
            dtos.add(toDto(bankAccount));
        }
        return dtos;
    }

    public List<BankAccount> toEntity(List<BCBankAccount> bcBankAccounts, User user, Bank bank) {
        List<BankAccount> result = new ArrayList<>();
        for (BCBankAccount bcBankAccount: bcBankAccounts) {
            result.add(toEntity(bcBankAccount, user, bank));
        }
        return result;
    }

    public BankAccount toEntity(BCBankAccount bcBankAccount, User user, Bank bank) {
        BankAccount result = new BankAccount();

        result.setUser(user);
        result.setBank(bank);
        result.setCurrency(bcBankAccount.getCurrency());
        result.setIban(bcBankAccount.getIban());
        result.setName(bcBankAccount.getName());
        result.setResourceId(bcBankAccount.getResourceId());

        return result;
    }

    public BankAccount toEntity(BankAccountDto dto) {
        BankAccount result = new BankAccount();
        result.setId(dto.getId());
        result.setCurrency(dto.getCurrency());
        result.setName(dto.getName());
        result.setResourceId(dto.getResourceId());
        result.setIban(dto.getIban());
        result.setUser(userMapper.fromId(dto.getUserId()));
        result.setBank(bankMapper.fromId(dto.getBankId()));

        return result;
    }

    public BankAccount toEntity(BCTransactionAccount account) {
        BankAccount result = new BankAccount();
        result.setIban(account.getIban());
        result.setCurrency(account.getCurrency());

        return result;
    }

    public TransactionAccountDto toTransactionAccountDto(BankAccount bankAccount) {
        return new TransactionAccountDto(bankAccount.getIban(), bankAccount.getCurrency());
    }

    public BankAccountDto toDto(BankAccount bankAccount) {
        BankAccountDto dto = new BankAccountDto();
        dto.setId(bankAccount.getId());
        dto.setBalance(bankAccount.getBalances().stream().filter(b -> b.getType().equals(BalanceService.DEFAULT_BALANCE_TYPE))
                .findAny().get().getAmount());
        dto.setCurrency(bankAccount.getCurrency());
        dto.setName(bankAccount.getName());
        dto.setIban(bankAccount.getIban());
        dto.setResourceId(bankAccount.getResourceId());
        dto.setUserId(bankAccount.getUser().getUserId());
        dto.setBankId(bankAccount.getBank().getId());

        return dto;
    }
}
