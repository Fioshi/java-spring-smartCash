package fioshi.com.github.SmartCash.user.application.usecase;

import fioshi.com.github.SmartCash.infra.exception.BusinessException;
import fioshi.com.github.SmartCash.user.application.port.in.model.AuthResult;
import fioshi.com.github.SmartCash.user.application.port.in.AuthenticateUserUseCase;
import fioshi.com.github.SmartCash.user.application.port.out.AuthenticationPort;
import fioshi.com.github.SmartCash.user.application.port.out.TokenProviderPort;
import fioshi.com.github.SmartCash.user.application.port.out.UserRepositoryPort;

public class AuthenticateUserUseCaseImpl implements AuthenticateUserUseCase {

    private final AuthenticationPort authenticationPort;
    private final TokenProviderPort tokenProviderPort;
    private final UserRepositoryPort userRepositoryPort;

    public AuthenticateUserUseCaseImpl(
            AuthenticationPort authenticationPort,
            TokenProviderPort tokenProviderPort,
            UserRepositoryPort userRepositoryPort
    ) {
        this.authenticationPort = authenticationPort;
        this.tokenProviderPort = tokenProviderPort;
        this.userRepositoryPort = userRepositoryPort;
    }

    @Override
    public AuthResult execute(AuthenticateUserCommand command) {
        authenticationPort.authenticate(command.email(), command.password());

        var user = userRepositoryPort.findByEmail(command.email())
                .orElseThrow(() -> new BusinessException("User not found"));

        String token = tokenProviderPort.generateToken(user.getEmail());
        String refreshToken = tokenProviderPort.generateRefreshToken(user.getEmail());

        return new AuthResult(token, refreshToken, user);
    }
}
