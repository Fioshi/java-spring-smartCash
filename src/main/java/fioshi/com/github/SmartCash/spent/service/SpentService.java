package fioshi.com.github.SmartCash.spent.service;

import fioshi.com.github.SmartCash.spent.domain.dto.*;
import fioshi.com.github.SmartCash.spent.domain.model.Spent;
import fioshi.com.github.SmartCash.spent.domain.model.SpentCategorie;

import java.time.Month;
import java.util.List;

public interface SpentService {

    Spent insertSpent(SpentDtoInsert dtoInsert );
    List<MonthlySpentDtoList> listMontlhySpenceFiltered(Long id);
    MonthlySpentDtoList getSpentResume(Long userId, Month month, int year);
    SpentDtoDetail getSpentDetail (Long userId , Long spentId);
    List<SpentCategorieDtoList> getCategories();
    SpentDtoDetail updateSpent(Long id ,SpentDtoUpdate dtoUpdate);
    void deleteSpent(Long id);
}
