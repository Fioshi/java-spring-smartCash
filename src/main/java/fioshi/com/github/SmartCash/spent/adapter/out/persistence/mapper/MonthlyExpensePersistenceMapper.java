package fioshi.com.github.SmartCash.spent.adapter.out.persistence.mapper;

import fioshi.com.github.SmartCash.spent.adapter.out.persistence.entity.MonthlyExpenseJpaEntity;
import fioshi.com.github.SmartCash.spent.domain.model.MonthlyExpense;
import fioshi.com.github.SmartCash.user.adapter.out.persistence.mapper.UserPersistenceMapper;
import org.springframework.stereotype.Component;

@Component
public class MonthlyExpensePersistenceMapper {

    private final SpentPersistenceMapper spentPersistenceMapper;
    private final UserPersistenceMapper userPersistenceMapper;

    public MonthlyExpensePersistenceMapper(
            SpentPersistenceMapper spentPersistenceMapper,
            UserPersistenceMapper userPersistenceMapper
    ) {
        this.spentPersistenceMapper = spentPersistenceMapper;
        this.userPersistenceMapper = userPersistenceMapper;
    }

    public MonthlyExpense toDomain(MonthlyExpenseJpaEntity entity) {
        return new MonthlyExpense(
                entity.getId(),
                entity.getYear(),
                entity.getMonth(),
                entity.getSpentJpaEntities().stream()
                        .map(spentPersistenceMapper::toDomain)
                        .toList(),
                entity.getUser() != null
                        ? userPersistenceMapper.toDomain(entity.getUser())
                        : null
        );
    }

    public MonthlyExpenseJpaEntity toEntity(MonthlyExpense domain) {
        return new MonthlyExpenseJpaEntity(
                domain.getId(),
                domain.getYear(),
                domain.getMonth(),
                domain.getSpents().stream()
                        .map(spentPersistenceMapper::toEntity)
                        .toList(),
                domain.getUser() != null
                        ? userPersistenceMapper.toEntity(domain.getUser())
                        : null
        );
    }
}
