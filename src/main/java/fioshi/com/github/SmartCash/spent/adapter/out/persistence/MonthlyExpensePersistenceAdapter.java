package fioshi.com.github.SmartCash.spent.adapter.out.persistence;

import fioshi.com.github.SmartCash.spent.adapter.out.persistence.mapper.MonthlyExpensePersistenceMapper;
import fioshi.com.github.SmartCash.spent.adapter.out.persistence.repository.MonthlyExpenseJpaRepository;
import fioshi.com.github.SmartCash.spent.application.port.out.MonthlyExpenseRepositoryPort;
import fioshi.com.github.SmartCash.spent.domain.model.MonthlyExpense;
import org.springframework.stereotype.Component;

import java.time.Month;
import java.util.List;
import java.util.Optional;

@Component
public class MonthlyExpensePersistenceAdapter implements MonthlyExpenseRepositoryPort {

    private final MonthlyExpenseJpaRepository monthlyExpenseJpaRepository;
    private final MonthlyExpensePersistenceMapper mapper;

    public MonthlyExpensePersistenceAdapter(
            MonthlyExpenseJpaRepository monthlyExpenseJpaRepository,
            MonthlyExpensePersistenceMapper mapper
    ) {
        this.monthlyExpenseJpaRepository = monthlyExpenseJpaRepository;
        this.mapper = mapper;
    }

    @Override
    public MonthlyExpense save(MonthlyExpense monthlyExpense) {
        var entity = mapper.toEntity(monthlyExpense);
        var saved = monthlyExpenseJpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public List<MonthlyExpense> findAllByUserId(Long userId) {
        return monthlyExpenseJpaRepository.findAllByUserId(userId).stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Optional<MonthlyExpense> findByUserIdAndMonthAndYear(Long userId, Month month, int year) {
        return monthlyExpenseJpaRepository.findByUserIdAndMonthAndYear(userId, month, year)
                .map(mapper::toDomain);
    }

    @Override
    public List<MonthlyExpense> findAllBySpentId(Long spentId) {
        return monthlyExpenseJpaRepository.findAllBySpentJpaEntities_Id(spentId).stream()
                .map(mapper::toDomain)
                .toList();
    }
}
