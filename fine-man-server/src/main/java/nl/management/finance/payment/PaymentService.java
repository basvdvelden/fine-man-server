package nl.management.finance.payment;

import nl.management.finance.AppProperties;
import nl.management.finance.bankclient.payment.BCPaymentService;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    private final BCPaymentService service;
    private final PaymentMapper mapper;
    private final AppProperties properties;

    public PaymentService(BCPaymentService service, PaymentMapper mapper, AppProperties properties) {
        this.service = service;
        this.mapper = mapper;
        this.properties = properties;
    }

    public PaymentResponse initiatePayment(SepaCreditTransfer sepaCreditTransfer, String ip) {
        return mapper.toDomain(service.initiatePayment(mapper.toBC(sepaCreditTransfer), ip,
                properties.getPostPaymentRedirectUrl()));
    }

    public String getPsuMessage(String paymentId, String ip) {
        return service.getPsuMessage(paymentId, ip);
    }
}
