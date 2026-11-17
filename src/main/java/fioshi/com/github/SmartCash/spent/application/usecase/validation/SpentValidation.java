package fioshi.com.github.SmartCash.spent.application.usecase.validation;

import fioshi.com.github.SmartCash.spent.application.port.in.InsertSpentUseCase.InsertSpentCommand;

public interface SpentValidation {

    void validate(InsertSpentCommand command);
}
