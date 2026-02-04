package fioshi.com.github.SmartCash.spent.repository;

import fioshi.com.github.SmartCash.spent.domain.model.AdditionalIncome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Month;
import java.util.List;

@Repository
public interface AdditionalIncomeRepository extends JpaRepository<AdditionalIncome, Long> {
    List<AdditionalIncome> findByUserIdAndAnoAndMes(Long userId, int ano, Month mes);
    List<AdditionalIncome> findByUserId(Long userId);
}
