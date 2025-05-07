package fioshi.com.github.SmartCash.spent.service;

import fioshi.com.github.SmartCash.spent.domain.dto.MonthlySpentDtoList;
import fioshi.com.github.SmartCash.spent.domain.dto.SpentDtoInsert;

import java.time.Month;
import java.util.List;

public interface SpentService {

    void insertSpent(SpentDtoInsert dtoInsert );
    List<MonthlySpentDtoList> listMontlhySpenceFiltered(Long id);
    MonthlySpentDtoList getSpentResume(Long userId, Month month, int year);

}
