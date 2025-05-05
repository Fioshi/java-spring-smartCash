package fioshi.com.github.SmartCash.spent.domain.dto;

import fioshi.com.github.SmartCash.spent.domain.model.Spent;
import fioshi.com.github.SmartCash.spent.domain.model.Type;

public record SpentDtoList(
        Long id,
        Type type,
        int installments,
        double value,
        String place,
        Long user
)
{
    public static int installmente = 1;

    public SpentDtoList(Spent spent){
        this(
                spent.getId(),
                spent.getType(),
                spent.getInstallments(),
                spent.getValue(),
                spent.getPlace(),
                spent.getUser().getId()
        );
    }
}
