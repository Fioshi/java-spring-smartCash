package fioshi.com.github.SmartCash.spent.adapter.in.web.dto;

import fioshi.com.github.SmartCash.spent.application.port.in.model.MonthlySpentSummary;

import java.util.List;

public record MonthlySpentDtoList(
        int year,
        String month,
        List<SpentDtoList> spentDtoList,
        double total,
        double saldoRestante,
        double rendimentosExtras
) {
    public MonthlySpentDtoList(MonthlySpentSummary summary) {
        this(
                summary.year(),
                summary.month().toString(),
                summary.spents().stream()
                        .map(SpentDtoList::new)
                        .toList(),
                summary.total(),
                summary.saldoRestante(),
                summary.rendimentosExtras()
        );
    }
}
