package nl.management.finance.payment;

import nl.management.finance.AuthContext;
import nl.management.finance.bankaccount.BankAccountMapper;
import nl.management.finance.bankclient.payment.BCPaymentResponse;
import nl.management.finance.bankclient.payment.BCSepaCreditTransfer;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {
    private final AuthContext context;
    private final BankAccountMapper bankAccountMapper;

    public PaymentMapper(AuthContext context, BankAccountMapper bankAccountMapper) {
        this.context = context;
        this.bankAccountMapper = bankAccountMapper;
    }

    public SepaCreditTransfer toDomain(SepaCreditTransferDto dto) {
        return new SepaCreditTransfer(dto.getDebtorAccountIban(), bankAccountMapper.toEntity(dto.getBankAccount()), dto.getAmount(),
                dto.getCurrency(), dto.getRequestedExecutionDate(), dto.getDescription(), dto.getPaymentReference());
    }

    public PaymentResponse toDomain(BCPaymentResponse bcPaymentResponse) {
        return new PaymentResponse(bcPaymentResponse.getRedirectUrl(), bcPaymentResponse.getPaymentId());
    }

    public PaymentResponseDto toDto(PaymentResponse paymentResponse) {
        return new PaymentResponseDto(paymentResponse.getRedirectUrl(), paymentResponse.getPaymentId());
    }

    public BCSepaCreditTransfer toBC(SepaCreditTransfer domain) {
        return new BCSepaCreditTransfer(
                domain.getDebtorAccountIban(),
                domain.getCreditorAccount().getName(),
                domain.getCreditorAccount().getIban(),
                domain.getCreditorAccount().getCurrency(),
                domain.getAmount(),
                domain.getCurrency(),
                domain.getRequestedExecutionDate(),
                domain.getDescription(),
                domain.getPaymentReference()
        );
    }
}
