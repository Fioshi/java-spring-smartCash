package fioshi.com.github.SmartCash.spent.application.port.in.model;

import fioshi.com.github.SmartCash.spent.domain.model.MonthlyExpense;
import fioshi.com.github.SmartCash.spent.domain.model.Spent;

import java.time.Month;
import java.util.List;

public record MonthlySpentSummary(
        int year,
        Month month,
        List<Spent> spents,
        double total,
        double saldoRestante,
        double rendimentosExtras
) {
    public static MonthlySpentSummary from(
            MonthlyExpense monthlyExpense,
            double salarioMensal,
            double rendimentosExtras
    ) {
        double total = monthlyExpense.calculateTotal();
        double saldoRestante = (salarioMensal + rendimentosExtras) - total;
        return new MonthlySpentSummary(
                monthlyExpense.getYear(),
                monthlyExpense.getMonth(),
                monthlyExpense.getSpents(),
                total,
                saldoRestante,
                rendimentosExtras
        );
    }
}
