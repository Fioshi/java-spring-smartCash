package fioshi.com.github.SmartCash.spent.domain.model;

import fioshi.com.github.SmartCash.user.domain.model.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Month;
import java.util.List;

@Entity
@Table(name = "tb_monthly_expense")
@Getter
@NoArgsConstructor
public class MonthlyExpense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int year;

    @Enumerated(EnumType.STRING)
    private Month month;

    @ManyToMany
    @JoinTable(
            name = "tb_monthly_expense_spents",
            joinColumns = @JoinColumn(name = "monthly_expense_id"),
            inverseJoinColumns = @JoinColumn(name = "spent_id")
    )
    @Getter(AccessLevel.NONE)
    private List<Spent> spents;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public List<Spent> getSpents() {
        return spents.stream().toList();
    }

    public void addSpent(Spent spent){
        this.spents.add(spent);
    }

    public MonthlyExpense(int year, Month month, List<Spent> spents, User user) {
        this.year = year;
        this.month = month;
        this.spents = spents;
        this.user = user;
    }


}