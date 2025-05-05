package fioshi.com.github.SmartCash.spent.repository;

import fioshi.com.github.SmartCash.spent.domain.model.MonthlyExpense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MonthlyExpenseRepository extends JpaRepository<MonthlyExpense, Long> {
    List<MonthlyExpense> findAllByUserId(Long userId);
}
