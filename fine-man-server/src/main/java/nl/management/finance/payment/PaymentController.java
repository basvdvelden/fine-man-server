package nl.management.finance.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.websocket.server.PathParam;

@Transactional
@RestController
@RequestMapping("/users/{userId}/banks/{bankId}/bank-accounts/{iban}/payments")
public class PaymentController {
    private final PaymentService service;
    private final PaymentMapper mapper;

    @Autowired
    public PaymentController(PaymentService service, PaymentMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping("/sepa-credit-transfers")
    public PaymentResponseDto initiatePayment(@RequestBody SepaCreditTransferDto sepaCreditTransfer, @PathParam("ip") String ip) {
        return mapper.toDto(service.initiatePayment(mapper.toDomain(sepaCreditTransfer), ip));
    }

    @GetMapping("{paymentId}/psu-message")
    public String getPsuMessage(@PathVariable("paymentId") String paymentId, @PathParam("ip") String ip) {
        return service.getPsuMessage(paymentId, ip);
    }
}
