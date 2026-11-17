package fioshi.com.github.SmartCash.spent.adapter.out.persistence.mapper;

import fioshi.com.github.SmartCash.spent.adapter.out.persistence.entity.AdditionalIncomeJpaEntity;
import fioshi.com.github.SmartCash.spent.domain.model.AdditionalIncome;
import fioshi.com.github.SmartCash.user.adapter.out.persistence.mapper.UserPersistenceMapper;
import org.springframework.stereotype.Component;

@Component
public class AdditionalIncomePersistenceMapper {

    private final UserPersistenceMapper userPersistenceMapper;

    public AdditionalIncomePersistenceMapper(UserPersistenceMapper userPersistenceMapper) {
        this.userPersistenceMapper = userPersistenceMapper;
    }

    public AdditionalIncome toDomain(AdditionalIncomeJpaEntity entity) {
        return new AdditionalIncome(
                entity.getId(),
                entity.getValor(),
                entity.getDescricao(),
                entity.getAno(),
                entity.getMes(),
                entity.getUser() != null
                        ? userPersistenceMapper.toDomain(entity.getUser())
                        : null
        );
    }

    public AdditionalIncomeJpaEntity toEntity(AdditionalIncome domain) {
        return new AdditionalIncomeJpaEntity(
                domain.getId(),
                domain.getValor(),
                domain.getDescricao(),
                domain.getAno(),
                domain.getMes(),
                domain.getUser() != null
                        ? userPersistenceMapper.toEntity(domain.getUser())
                        : null
        );
    }
}
