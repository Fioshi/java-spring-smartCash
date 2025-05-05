package fioshi.com.github.SmartCash.user.domain.dto;

import jakarta.validation.constraints.NotBlank;

public record UserDtoSignIn(
        @NotBlank
        String email,
        @NotBlank
        String password
) {
}
