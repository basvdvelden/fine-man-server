package nl.management.finance.bankclient.payment;

public class BCPaymentResponse {
    private final String redirectUrl;
    private final String paymentId;

    public BCPaymentResponse(String redirectUrl, String paymentId) {
        this.redirectUrl = redirectUrl;
        this.paymentId = paymentId;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public String getPaymentId() {
        return paymentId;
    }
}
