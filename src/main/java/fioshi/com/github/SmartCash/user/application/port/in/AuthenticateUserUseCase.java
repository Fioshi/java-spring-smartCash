package fioshi.com.github.SmartCash.user.application.port.in;

import fioshi.com.github.SmartCash.user.application.port.in.model.AuthResult;

public interface AuthenticateUserUseCase {

    AuthResult execute(AuthenticateUserCommand command);

    record AuthenticateUserCommand(
            String email,
            String password
    ) {
    }
}
