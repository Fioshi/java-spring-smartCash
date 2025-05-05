package fioshi.com.github.SmartCash.spent.service.validacao;

import fioshi.com.github.SmartCash.spent.domain.dto.SpentDtoInsert;

public interface SpentValidation {

    void validation(SpentDtoInsert dtoInsert);

}
