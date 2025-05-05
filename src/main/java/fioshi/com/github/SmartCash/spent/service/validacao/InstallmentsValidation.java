package fioshi.com.github.SmartCash.spent.service.validacao;

import fioshi.com.github.SmartCash.spent.domain.model.Type;
import fioshi.com.github.SmartCash.spent.domain.dto.SpentDtoInsert;

public class InstallmentsValidation implements SpentValidation{
    @Override
    public void validation(SpentDtoInsert dtoInsert) {
        if (dtoInsert.typeSpent() != Type.CREDIT && dtoInsert.installments() > 1)
            throw new RuntimeException("Voce so pode ter compras parceladas no credito");
    }
}
