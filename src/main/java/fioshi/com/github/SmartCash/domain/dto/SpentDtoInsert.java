package fioshi.com.github.SmartCash.domain.dto;

import fioshi.com.github.SmartCash.domain.Type;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SpentDtoInsert(
        @NotNull
        Long idUser,
        @NotNull
        Type typeSpent,
        @NotNull
        int  installments,
        @NotNull
        double value,
        @NotBlank
        String place
) {
}
