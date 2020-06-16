package nl.management.finance.bankclient.payment;

import java.util.Locale;

public class RaboSepaCreditTransferAmount {
    private String content;
    private String currency;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "[\ncontent=%s, \ncurrency=%s\n]", content, currency);
    }
}
