package nl.management.finance.payment;

public class PaymentResponse {
    private final String redirectUrl;
    private final String paymentId;

    public PaymentResponse(String redirectUrl, String paymentId) {
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
