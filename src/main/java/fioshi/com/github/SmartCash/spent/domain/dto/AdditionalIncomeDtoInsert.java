package fioshi.com.github.SmartCash.spent.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.Month;

public record AdditionalIncomeDtoInsert(
        @NotNull
        @Positive
        Double valor,
        @NotBlank
        String descricao,
        @NotNull
        int ano,
        @NotNull
        Month mes
) {
}
