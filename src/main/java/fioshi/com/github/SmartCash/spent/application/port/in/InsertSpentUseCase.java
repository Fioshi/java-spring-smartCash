package fioshi.com.github.SmartCash.spent.application.port.in;

import fioshi.com.github.SmartCash.spent.domain.model.Spent;
import fioshi.com.github.SmartCash.spent.domain.model.SpentCategorie;
import fioshi.com.github.SmartCash.spent.domain.model.Type;

public interface InsertSpentUseCase {

    Spent execute(InsertSpentCommand command);

    record InsertSpentCommand(
            Long userId,
            Type typeSpent,
            int installments,
            double value,
            String place,
            String item,
            SpentCategorie categorie
    ) {
    }
}
