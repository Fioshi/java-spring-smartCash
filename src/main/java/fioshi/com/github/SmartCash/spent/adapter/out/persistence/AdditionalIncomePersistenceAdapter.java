package fioshi.com.github.SmartCash.spent.adapter.out.persistence;

import fioshi.com.github.SmartCash.spent.adapter.out.persistence.mapper.AdditionalIncomePersistenceMapper;
import fioshi.com.github.SmartCash.spent.adapter.out.persistence.repository.AdditionalIncomeJpaRepository;
import fioshi.com.github.SmartCash.spent.application.port.out.AdditionalIncomeRepositoryPort;
import fioshi.com.github.SmartCash.spent.domain.model.AdditionalIncome;
import org.springframework.stereotype.Component;

import java.time.Month;
import java.util.List;
import java.util.Optional;

@Component
public class AdditionalIncomePersistenceAdapter implements AdditionalIncomeRepositoryPort {

    private final AdditionalIncomeJpaRepository additionalIncomeJpaRepository;
    private final AdditionalIncomePersistenceMapper mapper;

    public AdditionalIncomePersistenceAdapter(
            AdditionalIncomeJpaRepository additionalIncomeJpaRepository,
            AdditionalIncomePersistenceMapper mapper
    ) {
        this.additionalIncomeJpaRepository = additionalIncomeJpaRepository;
        this.mapper = mapper;
    }

    @Override
    public AdditionalIncome save(AdditionalIncome income) {
        var entity = mapper.toEntity(income);
        var saved = additionalIncomeJpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public List<AdditionalIncome> findByUserId(Long userId) {
        return additionalIncomeJpaRepository.findByUserId(userId).stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<AdditionalIncome> findByUserIdAndAnoAndMes(Long userId, int ano, Month mes) {
        return additionalIncomeJpaRepository.findByUserIdAndAnoAndMes(userId, ano, mes).stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Optional<AdditionalIncome> findById(Long id) {
        return additionalIncomeJpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public void delete(AdditionalIncome income) {
        additionalIncomeJpaRepository.delete(mapper.toEntity(income));
    }
}
