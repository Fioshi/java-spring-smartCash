package fioshi.com.github.SmartCash.spent.adapter.out.persistence;

import fioshi.com.github.SmartCash.spent.adapter.out.persistence.mapper.SpentPersistenceMapper;
import fioshi.com.github.SmartCash.spent.adapter.out.persistence.repository.SpentJpaRepository;
import fioshi.com.github.SmartCash.spent.application.port.out.SpentRepositoryPort;
import fioshi.com.github.SmartCash.spent.domain.model.Spent;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class SpentPersistenceAdapter implements SpentRepositoryPort {

    private final SpentJpaRepository spentJpaRepository;
    private final SpentPersistenceMapper mapper;

    public SpentPersistenceAdapter(SpentJpaRepository spentJpaRepository, SpentPersistenceMapper mapper) {
        this.spentJpaRepository = spentJpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Spent save(Spent spent) {
        var entity = mapper.toEntity(spent);
        var saved = spentJpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Spent> findById(Long id) {
        return spentJpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<Spent> findByIdAndUserId(Long id, Long userId) {
        return spentJpaRepository.findByIdAndUserId(id, userId).map(mapper::toDomain);
    }

    @Override
    public List<Spent> findAllByUserIdAndIsMonthlyTrue(Long userId) {
        return spentJpaRepository.findAllByUserIdAndIsMonthlyTrue(userId).stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void delete(Spent spent) {
        spentJpaRepository.delete(mapper.toEntity(spent));
    }
}
