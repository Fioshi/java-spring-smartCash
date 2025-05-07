package fioshi.com.github.SmartCash.spent.domain.model;

import fioshi.com.github.SmartCash.user.domain.model.User;
import fioshi.com.github.SmartCash.spent.domain.dto.SpentDtoInsert;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_spent")
@EqualsAndHashCode(of = "id")
@Getter
@NoArgsConstructor
public class Spent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Type type;

    private int installments;

    private double value;

    private String place;

    private String item;

    private boolean isMonthly;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Spent(SpentDtoInsert dtoInsert, User user) {
        this.type = dtoInsert.typeSpent();
        this.installments = dtoInsert.installments();
        this.value = dtoInsert.value() / dtoInsert.installments();
        this.place = dtoInsert.place();
        this.user = user;
        this.item = dtoInsert.item();
        this.isMonthly = dtoInsert.isMonthly();
    }
}
