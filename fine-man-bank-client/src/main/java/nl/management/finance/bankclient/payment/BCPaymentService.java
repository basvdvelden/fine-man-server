package nl.management.finance.bankclient.payment;

import nl.management.finance.bankclient.IAppInterface;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class BCPaymentService {
    private final IAppInterface context;

    public BCPaymentService(@Qualifier("external") IAppInterface context) {
        this.context = context;
    }

    public BCPaymentResponse initiatePayment(BCSepaCreditTransfer sepaCreditTransfer, String ip, String redirectUrl) {
        return context.getBankAdapter().initiatePayment(sepaCreditTransfer, ip, redirectUrl);
    }

    public String getPsuMessage(String paymentId, String ip) {
        return context.getBankAdapter().getPsuMessage(paymentId, ip);
    }
}
