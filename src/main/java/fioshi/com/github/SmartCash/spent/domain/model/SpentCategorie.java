package fioshi.com.github.SmartCash.spent.domain.model;

import lombok.Getter;

@Getter
public enum SpentCategorie {
    MORADIA("Moradia", true),
    ALIMENTACAO("Alimentação", false),
    TRANSPORTE("Transporte", false),
    CONTAS_FIXAS("Contas Fixas", true),
    ASSINATURAS("Assinaturas", true),
    SAUDE("Saúde", false),
    EDUCACAO("Educação", false),
    COMPRAS("Compras", false),
    DIVIDAS("Dívidas e Financiamentos", true),
    IMPREVISTOS("Imprevistos", false),
    LAZER_E_VIAGENS("Lazer e Viagens", false),
    TRABALHO("Trabalho / Profissional", false),
    INVESTIMENTOS("Investimentos", false);

    private final String descricao;
    private final boolean isRecurring;

    SpentCategorie(String descricao, boolean isRecurring) {
        this.descricao = descricao;
        this.isRecurring = isRecurring;
    }

}
