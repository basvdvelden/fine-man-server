package nl.management.finance.balance;

import nl.management.finance.bankclient.balance.BCBalance;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BalanceMapper {
    public List<Balance> toEntity(List<BCBalance> bcBalances) {
        List<Balance> result = new ArrayList<>();
        for (BCBalance bcBalance: bcBalances) {
            Balance balance = new Balance();
            balance.setAmount(bcBalance.getAmount());
            balance.setCurrency(bcBalance.getCurrency());
            balance.setType(bcBalance.getType());
            result.add(balance);
        }
        return result;
    }

    public BalanceDto toDto(Balance balance) {
        return new BalanceDto(balance.getAmount(), balance.getCurrency(), balance.getType());
    }
}
