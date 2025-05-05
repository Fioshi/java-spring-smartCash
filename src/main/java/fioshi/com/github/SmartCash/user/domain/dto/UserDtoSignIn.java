package fioshi.com.github.SmartCash.user.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserDtoSignIn(

        @NotBlank
        @Email
        String email,
        @NotBlank
        String password
) {
}
