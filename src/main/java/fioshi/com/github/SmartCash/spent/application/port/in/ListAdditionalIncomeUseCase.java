package fioshi.com.github.SmartCash.spent.application.port.in;

import fioshi.com.github.SmartCash.spent.domain.model.AdditionalIncome;

import java.util.List;

public interface ListAdditionalIncomeUseCase {

    List<AdditionalIncome> execute(Long userId);
}
