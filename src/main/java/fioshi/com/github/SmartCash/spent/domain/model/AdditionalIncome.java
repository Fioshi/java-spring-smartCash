package fioshi.com.github.SmartCash.spent.domain.model;

import fioshi.com.github.SmartCash.user.domain.model.User;

import java.time.Month;
import java.util.Objects;

/**
 * Pure domain model representing an additional monthly income.
 */
public class AdditionalIncome {

    private final Long id;
    private final Double valor;
    private final String descricao;
    private final int ano;
    private final Month mes;
    private final User user;

    public AdditionalIncome(Long id, Double valor, String descricao, int ano, Month mes, User user) {
        this.id = id;
        this.valor = valor;
        this.descricao = descricao;
        this.ano = ano;
        this.mes = mes;
        this.user = user;
    }

    public static AdditionalIncome createNew(Double valor, String descricao, int ano, Month mes, User user) {
        return new AdditionalIncome(null, valor, descricao, ano, mes, user);
    }

    public Long getId() {
        return id;
    }

    public Double getValor() {
        return valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getAno() {
        return ano;
    }

    public Month getMes() {
        return mes;
    }

    public User getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AdditionalIncome other)) return false;
        return id != null && Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
