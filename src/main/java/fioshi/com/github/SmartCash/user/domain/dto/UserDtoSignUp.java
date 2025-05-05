package fioshi.com.github.SmartCash.user.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserDtoSignUp(
        @NotBlank
        String name,
        @NotBlank
        String surname,
        @NotBlank
        @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 dígitos numéricos.")
        String cpf,
        @NotBlank
        @Email
        String email,
        @NotBlank
        String password
) {
}
