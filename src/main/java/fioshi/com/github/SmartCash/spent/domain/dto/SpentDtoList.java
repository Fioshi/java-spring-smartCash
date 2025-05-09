package fioshi.com.github.SmartCash.spent.domain.dto;

import fioshi.com.github.SmartCash.spent.domain.model.Spent;
import fioshi.com.github.SmartCash.spent.domain.model.Type;

public record SpentDtoList(
        Type type,
        int installments,
        double valueInstallment,
        String item
)
{

    public SpentDtoList(Spent spent){
        this(
                spent.getType(),
                spent.getInstallments(),
                spent.getValue(),
                spent.getItem()
        );
    }
}
