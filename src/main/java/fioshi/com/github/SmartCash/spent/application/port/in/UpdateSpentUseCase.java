package fioshi.com.github.SmartCash.spent.application.port.in;

import fioshi.com.github.SmartCash.spent.domain.model.Spent;
import fioshi.com.github.SmartCash.spent.domain.model.SpentCategorie;
import fioshi.com.github.SmartCash.spent.domain.model.Type;

public interface UpdateSpentUseCase {

    Spent execute(Long id, UpdateSpentCommand command);

    record UpdateSpentCommand(
            Type typeSpent,
            int installments,
            double value,
            String place,
            String item,
            SpentCategorie categorie
    ) {
    }
}
