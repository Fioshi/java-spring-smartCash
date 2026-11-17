package fioshi.com.github.SmartCash.spent.application.port.in;

import fioshi.com.github.SmartCash.spent.application.port.in.model.MonthlySpentSummary;

import java.time.Month;

public interface GetSpentResumeUseCase {

    MonthlySpentSummary execute(Long userId, Month month, int year);
}
