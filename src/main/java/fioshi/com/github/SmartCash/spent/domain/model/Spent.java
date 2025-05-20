package fioshi.com.github.SmartCash.spent.domain.model;

import fioshi.com.github.SmartCash.spent.domain.dto.SpentDtoUpdate;
import fioshi.com.github.SmartCash.user.domain.model.User;
import fioshi.com.github.SmartCash.spent.domain.dto.SpentDtoInsert;
import jakarta.annotation.Nullable;
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

    @Enumerated(EnumType.STRING)
    private SpentCategorie categorie;

    private String item;

    private boolean isMonthly;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Spent(SpentDtoInsert dtoInsert, User user) {
        this.type = dtoInsert.typeSpent();
        this.categorie = dtoInsert.categorie();
        this.place = dtoInsert.place();
        this.user = user;
        this.item = dtoInsert.item();
        this.isMonthly = dtoInsert.categorie().isRecurring();
        checkIsCurrent(dtoInsert.categorie(), dtoInsert.installments(), dtoInsert.value());
    }

    public void update(SpentDtoUpdate dtoUpdate) {
        if (dtoUpdate.categorie() != null)
            this.categorie = dtoUpdate.categorie();
        if (dtoUpdate.place() != null)
            this.place = dtoUpdate.place();
        if (dtoUpdate.item() != null)
            this.item = dtoUpdate.item();
        if (dtoUpdate.typeSpent() != null)
            this.type = dtoUpdate.typeSpent();
        checkIsCurrent(dtoUpdate.categorie(), dtoUpdate.installments(), dtoUpdate.value());
    }

    private void checkIsCurrent(SpentCategorie categorie, int installments, double value){
        if (!categorie.isRecurring()) {
            this.installments = installments;
            this.value = value / installments;
        } else {
            this.value = value;
        }
    }
}
