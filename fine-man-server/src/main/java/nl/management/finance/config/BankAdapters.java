package nl.management.finance.config;

import nl.management.finance.bank.Bank;
import nl.management.finance.bankclient.IBankAdapter;
import nl.management.finance.bankclient.RaboBankAdapter;
import nl.management.finance.bankclient.bank.EBank;
import org.springframework.stereotype.Component;

@Component
public class BankAdapters {
    private final RaboBankAdapter raboBankAdapter;

    public BankAdapters(RaboBankAdapter raboBankAdapter) {
        this.raboBankAdapter = raboBankAdapter;
    }

    public IBankAdapter getForBank(Bank bank) {
        switch(EBank.getForId(bank.getId())) {
            case RABOBANK:
                return raboBankAdapter;
        }
        return null;
    }
}
