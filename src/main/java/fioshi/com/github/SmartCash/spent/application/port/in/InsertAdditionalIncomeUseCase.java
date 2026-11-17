package fioshi.com.github.SmartCash.spent.application.port.in;

import fioshi.com.github.SmartCash.spent.domain.model.AdditionalIncome;

import java.time.Month;

public interface InsertAdditionalIncomeUseCase {

    AdditionalIncome execute(InsertAdditionalIncomeCommand command);

    record InsertAdditionalIncomeCommand(
            Long userId,
            Double valor,
            String descricao,
            int ano,
            Month mes
    ) {
    }
}
