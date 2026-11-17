package fioshi.com.github.SmartCash.spent.domain.model;

import fioshi.com.github.SmartCash.user.domain.model.User;

import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Pure domain model representing monthly consolidated expenses for a user.
 */
public class MonthlyExpense {

    private final Long id;
    private final int year;
    private final Month month;
    private final List<Spent> spents;
    private final User user;

    public MonthlyExpense(Long id, int year, Month month, List<Spent> spents, User user) {
        this.id = id;
        this.year = year;
        this.month = month;
        this.spents = spents != null ? new ArrayList<>(spents) : new ArrayList<>();
        this.user = user;
    }

    public static MonthlyExpense createNew(int year, Month month, User user) {
        return new MonthlyExpense(null, year, month, new ArrayList<>(), user);
    }

    public List<Spent> getSpents() {
        return Collections.unmodifiableList(spents);
    }

    public void addSpent(Spent spent) {
        if (!this.spents.contains(spent)) {
            this.spents.add(spent);
        }
    }

    public void removeSpent(Spent spent) {
        this.spents.remove(spent);
    }

    public double calculateTotal() {
        return spents.stream().mapToDouble(Spent::getValue).sum();
    }

    public Long getId() {
        return id;
    }

    public int getYear() {
        return year;
    }

    public Month getMonth() {
        return month;
    }

    public User getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MonthlyExpense other)) return false;
        return id != null && Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
