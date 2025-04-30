package fioshi.com.github.SmartCash.domain;

import fioshi.com.github.SmartCash.domain.dto.SpentDtoInsert;
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

    private Type type;

    private int installments;

    private double value;

    private String place;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Spent(SpentDtoInsert dtoInsert) {
        this.type = dtoInsert.typeSpent();
        this.installments = dtoInsert.installments();
        this.value = dtoInsert.value();
        this.place = dtoInsert.place();
    }
}
