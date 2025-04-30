package fioshi.com.github.SmartCash.domain.dto;

import jakarta.validation.constraints.NotBlank;

public record UserDtoSignIn(
        @NotBlank
        String email,
        @NotBlank
        String password
) {
}
