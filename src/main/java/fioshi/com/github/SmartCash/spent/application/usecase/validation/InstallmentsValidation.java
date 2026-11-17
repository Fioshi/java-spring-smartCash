package fioshi.com.github.SmartCash.spent.application.usecase.validation;

import fioshi.com.github.SmartCash.infra.exception.BusinessException;
import fioshi.com.github.SmartCash.spent.application.port.in.InsertSpentUseCase.InsertSpentCommand;
import fioshi.com.github.SmartCash.spent.domain.model.Type;

public class InstallmentsValidation implements SpentValidation {

    @Override
    public void validate(InsertSpentCommand command) {
        if (command.typeSpent() != Type.CREDIT && command.installments() > 1) {
            throw new BusinessException("Voce so pode ter compras parceladas no credito");
        }
    }
}
