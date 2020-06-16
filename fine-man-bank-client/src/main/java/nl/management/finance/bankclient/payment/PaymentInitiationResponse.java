package nl.management.finance.bankclient.payment;

import java.util.List;

public class PaymentInitiationResponse {
    private PaymentInitiationLinks _links;
    private String paymentId;
    private String psuMessage;
    private List<TPPMessage> tppMessages;
    private String transactionStatus;

    public PaymentInitiationLinks get_links() {
        return _links;
    }

    public void set_links(PaymentInitiationLinks _links) {
        this._links = _links;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getPsuMessage() {
        return psuMessage;
    }

    public void setPsuMessage(String psuMessage) {
        this.psuMessage = psuMessage;
    }

    public List<TPPMessage> getTppMessages() {
        return tppMessages;
    }

    public void setTppMessages(List<TPPMessage> tppMessages) {
        this.tppMessages = tppMessages;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    @Override
    public String toString() {
        return "PaymentInitiationResponse{" +
                "_links=" + _links +
                ", paymentId='" + paymentId + '\'' +
                ", psuMessage='" + psuMessage + '\'' +
                ", tppMessages=" + tppMessages +
                ", transactionStatus='" + transactionStatus + '\'' +
                '}';
    }
}
