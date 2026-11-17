package fioshi.com.github.SmartCash.spent.application.usecase.validation;

import fioshi.com.github.SmartCash.infra.exception.BusinessException;
import fioshi.com.github.SmartCash.spent.application.port.in.InsertSpentUseCase.InsertSpentCommand;

public class ValueValidation implements SpentValidation {

    @Override
    public void validate(InsertSpentCommand command) {
        if (command.value() <= 0 && !command.categorie().isRecurring()) {
            throw new BusinessException("O valor precisa ser valido");
        }
    }
}
