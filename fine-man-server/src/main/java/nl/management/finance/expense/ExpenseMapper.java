package nl.management.finance.expense;

import nl.management.finance.label.LabelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExpenseMapper {
    private final LabelMapper labelMapper;

    public ExpenseMapper(LabelMapper labelMapper) {
        this.labelMapper = labelMapper;
    }

    public List<ExpenseDto> toDto(List<Expense> expenses) {
        List<ExpenseDto> dtos = new ArrayList<>();
        for (Expense expense: expenses) {
            dtos.add(toDto(expense));
        }
        return dtos;
    }

    public ExpenseDto toDto(Expense expense) {
        ExpenseDto dto = new ExpenseDto();
        dto.setId(expense.getId());
        dto.setAmount(expense.getTotalAmount());
        dto.setLabel(labelMapper.toDto(expense.getLabel()));
        return dto;
    }
}
