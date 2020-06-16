package nl.management.finance.payment;

public class PaymentResponseDto {
    private final String redirectUrl;
    private final String paymentId;

    public PaymentResponseDto(String redirectUrl, String paymentId) {
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
