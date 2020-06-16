package nl.management.finance.bank;

import nl.management.finance.user.models.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class BankMapper {
    public Bank toEntity(int bankId) {
        Bank bank = new Bank();
        bank.setId(bankId);
        return bank;
    }

    public Bank toEntity(BankDto dto) {
        Bank bank = new Bank();
        bank.setId(dto.getId());
        bank.setName(dto.getName());
        return bank;
    }

    public List<BankDto> toDto(List<Bank> banks) {
        List<BankDto> result = new ArrayList<>();
        for (Bank bank: banks) {
            result.add(toDto(bank));
        }
        return result;
    }

    public BankDto toDto(Bank bank) {
        BankDto dto = new BankDto();
        dto.setId(bank.getId());
        dto.setName(bank.getName());
        return dto;
    }

    public Bank fromId(int id) {
        Bank result = new Bank();
        result.setId(id);
        return result;
    }
}
