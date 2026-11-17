package fioshi.com.github.SmartCash.user.application.port.in;

import fioshi.com.github.SmartCash.user.domain.model.User;

public interface SignUpUserUseCase {

    User execute(SignUpUserCommand command);

    record SignUpUserCommand(
            String name,
            String surname,
            String cpf,
            String email,
            String password,
            Double salarioMensal
    ) {
    }
}
