package fioshi.com.github.SmartCash.spent.domain.dto;

import fioshi.com.github.SmartCash.spent.domain.model.MonthlyExpense;
import fioshi.com.github.SmartCash.spent.domain.model.Spent;

import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;

public record MonthlySpentDtoList(
        int year,
        String month,
        List<SpentDtoList> spentDtoList,
        double total,
        double saldoRestante

) {
    public MonthlySpentDtoList(MonthlyExpense monthlyExpense, double salarioMensal){
        this(
                monthlyExpense.getYear(),
                monthlyExpense.getMonth().toString(),
                monthlyExpense.getSpents()
                        .stream()
                        .map(SpentDtoList::new)
                        .toList(),
                monthlyExpense.getSpents().stream().map(Spent::getValue).mapToDouble(Double::doubleValue).sum(),
                salarioMensal - monthlyExpense.getSpents().stream().map(Spent::getValue).mapToDouble(Double::doubleValue).sum());
    }
}
