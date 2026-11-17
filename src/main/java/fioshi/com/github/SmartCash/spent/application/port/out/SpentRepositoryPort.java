package fioshi.com.github.SmartCash.spent.application.port.out;

import fioshi.com.github.SmartCash.spent.domain.model.Spent;

import java.util.List;
import java.util.Optional;

public interface SpentRepositoryPort {

    Spent save(Spent spent);

    Optional<Spent> findById(Long id);

    Optional<Spent> findByIdAndUserId(Long id, Long userId);

    List<Spent> findAllByUserIdAndIsMonthlyTrue(Long userId);

    void delete(Spent spent);
}
