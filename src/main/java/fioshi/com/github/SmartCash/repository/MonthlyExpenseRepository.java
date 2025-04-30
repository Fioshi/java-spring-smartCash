package fioshi.com.github.SmartCash.repository;

import fioshi.com.github.SmartCash.domain.MonthlyExpense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MonthlyExpenseRepository extends JpaRepository<MonthlyExpense, Long> {
    List<MonthlyExpense> findAllByUserId(Long userId);
}
