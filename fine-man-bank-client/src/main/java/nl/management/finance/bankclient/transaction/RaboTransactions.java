package nl.management.finance.bankclient.transaction;

public class RaboTransactions {
    private RaboTransactionsAccount account;
    private RaboTransactionsPerType transactions;

    public RaboTransactionsAccount getAccount() {
        return account;
    }

    public void setAccount(RaboTransactionsAccount transactionsAccount) {
        this.account = transactionsAccount;
    }

    public RaboTransactionsPerType getTransactions() {
        return transactions;
    }

    public void setTransactions(RaboTransactionsPerType transactionsPerType) {
        this.transactions = transactionsPerType;
    }
}
