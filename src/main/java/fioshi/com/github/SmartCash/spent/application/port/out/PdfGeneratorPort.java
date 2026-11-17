package fioshi.com.github.SmartCash.spent.application.port.out;

import fioshi.com.github.SmartCash.spent.application.port.in.model.MonthlySpentSummary;
import fioshi.com.github.SmartCash.user.domain.model.User;

import java.util.List;

public interface PdfGeneratorPort {

    byte[] exportMonthlyExpensesToPdf(List<MonthlySpentSummary> monthlyExpenses, User user);

    byte[] exportSingleMonthToPdf(MonthlySpentSummary monthlyExpense, User user);
}
