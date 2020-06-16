package nl.management.finance.bankclient.bank;

import java.util.Arrays;
import java.util.Optional;

public enum EBank {
    RABOBANK(1);

    private final int id;

    EBank(final int id) {
        this.id = id;
    }

    public static EBank getForId(int id) {
        Optional<EBank> optBank = Arrays.stream(EBank.values())
                .filter(bank -> bank.id == id).findFirst();
        if (optBank.isEmpty()) {
            throw new IllegalArgumentException(String.format("No bank found with id: %d", id));
        }
        return optBank.get();
    }

    public int getId() {
        return id;
    }
}
