package nl.management.finance;

import nl.management.finance.bank.Bank;
import nl.management.finance.user.models.User;
import nl.management.finance.userbank.UserBank;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class AuthContext {
    private User user;
    private Bank bank;
    private UserBank userBank;

    private AuthContext(AuthContext other) {
        this.user = other.getUser();
        this.bank = other.getBank();
        this.userBank = other.getUserBank();
    }

    public AuthContext() {

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public UserBank getUserBank() {
        return userBank;
    }

    public void setUserBank(UserBank userBank) {
        this.userBank = userBank;
    }

    public AuthContext freeze() {
        return new AuthContext(this);
    }
}
