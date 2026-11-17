package fioshi.com.github.SmartCash.user.application.usecase.validation;

import fioshi.com.github.SmartCash.user.application.port.in.SignUpUserUseCase.SignUpUserCommand;

public interface UserSignUpValidation {

    void validate(SignUpUserCommand command);
}
