package fioshi.com.github.SmartCash.spent.domain.dto;

import fioshi.com.github.SmartCash.spent.domain.model.SpentCategorie;

public record SpentCategorieDtoList(
        String name,
        String description,
        boolean isRecorring
) {
    public SpentCategorieDtoList(SpentCategorie categorie){
        this(categorie.name(), categorie.getDescricao(), categorie.isRecurring());
    }
}
