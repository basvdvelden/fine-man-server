package nl.management.finance.bankclient.payment;

public class RaboSepaCreditTransfer {
//  Mandatory fields
    private RaboSepaCreditTransferAccount creditorAccount;
    private String creditorName;
    private RaboSepaCreditTransferAccount debtorAccount;
    private String endToEndIdentification;
    private RaboSepaCreditTransferAmount instructedAmount;
//  Optional fields
    private String requestedExecutionDate;
    private String remittanceInformationUnstructured;
    private RaboSepaCreditTransferRIS remittanceInformationStructured;

    public RaboSepaCreditTransferAccount getCreditorAccount() {
        return creditorAccount;
    }

    public void setCreditorAccount(RaboSepaCreditTransferAccount creditorAccount) {
        this.creditorAccount = creditorAccount;
    }

    public String getCreditorName() {
        return creditorName;
    }

    public void setCreditorName(String creditorName) {
        this.creditorName = creditorName;
    }

    public RaboSepaCreditTransferAccount getDebtorAccount() {
        return debtorAccount;
    }

    public void setDebtorAccount(RaboSepaCreditTransferAccount debtorAccount) {
        this.debtorAccount = debtorAccount;
    }

    public String getEndToEndIdentification() {
        return endToEndIdentification;
    }

    public void setEndToEndIdentification(String endToEndIdentification) {
        this.endToEndIdentification = endToEndIdentification;
    }

    public RaboSepaCreditTransferAmount getInstructedAmount() {
        return instructedAmount;
    }

    public void setInstructedAmount(RaboSepaCreditTransferAmount instructedAmount) {
        this.instructedAmount = instructedAmount;
    }

    public String getRemittanceInformationUnstructured() {
        return remittanceInformationUnstructured;
    }

    public void setRemittanceInformationUnstructured(String remittanceInformationUnstructured) {
        this.remittanceInformationUnstructured = remittanceInformationUnstructured;
    }

    public RaboSepaCreditTransferRIS getRemittanceInformationStructured() {
        return remittanceInformationStructured;
    }

    public void setRemittanceInformationStructured(RaboSepaCreditTransferRIS remittanceInformationStructured) {
        this.remittanceInformationStructured = remittanceInformationStructured;
    }

    public String getRequestedExecutionDate() {
        return requestedExecutionDate;
    }

    public void setRequestedExecutionDate(String requestedExecutionDate) {
        this.requestedExecutionDate = requestedExecutionDate;
    }

    @Override
    public String toString() {
        return String.format("[\ncreditorAccount=%s, \ncreditorName=%s, \ndebtorAccount=%s, " +
                        "\nendToEndIdentification=%s, \ninstructedAmount=%s, \nrequestedExecutionDate=%s, " +
                        "\nremittanceInformationUnstructured=%s, \nremittanceInformationStructured=%s\n]",
                creditorAccount, creditorName, debtorAccount, endToEndIdentification, instructedAmount,
                requestedExecutionDate, remittanceInformationUnstructured, remittanceInformationStructured);
    }

}
