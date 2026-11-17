package fioshi.com.github.SmartCash.user.adapter.in.web.dto;

import fioshi.com.github.SmartCash.user.application.port.in.model.AuthResult;

public record AuthenticationResponse(
        String token,
        String refreshToken,
        String type,
        UserDtoDetail user
) {
    public AuthenticationResponse(String token, String refreshToken, UserDtoDetail user) {
        this(token, refreshToken, "Bearer", user);
    }

    public static AuthenticationResponse from(AuthResult result) {
        return new AuthenticationResponse(
                result.token(),
                result.refreshToken(),
                new UserDtoDetail(result.user())
        );
    }
}
