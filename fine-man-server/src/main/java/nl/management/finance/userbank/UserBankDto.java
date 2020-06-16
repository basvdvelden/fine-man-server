package nl.management.finance.userbank;

import nl.management.finance.bank.BankDto;
import nl.management.finance.user.models.UserDto;

public class UserBankDto {
    private int id;
    private UserDto user;
    private BankDto bank;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public BankDto getBank() {
        return bank;
    }

    public void setBank(BankDto bank) {
        this.bank = bank;
    }
}
