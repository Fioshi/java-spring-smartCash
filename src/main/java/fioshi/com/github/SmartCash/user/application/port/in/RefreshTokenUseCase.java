package fioshi.com.github.SmartCash.user.application.port.in;

import fioshi.com.github.SmartCash.user.application.port.in.model.AuthResult;

public interface RefreshTokenUseCase {

    AuthResult execute(RefreshTokenCommand command);

    record RefreshTokenCommand(String refreshToken) {
    }
}
