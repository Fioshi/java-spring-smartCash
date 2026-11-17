package fioshi.com.github.SmartCash.user.application.usecase;

import fioshi.com.github.SmartCash.user.application.port.in.SignUpUserUseCase;
import fioshi.com.github.SmartCash.user.application.port.out.PasswordEncoderPort;
import fioshi.com.github.SmartCash.user.application.port.out.UserRepositoryPort;
import fioshi.com.github.SmartCash.user.application.usecase.validation.UserSignUpValidation;
import fioshi.com.github.SmartCash.user.domain.model.User;

import java.util.List;

public class SignUpUserUseCaseImpl implements SignUpUserUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final List<UserSignUpValidation> validations;
    private final PasswordEncoderPort passwordEncoderPort;

    public SignUpUserUseCaseImpl(
            UserRepositoryPort userRepositoryPort,
            List<UserSignUpValidation> validations,
            PasswordEncoderPort passwordEncoderPort
    ) {
        this.userRepositoryPort = userRepositoryPort;
        this.validations = validations;
        this.passwordEncoderPort = passwordEncoderPort;
    }

    @Override
    public User execute(SignUpUserCommand command) {
        validations.forEach(validation -> validation.validate(command));

        String encodedPassword = passwordEncoderPort.encode(command.password());
        User newUser = User.createNew(
                command.name(),
                command.surname(),
                command.cpf(),
                command.email(),
                encodedPassword,
                command.salarioMensal()
        );

        return userRepositoryPort.save(newUser);
    }
}
