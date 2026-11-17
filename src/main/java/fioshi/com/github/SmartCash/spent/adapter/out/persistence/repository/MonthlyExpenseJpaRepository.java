package fioshi.com.github.SmartCash.spent.adapter.out.persistence.repository;

import fioshi.com.github.SmartCash.spent.adapter.out.persistence.entity.MonthlyExpenseJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Month;
import java.util.List;
import java.util.Optional;

public interface MonthlyExpenseJpaRepository extends JpaRepository<MonthlyExpenseJpaEntity, Long> {
    List<MonthlyExpenseJpaEntity> findAllByUserId(Long userId);
    Optional<MonthlyExpenseJpaEntity> findByUserIdAndMonthAndYear(Long userId, Month month, int year);
    List<MonthlyExpenseJpaEntity> findAllBySpentJpaEntities_Id(Long id);
}
