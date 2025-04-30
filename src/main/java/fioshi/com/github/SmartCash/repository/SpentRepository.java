package fioshi.com.github.SmartCash.repository;

import fioshi.com.github.SmartCash.domain.Spent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpentRepository extends JpaRepository<Spent, Long> {
}
