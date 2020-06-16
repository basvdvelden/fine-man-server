package nl.management.finance.bankclient;

import nl.management.finance.bankclient.auth.Authentication;
import nl.management.finance.bankclient.balance.BCBalance;
import nl.management.finance.bankclient.bankaccount.BCBankAccount;
import nl.management.finance.bankclient.payment.BCPaymentResponse;
import nl.management.finance.bankclient.payment.BCSepaCreditTransfer;
import nl.management.finance.bankclient.transaction.BCTransaction;

import java.time.ZonedDateTime;
import java.util.List;

public interface IBankAdapter {
    List<BCTransaction> getBookedTransactions(String bankAccountResourceId, int size, int page);

    List<BCTransaction> getBookedTransactions(String bankAccountResourceId, ZonedDateTime dateFrom, ZonedDateTime dateTo);

    List<BCBankAccount> getAccounts();

    List<BCBalance> getBalances(String accountResourceId);

    BCPaymentResponse initiatePayment(BCSepaCreditTransfer sepaCreditTransfer, String ip, String redirectUrl);

    Authentication authenticate(String code);

    String getPsuMessage(String paymentId, String ip);
}
