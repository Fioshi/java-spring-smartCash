package fioshi.com.github.SmartCash.spent.adapter.out.persistence.entity;

import fioshi.com.github.SmartCash.user.adapter.out.persistence.entity.UserJpaEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_monthly_expense")
@Getter
@NoArgsConstructor
public class MonthlyExpenseJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "expense_year")
    private int year;

    @Enumerated(EnumType.STRING)
    @Column(name = "month_name")
    private Month month;

    @ManyToMany
    @JoinTable(
            name = "tb_monthly_expense_spents",
            joinColumns = @JoinColumn(name = "monthly_expense_id"),
            inverseJoinColumns = @JoinColumn(name = "spent_id")
    )
    @Getter(AccessLevel.NONE)
    private List<SpentJpaEntity> spentJpaEntities = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserJpaEntity user;

    public MonthlyExpenseJpaEntity(
            Long id,
            int year,
            Month month,
            List<SpentJpaEntity> spentJpaEntities,
            UserJpaEntity user
    ) {
        this.id = id;
        this.year = year;
        this.month = month;
        this.spentJpaEntities = spentJpaEntities != null ? new ArrayList<>(spentJpaEntities) : new ArrayList<>();
        this.user = user;
    }

    public List<SpentJpaEntity> getSpentJpaEntities() {
        return spentJpaEntities == null ? List.of() : spentJpaEntities.stream().toList();
    }
}
