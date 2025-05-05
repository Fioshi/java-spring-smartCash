package fioshi.com.github.SmartCash.user.domain.dto;

import jakarta.validation.constraints.NotBlank;

public record UserDtoSignUp(
        @NotBlank
        String name,
        @NotBlank
        String surname,
        @NotBlank
        String cpf,
        @NotBlank
        String email,
        @NotBlank
        String password
) {
}
