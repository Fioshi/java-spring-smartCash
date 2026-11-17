package fioshi.com.github.SmartCash.spent.adapter.out.persistence.entity;

import fioshi.com.github.SmartCash.spent.domain.model.SpentCategorie;
import fioshi.com.github.SmartCash.spent.domain.model.Type;
import fioshi.com.github.SmartCash.user.adapter.out.persistence.entity.UserJpaEntity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_spent")
@EqualsAndHashCode(of = "id")
@Getter
@NoArgsConstructor
public class SpentJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Type type;

    private int installments;

    @Column(name = "spent_value")
    private double value;

    private String place;

    @Enumerated(EnumType.STRING)
    private SpentCategorie categorie;

    private String item;

    private boolean isMonthly;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserJpaEntity user;

    public SpentJpaEntity(
            Long id,
            Type type,
            int installments,
            double value,
            String place,
            SpentCategorie categorie,
            String item,
            boolean isMonthly,
            UserJpaEntity user
    ) {
        this.id = id;
        this.type = type;
        this.installments = installments;
        this.value = value;
        this.place = place;
        this.categorie = categorie;
        this.item = item;
        this.isMonthly = isMonthly;
        this.user = user;
    }
}
