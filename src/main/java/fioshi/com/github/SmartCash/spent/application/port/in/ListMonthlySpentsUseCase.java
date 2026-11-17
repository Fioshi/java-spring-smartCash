package fioshi.com.github.SmartCash.spent.application.port.in;

import fioshi.com.github.SmartCash.spent.application.port.in.model.MonthlySpentSummary;

import java.util.List;

public interface ListMonthlySpentsUseCase {

    List<MonthlySpentSummary> execute(Long userId);
}
