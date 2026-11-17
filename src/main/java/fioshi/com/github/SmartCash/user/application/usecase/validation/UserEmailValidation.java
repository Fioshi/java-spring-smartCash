package fioshi.com.github.SmartCash.user.application.usecase.validation;

import fioshi.com.github.SmartCash.infra.exception.BusinessException;
import fioshi.com.github.SmartCash.user.application.port.in.SignUpUserUseCase.SignUpUserCommand;
import fioshi.com.github.SmartCash.user.application.port.out.UserRepositoryPort;

public class UserEmailValidation implements UserSignUpValidation {

    private final UserRepositoryPort userRepositoryPort;

    public UserEmailValidation(UserRepositoryPort userRepositoryPort) {
        this.userRepositoryPort = userRepositoryPort;
    }

    @Override
    public void validate(SignUpUserCommand command) {
        if (userRepositoryPort.existsByEmail(command.email())) {
            throw new BusinessException("Email ja registrado");
        }
    }
}
