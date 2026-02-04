package fioshi.com.github.SmartCash.spent.service;

import fioshi.com.github.SmartCash.spent.domain.dto.AdditionalIncomeDtoInsert;
import fioshi.com.github.SmartCash.spent.domain.dto.AdditionalIncomeDtoList;
import fioshi.com.github.SmartCash.spent.domain.model.AdditionalIncome;

import java.time.Month;
import java.util.List;

public interface AdditionalIncomeService {
    AdditionalIncome insertIncome(Long userId, AdditionalIncomeDtoInsert dto);
    List<AdditionalIncomeDtoList> listByUser(Long userId);
    Double getAdditionalIncomeByMonth(Long userId, int ano, Month mes);
    void deleteIncome(Long id);
}
