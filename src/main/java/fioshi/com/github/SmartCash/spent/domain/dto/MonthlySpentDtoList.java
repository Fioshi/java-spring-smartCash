package fioshi.com.github.SmartCash.spent.domain.dto;

import fioshi.com.github.SmartCash.spent.domain.model.MonthlyExpense;

import java.time.Month;
import java.util.List;

public record MonthlySpentDtoList(
        Long id,
        int year,
        Month month,
        List<SpentDtoList> spentDtoList

) {
    public MonthlySpentDtoList(MonthlyExpense monthlyExpense){
        this(
                monthlyExpense.getId(),
                monthlyExpense.getYear(),
                monthlyExpense.getMonth(),
                monthlyExpense.getSpents().stream().map(SpentDtoList::new).toList()
        );
    }
}
