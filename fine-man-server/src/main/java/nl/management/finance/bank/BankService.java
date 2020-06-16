package nl.management.finance.bank;

import nl.management.finance.bankclient.auth.BCBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class BankService {
    private final BankRepository repository;
    private final BCBankService bankService;

    @Autowired
    public BankService(BankRepository repository, BCBankService bankService) {
        this.repository = repository;
        this.bankService = bankService;
    }

    public Bank getById(int bankId) {
        return repository.getOne(bankId);
    }

    public List<Bank> getAll() {
        return repository.findAll();
    }

    public String getConsentUrl(int bankId) {
        return bankService.getConsentUrl(bankId);
    }
}
