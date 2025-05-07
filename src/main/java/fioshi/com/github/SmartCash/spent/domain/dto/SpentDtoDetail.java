package fioshi.com.github.SmartCash.spent.domain.dto;

import fioshi.com.github.SmartCash.spent.domain.model.Spent;
import fioshi.com.github.SmartCash.spent.domain.model.Type;

public record SpentDtoDetail(
        Type type,
        int installments,
        double valueInstallment,
        double valueTotal,
        String place,
        String item
) {
    public SpentDtoDetail(Spent spent){
        this(
                spent.getType(),
                spent.getInstallments(),
                spent.getValue(),
                spent.getValue() * spent.getInstallments(),
                spent.getPlace(),
                spent.getItem() );
    }
}
