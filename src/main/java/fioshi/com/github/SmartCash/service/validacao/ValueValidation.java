package fioshi.com.github.SmartCash.service.validacao;

import fioshi.com.github.SmartCash.domain.dto.SpentDtoInsert;

public class ValueValidation implements SpentValidation{
    @Override
    public void validation(SpentDtoInsert dtoInsert) {
        if (dtoInsert.value() >= 0)
            throw new RuntimeException("O valor precisa ser valido");
    }
}
