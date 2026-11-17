package fioshi.com.github.SmartCash.spent.adapter.out.persistence.repository;

import fioshi.com.github.SmartCash.spent.adapter.out.persistence.entity.AdditionalIncomeJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Month;
import java.util.List;

public interface AdditionalIncomeJpaRepository extends JpaRepository<AdditionalIncomeJpaEntity, Long> {
    List<AdditionalIncomeJpaEntity> findByUserIdAndAnoAndMes(Long userId, int ano, Month mes);
    List<AdditionalIncomeJpaEntity> findByUserId(Long userId);
}
