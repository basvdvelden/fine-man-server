package nl.management.finance.bankclient.payment;

public class PaymentInitiationLink {
    private String href;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    @Override
    public String toString() {
        return String.format("[href=%s]", href);
    }
}
