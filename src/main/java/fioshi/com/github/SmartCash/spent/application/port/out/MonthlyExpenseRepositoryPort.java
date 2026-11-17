package fioshi.com.github.SmartCash.spent.application.port.out;

import fioshi.com.github.SmartCash.spent.domain.model.MonthlyExpense;

import java.time.Month;
import java.util.List;
import java.util.Optional;

public interface MonthlyExpenseRepositoryPort {

    MonthlyExpense save(MonthlyExpense monthlyExpense);

    List<MonthlyExpense> findAllByUserId(Long userId);

    Optional<MonthlyExpense> findByUserIdAndMonthAndYear(Long userId, Month month, int year);

    List<MonthlyExpense> findAllBySpentId(Long spentId);
}
