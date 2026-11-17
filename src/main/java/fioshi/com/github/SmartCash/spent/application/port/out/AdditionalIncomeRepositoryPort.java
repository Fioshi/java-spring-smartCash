package fioshi.com.github.SmartCash.spent.application.port.out;

import fioshi.com.github.SmartCash.spent.domain.model.AdditionalIncome;

import java.time.Month;
import java.util.List;
import java.util.Optional;

public interface AdditionalIncomeRepositoryPort {

    AdditionalIncome save(AdditionalIncome income);

    List<AdditionalIncome> findByUserId(Long userId);

    List<AdditionalIncome> findByUserIdAndAnoAndMes(Long userId, int ano, Month mes);

    Optional<AdditionalIncome> findById(Long id);

    void delete(AdditionalIncome income);
}
