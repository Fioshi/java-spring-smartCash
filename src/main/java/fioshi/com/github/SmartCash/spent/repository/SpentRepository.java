package fioshi.com.github.SmartCash.spent.repository;

import fioshi.com.github.SmartCash.spent.domain.model.Spent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpentRepository extends JpaRepository<Spent, Long> {
}
