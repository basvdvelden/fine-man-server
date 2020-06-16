package nl.management.finance.bankclient;

import nl.management.finance.bankclient.api.RaboApi;
import nl.management.finance.bankclient.auth.Authentication;
import nl.management.finance.bankclient.auth.RaboOAuthResponse;
import nl.management.finance.bankclient.balance.BCBalance;
import nl.management.finance.bankclient.balance.RaboAccountBalance;
import nl.management.finance.bankclient.balance.RaboBalance;
import nl.management.finance.bankclient.bankaccount.BCBankAccount;
import nl.management.finance.bankclient.bankaccount.BCTransactionAccount;
import nl.management.finance.bankclient.payment.*;
import nl.management.finance.bankclient.transaction.BCTransaction;
import nl.management.finance.bankclient.transaction.RaboTransaction;
import nl.management.finance.bankclient.transaction.RaboTransactions;
import nl.management.finance.bankclient.transaction.RaboTransactionsAccount;
import nl.management.finance.lib.rabo.pojo.bankaccount.RaboBankAccount;
import nl.management.finance.lib.rabo.pojo.bankaccount.RaboBankAccounts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Component
public class RaboBankAdapter implements IBankAdapter {
    private static final Logger LOG = LoggerFactory.getLogger(RaboBankAdapter.class);
    private final RaboApi api;
    private final BCProperties properties;

    public RaboBankAdapter(RaboApi api, BCProperties properties) {
        this.api = api;
        this.properties = properties;
    }

    @Override
    public List<BCTransaction> getBookedTransactions(String bankAccountResourceId, int size, int page) {
        return getBookedTransactions(bankAccountResourceId, size, page, null, null);
    }

    @Override
    public List<BCTransaction> getBookedTransactions(String bankAccountResourceId, ZonedDateTime dateFrom, ZonedDateTime dateTo) {
        return getBookedTransactions(bankAccountResourceId, 499, null, dateFrom, dateTo);
    }

    private List<BCTransaction> getBookedTransactions(String bankAccountResourceId, Integer size, Integer page,
                                                      ZonedDateTime dateFrom, ZonedDateTime dateTo) {
        String bookingStatus = "booked";
        try {
            Call<RaboTransactions> call = api.getTransactions(bankAccountResourceId, size, page, dateFrom, dateTo, bookingStatus);
            Response<RaboTransactions> response = call.clone().execute();
            if (!response.isSuccessful()) {
                throw new ResponseUnsuccessfulException(response.errorBody() != null ? response.errorBody().string() : response.toString());
            }
            return toDomain(response.body(), bookingStatus);
        } catch (ResponseUnsuccessfulException e) {
            LOG.error("Unsuccessful response fetching rabo bank account transactions.", e);
            return null;
        } catch (IOException e) {
            LOG.error("I/O error fetching rabo bank account transactions.", e);
            return null;
        }
    }

    private List<BCTransaction> toDomain(RaboTransactions raboTransactions, String bookingStatus) {
        List<BCTransaction> result = new ArrayList<>();
        List<RaboTransaction> bookedTransactions = raboTransactions.getTransactions().getBooked();
        BCTransactionAccount account = toDomain(raboTransactions.getAccount());
        for (RaboTransaction bookedTransaction : bookedTransactions) {
            result.add(new BCTransaction(
                    account,
                    bookedTransaction.getEntryReference(),
                    bookingStatus,
                    LocalDate.parse(bookedTransaction.getBookingDate()),
                    bookedTransaction.getDebtorName(),
                    bookedTransaction.getUltimateDebtor(),
                    bookedTransaction.getCreditorName(),
                    bookedTransaction.getUltimateCreditor(),
                    Double.parseDouble(bookedTransaction.getTransactionAmount().getAmount()),
                    bookedTransaction.getRemittanceInformationUnstructured(),
                    bookedTransaction.getRemittanceInformationStructured(),
                    bookedTransaction.getInitiatingPartyName()
            ));
        }

        return result;
    }

    private BCTransactionAccount toDomain(RaboTransactionsAccount account) {
        return new BCTransactionAccount(account.getIban(), account.getCurrency());
    }

    @Override
    public List<BCBankAccount> getAccounts() {
        try {
            Call<RaboBankAccounts> call = api.getBankAccounts();
            Response<RaboBankAccounts> response = call.clone().execute();
            if (!response.isSuccessful()) {
                throw new ResponseUnsuccessfulException(response.errorBody() != null ? response.errorBody().string() : response.toString());
            }
            return toDomain(response.body().getAccounts());
        } catch (ResponseUnsuccessfulException e) {
            LOG.error("Unsuccessful response fetching rabo bank accounts.", e);
            return null;
        } catch (IOException e) {
            LOG.error("I/O error fetching rabo bank accounts.", e);
            return null;
        }
    }

    private List<BCBankAccount> toDomain(List<RaboBankAccount> raboBankAccounts) {
        List<BCBankAccount> result = new ArrayList<>();
        for (RaboBankAccount raboBankAccount : raboBankAccounts) {
            result.add(new BCBankAccount(
                    raboBankAccount.getName(),
                    raboBankAccount.getCurrency(),
                    raboBankAccount.getIban(),
                    raboBankAccount.getResourceId(),
                    0 //TODO: replace this
                    ));
        }

        return result;
    }

    @Override
    public Authentication authenticate(String code) {
        try {
            String authHeader = "Basic " + Base64.getEncoder().encodeToString(String.format(
                    "%s:%s", properties.getRaboClientId(), properties.getRaboClientSecret()).getBytes());

            Call<RaboOAuthResponse> call = api.authenticate(authHeader, "authorization_code", code);
            Response<RaboOAuthResponse> response = call.clone().execute();
            if (!response.isSuccessful()) {
                throw new ResponseUnsuccessfulException(response.errorBody() != null ? response.errorBody().string() : response.toString());
            }
            return toDomain(response.body());
        } catch (ResponseUnsuccessfulException e) {
            LOG.error("Unsuccessful response authenticating at rabobank.", e);
            return null;
        } catch (IOException e) {
            LOG.error("I/O error authenticating at rabobank.", e);
            return null;
        }
    }

    private Authentication toDomain(RaboOAuthResponse auth) {
        return new Authentication(
                auth.getAccessToken(),
                System.currentTimeMillis() + auth.getExpiresIn() * 1000,
                auth.getRefreshToken(),
                auth.getTokenType()
        );
    }

    public List<BCBalance> getBalances(String accountResourceId) {
        try {
            Call<RaboAccountBalance> call = api.getBalances(accountResourceId);
            Response<RaboAccountBalance> response = call.clone().execute();
            if (!response.isSuccessful()) {
                throw new ResponseUnsuccessfulException(response.errorBody() != null ? response.errorBody().string() : response.toString());
            }
            return toDomain(response.body());
        } catch (ResponseUnsuccessfulException e) {
            LOG.error("Unsuccessful response getting balances from rabo bank.", e);
            return null;
        } catch (IOException e) {
            LOG.error("I/O error getting balances from rabo bank.", e);
            return null;
        }
    }

    private List<BCBalance> toDomain(RaboAccountBalance raboAccountBalance) {
        List<BCBalance> result = new ArrayList<>();
        for (RaboBalance raboBalance: raboAccountBalance.getBalances()) {
            result.add(new BCBalance(raboBalance.getBalanceType(), raboBalance.getBalanceAmount().getCurrency(),
                    raboBalance.getBalanceAmount().getAmount()));
        }
        return result;
    }

    public BCPaymentResponse initiatePayment(BCSepaCreditTransfer sepaCreditTransfer, String ip, String redirectUrl) {
        try {
            Call<PaymentInitiationResponse> call = api.initiatePayment(ip, redirectUrl, "application/json",
                    toRabo(sepaCreditTransfer));

            Response<PaymentInitiationResponse> response = call.clone().execute();
            if (!response.isSuccessful()) {
                throw new ResponseUnsuccessfulException(response.errorBody() != null ? response.errorBody().string() : response.toString());
            }
            PaymentInitiationResponse responseBody = response.body();
            LOG.info("Payment initiation response rabobank: {}", responseBody);
            return toDomain(responseBody);
        } catch (ResponseUnsuccessfulException e) {
            LOG.error("Unsuccessful response initiating rabobank sepa credit transfer payment.", e);
            return null;
        } catch (IOException e) {
            LOG.error("I/O error initiating rabobank sepa credit transfer payment.", e);
            return null;
        }
    }

    private RaboSepaCreditTransfer toRabo(BCSepaCreditTransfer sepaCreditTransfer) {

        RaboSepaCreditTransfer result = new RaboSepaCreditTransfer();

        RaboSepaCreditTransferAccount creditor = new RaboSepaCreditTransferAccount();
        creditor.setIban(sepaCreditTransfer.getCreditorIban());
        result.setCreditorAccount(creditor);

        result.setCreditorName(sepaCreditTransfer.getCreditorName());

        RaboSepaCreditTransferAccount debtor = new RaboSepaCreditTransferAccount();
        debtor.setIban(sepaCreditTransfer.getDebtorAccountIban());
        result.setDebtorAccount(debtor);

        result.setEndToEndIdentification(sepaCreditTransfer.getEndToEndId());

        RaboSepaCreditTransferAmount amount = new RaboSepaCreditTransferAmount();
        amount.setContent(String.valueOf(sepaCreditTransfer.getAmount()));
        amount.setCurrency(sepaCreditTransfer.getCurrency());
        result.setInstructedAmount(amount);

        result.setRequestedExecutionDate(sepaCreditTransfer.getRequestedExecutionDate());
//        if (sepaCreditTransfer.getDescription() != null && !sepaCreditTransfer.getDescription().trim().isEmpty()) {
//            result.setRemittanceInformationUnstructured(sepaCreditTransfer.getDescription());
//        }

        RaboSepaCreditTransferRIS ris = new RaboSepaCreditTransferRIS();
        ris.setReference("1515140706132013");

        // TODO: Don't know yet what to do with these fields.
        ris.setReferenceIssuer("CUR");
        ris.setReferenceType("SCOR");
        result.setRemittanceInformationStructured(ris);

        return result;
    }

    private BCPaymentResponse toDomain(PaymentInitiationResponse pir) {
        return new BCPaymentResponse(pir.get_links().getScaRedirect().getHref(), pir.getPaymentId());
    }

    public String getPsuMessage(String paymentId, String ip) {
        try {
            Call<RaboPaymentStatusResponse> call = api.getPaymentStatus(ip, "application/json", paymentId);

            Response<RaboPaymentStatusResponse> response = call.clone().execute();
            if (response.isSuccessful()) {
                throw new ResponseUnsuccessfulException(response.errorBody().string());
            }
            LOG.info("Payment status response from rabobank for paymentId: {}, {}", paymentId, response.body());
            return response.body().getPsuMessage();
        } catch (ResponseUnsuccessfulException e) {
            LOG.error("Unsuccessful response getting payment status from rabobank.", e);
            return null;
        } catch (IOException e) {
            LOG.error("I/O error getting payment status from rabobank.", e);
            return null;
        }
    }
}
