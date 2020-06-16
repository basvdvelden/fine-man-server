package nl.management.finance.expense;

import nl.management.finance.label.LabelDto;

import java.util.UUID;

public class ExpenseDto {
    private UUID id;
    private LabelDto label;
    private Double amount;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LabelDto getLabel() {
        return label;
    }

    public void setLabel(LabelDto label) {
        this.label = label;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
