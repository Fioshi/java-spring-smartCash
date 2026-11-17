package fioshi.com.github.SmartCash.spent.adapter.out.persistence.mapper;

import fioshi.com.github.SmartCash.spent.adapter.out.persistence.entity.SpentJpaEntity;
import fioshi.com.github.SmartCash.spent.domain.model.Spent;
import fioshi.com.github.SmartCash.user.adapter.out.persistence.mapper.UserPersistenceMapper;
import org.springframework.stereotype.Component;

@Component
public class SpentPersistenceMapper {

    private final UserPersistenceMapper userPersistenceMapper;

    public SpentPersistenceMapper(UserPersistenceMapper userPersistenceMapper) {
        this.userPersistenceMapper = userPersistenceMapper;
    }

    public Spent toDomain(SpentJpaEntity spentJpaEntity) {
        return new Spent(
                spentJpaEntity.getId(),
                spentJpaEntity.getType(),
                spentJpaEntity.getInstallments(),
                spentJpaEntity.getValue(),
                spentJpaEntity.getPlace(),
                spentJpaEntity.getCategorie(),
                spentJpaEntity.getItem(),
                spentJpaEntity.isMonthly(),
                spentJpaEntity.getUser() != null
                        ? userPersistenceMapper.toDomain(spentJpaEntity.getUser())
                        : null
        );
    }

    public SpentJpaEntity toEntity(Spent spent) {
        return new SpentJpaEntity(
                spent.getId(),
                spent.getType(),
                spent.getInstallments(),
                spent.getValue(),
                spent.getPlace(),
                spent.getCategorie(),
                spent.getItem(),
                spent.isMonthly(),
                spent.getUser() != null
                        ? userPersistenceMapper.toEntity(spent.getUser())
                        : null
        );
    }
}