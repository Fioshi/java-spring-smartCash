package fioshi.com.github.SmartCash.spent.adapter.out.persistence.entity;

import fioshi.com.github.SmartCash.user.adapter.out.persistence.entity.UserJpaEntity;
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
public class AdditionalIncomeJpaEntity {

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
    private UserJpaEntity user;

    public AdditionalIncomeJpaEntity(Long id, Double valor, String descricao, int ano, Month mes, UserJpaEntity user) {
        this.id = id;
        this.valor = valor;
        this.descricao = descricao;
        this.ano = ano;
        this.mes = mes;
        this.user = user;
    }
}
