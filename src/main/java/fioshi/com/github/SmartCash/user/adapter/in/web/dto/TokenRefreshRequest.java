package fioshi.com.github.SmartCash.user.adapter.in.web.dto;

import jakarta.validation.constraints.NotBlank;

public record TokenRefreshRequest(
        @NotBlank(message = "Refresh token is required")
        String refreshToken
) {
}
