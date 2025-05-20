package fioshi.com.github.SmartCash.spent.domain.dto;

import fioshi.com.github.SmartCash.spent.domain.model.SpentCategorie;
import fioshi.com.github.SmartCash.spent.domain.model.Type;

public record SpentDtoUpdate (
        Type typeSpent,
        int  installments,
        double value,
        String place,
        String item,
        SpentCategorie categorie
){
}
