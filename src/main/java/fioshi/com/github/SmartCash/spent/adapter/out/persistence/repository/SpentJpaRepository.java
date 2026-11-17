package fioshi.com.github.SmartCash.spent.adapter.out.persistence.repository;

import fioshi.com.github.SmartCash.spent.adapter.out.persistence.entity.SpentJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SpentJpaRepository extends JpaRepository<SpentJpaEntity, Long> {
    Optional<SpentJpaEntity> findByIdAndUserId(Long id, Long userId);
    List<SpentJpaEntity> findAllByUserIdAndIsMonthlyTrue(Long id);
}
