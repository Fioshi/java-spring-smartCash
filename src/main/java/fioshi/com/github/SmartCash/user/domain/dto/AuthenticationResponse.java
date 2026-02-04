package fioshi.com.github.SmartCash.user.domain.dto;

public record AuthenticationResponse(
        String token,
        String refreshToken,
        String type,
        UserDtoDetail user
) {
    public AuthenticationResponse(String token, String refreshToken, UserDtoDetail user) {
        this(token, refreshToken, "Bearer", user);
    }
}
