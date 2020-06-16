package nl.management.finance.userbank;

import nl.management.finance.bank.BankMapper;
import nl.management.finance.user.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserBankMapper {
    private final UserMapper userMapper;
    private final BankMapper bankMapper;

    @Autowired
    public UserBankMapper(UserMapper userMapper, BankMapper bankMapper) {
        this.userMapper = userMapper;
        this.bankMapper = bankMapper;
    }

    public List<UserBankDto> toDto(List<UserBank> userBanks) {
        List<UserBankDto> result = new ArrayList<>();
        for (UserBank userBank: userBanks) {
            result.add(toDto(userBank));
        }
        return result;
    }

    public UserBankDto toDto(UserBank userBank) {
            UserBankDto dto = new UserBankDto();
            dto.setId(userBank.getId());
            dto.setBank(bankMapper.toDto(userBank.getBank()));
            dto.setUser(userMapper.toDto(userBank.getUser()));
            return dto;
    }

    public UserBank toEntity(UserBankDto dto) {
        UserBank userBank = new UserBank();
        userBank.setId(dto.getId());
        userBank.setBank(bankMapper.toEntity(dto.getBank()));
        userBank.setUser(userMapper.toEntity(dto.getUser()));
        return userBank;
    }
}
