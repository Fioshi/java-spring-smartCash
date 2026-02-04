package fioshi.com.github.SmartCash.spent.domain.model;

import fioshi.com.github.SmartCash.user.domain.model.User;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Month;

@Entity
@Table(name = "tb_additional_income")
@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class AdditionalIncome {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double valor;

    private String descricao;

    private int ano;

    @Enumerated(EnumType.STRING)
    private Month mes;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public AdditionalIncome(Double valor, String descricao, int ano, Month mes, User user) {
        this.valor = valor;
        this.descricao = descricao;
        this.ano = ano;
        this.mes = mes;
        this.user = user;
    }
}
