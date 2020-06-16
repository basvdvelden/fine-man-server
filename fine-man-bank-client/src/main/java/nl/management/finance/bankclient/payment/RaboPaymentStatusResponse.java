package nl.management.finance.bankclient.payment;

import java.util.List;

public class RaboPaymentStatusResponse {
    private String psuMessage;
    private String scaStatus;
    private List<TPPMessage> tppMessages;
    private String transactionStatus;

    public String getPsuMessage() {
        return psuMessage;
    }

    public void setPsuMessage(String psuMessage) {
        this.psuMessage = psuMessage;
    }

    public String getScaStatus() {
        return scaStatus;
    }

    public void setScaStatus(String scaStatus) {
        this.scaStatus = scaStatus;
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
        return "RaboPaymentStatusResponse{" +
                "psuMessage='" + psuMessage + '\'' +
                ", scaStatus='" + scaStatus + '\'' +
                ", tppMessages=" + tppMessages +
                ", transactionStatus='" + transactionStatus + '\'' +
                '}';
    }
}
