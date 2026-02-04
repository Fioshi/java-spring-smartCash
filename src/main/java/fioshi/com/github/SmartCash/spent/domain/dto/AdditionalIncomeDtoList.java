package fioshi.com.github.SmartCash.spent.domain.dto;

import fioshi.com.github.SmartCash.spent.domain.model.AdditionalIncome;

public record AdditionalIncomeDtoList(
        Long id,
        Double valor,
        String descricao,
        int ano,
        String mes
) {
    public AdditionalIncomeDtoList(AdditionalIncome income) {
        this(
                income.getId(),
                income.getValor(),
                income.getDescricao(),
                income.getAno(),
                income.getMes().toString()
        );
    }
}
