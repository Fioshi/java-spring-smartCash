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
        double saldoRestante,
        double rendimentosExtras

) {
    public MonthlySpentDtoList(MonthlyExpense monthlyExpense, double totalIncome){
        this(
                monthlyExpense.getYear(),
                monthlyExpense.getMonth().toString(),
                monthlyExpense.getSpents()
                        .stream()
                        .map(SpentDtoList::new)
                        .toList(),
                monthlyExpense.getSpents().stream().map(Spent::getValue).mapToDouble(Double::doubleValue).sum(),
                totalIncome - monthlyExpense.getSpents().stream().map(Spent::getValue).mapToDouble(Double::doubleValue).sum(),
                0.0);
    }

    public MonthlySpentDtoList(MonthlyExpense monthlyExpense, double salarioMensal, double rendimentosExtras){
        this(
                monthlyExpense.getYear(),
                monthlyExpense.getMonth().toString(),
                monthlyExpense.getSpents()
                        .stream()
                        .map(SpentDtoList::new)
                        .toList(),
                monthlyExpense.getSpents().stream().map(Spent::getValue).mapToDouble(Double::doubleValue).sum(),
                (salarioMensal + rendimentosExtras) - monthlyExpense.getSpents().stream().map(Spent::getValue).mapToDouble(Double::doubleValue).sum(),
                rendimentosExtras);
    }
}
