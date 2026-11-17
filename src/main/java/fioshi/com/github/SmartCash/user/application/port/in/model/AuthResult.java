package fioshi.com.github.SmartCash.user.application.port.in.model;

import fioshi.com.github.SmartCash.user.domain.model.User;

public record AuthResult(
        String token,
        String refreshToken,
        User user
) {
}
