package fioshi.com.github.SmartCash.user.application.usecase;

import fioshi.com.github.SmartCash.infra.exception.BusinessException;
import fioshi.com.github.SmartCash.user.application.port.in.model.AuthResult;
import fioshi.com.github.SmartCash.user.application.port.in.RefreshTokenUseCase;
import fioshi.com.github.SmartCash.user.application.port.out.TokenProviderPort;
import fioshi.com.github.SmartCash.user.application.port.out.UserRepositoryPort;

public class RefreshTokenUseCaseImpl implements RefreshTokenUseCase {

    private final TokenProviderPort tokenProviderPort;
    private final UserRepositoryPort userRepositoryPort;

    public RefreshTokenUseCaseImpl(
            TokenProviderPort tokenProviderPort,
            UserRepositoryPort userRepositoryPort
    ) {
        this.tokenProviderPort = tokenProviderPort;
        this.userRepositoryPort = userRepositoryPort;
    }

    @Override
    public AuthResult execute(RefreshTokenCommand command) {
        try {
            String username = tokenProviderPort.extractUsername(command.refreshToken());

            var user = userRepositoryPort.findByEmail(username)
                    .orElseThrow(() -> new BusinessException("User not found"));

            if (!tokenProviderPort.validateToken(command.refreshToken(), username)) {
                throw new BusinessException("Invalid refresh token");
            }

            String newToken = tokenProviderPort.generateToken(username);
            String newRefreshToken = tokenProviderPort.generateRefreshToken(username);

            return new AuthResult(newToken, newRefreshToken, user);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException("Invalid refresh token");
        }
    }
}
