package fioshi.com.github.SmartCash.spent.repository;

import fioshi.com.github.SmartCash.spent.domain.dto.MonthlySpentDtoList;
import fioshi.com.github.SmartCash.spent.domain.model.MonthlyExpense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Month;
import java.util.List;
import java.util.Optional;

public interface MonthlyExpenseRepository extends JpaRepository<MonthlyExpense, Long> {
    List<MonthlyExpense> findAllByUserId(Long userId);
    Optional<MonthlyExpense> findByUserIdAndMonthAndYear(Long userId, Month month, int year);
}
