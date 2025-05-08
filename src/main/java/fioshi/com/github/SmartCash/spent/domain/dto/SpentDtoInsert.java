package fioshi.com.github.SmartCash.spent.domain.dto;

import fioshi.com.github.SmartCash.spent.domain.model.Type;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SpentDtoInsert(
        @NotNull
        Long idUser,
        @NotNull
        Type typeSpent,
        int  installments,
        @NotNull
        double value,
        @NotBlank
        String place,
        @NotBlank
        String item,
        @NotNull
        boolean isMonthly
) {
}
