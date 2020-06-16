package nl.management.finance.bankclient.api;

import nl.management.finance.bankclient.auth.RaboOAuthResponse;
import nl.management.finance.bankclient.balance.RaboAccountBalance;
import nl.management.finance.bankclient.payment.PaymentInitiationResponse;
import nl.management.finance.bankclient.payment.RaboPaymentStatusResponse;
import nl.management.finance.bankclient.payment.RaboSepaCreditTransfer;
import nl.management.finance.bankclient.transaction.RaboTransactions;
import nl.management.finance.lib.rabo.pojo.bankaccount.RaboBankAccounts;
import retrofit2.Call;
import retrofit2.http.*;

import java.time.ZonedDateTime;

public interface RaboApi {
    @GET("payments/account-information/ais/v3/accounts/{accountId}/transactions")
    Call<RaboTransactions> getTransactions(@Path("accountId") String accountId,
                                           @Query("size") Integer size,
                                           @Query("page") Integer page,
                                           @Query("dateFrom") ZonedDateTime dateFrom,
                                           @Query("dateTo") ZonedDateTime dateTo,
                                           @Query("bookingStatus") String bookingStatus);

    @GET("payments/account-information/ais/v3/accounts")
    Call<RaboBankAccounts> getBankAccounts();

    @GET("payments/account-information/ais/v3/accounts/{accountId}/balances")
    Call<RaboAccountBalance> getBalances(@Path("accountId") String accountId);

    @FormUrlEncoded
    @POST("oauth2/token")
    Call<RaboOAuthResponse> authenticate(@Header("Authorization") String authHeader,
                                         @Field("grant_type") String grantType,
                                         @Field("code") String code);

    @POST("payments/payment-initiation/pis/v1/payments/sepa-credit-transfers")
    Call<PaymentInitiationResponse> initiatePayment(@Header("PSU-IP-Address") String userIp,
                                                    @Header("TPP-Redirect-URI") String redirectUri,
                                                    @Header("Content-Type") String contentType,
                                                    @Body RaboSepaCreditTransfer body);

    @GET("payments/payment-initiation/pis/v1/payments/sepa-credit-transfers/{paymentId}/status")
    Call<RaboPaymentStatusResponse> getPaymentStatus(@Header("PSU-IP-Address") String userIp,
                                                     @Header("Content-Type") String contentType,
                                                     @Path("paymentId") String paymentId);
}
