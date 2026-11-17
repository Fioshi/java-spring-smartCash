package fioshi.com.github.SmartCash.spent.domain.model;

import fioshi.com.github.SmartCash.user.domain.model.User;

import java.util.Objects;

/**
 * Pure domain model representing an expense (spent).
 */
public class Spent {

    private final Long id;
    private Type type;
    private int installments;
    private double value;
    private String place;
    private SpentCategorie categorie;
    private String item;
    private boolean isMonthly;
    private final User user;

    public Spent(
            Long id,
            Type type,
            int installments,
            double value,
            String place,
            SpentCategorie categorie,
            String item,
            boolean isMonthly,
            User user
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

    public static Spent createNew(
            Type type,
            int installments,
            double value,
            String place,
            SpentCategorie categorie,
            String item,
            User user
    ) {
        var spent = new Spent(
                null,
                type,
                0,
                0.0,
                place,
                categorie,
                item,
                categorie.isRecurring(),
                user
        );
        spent.calculateInstallmentsAndValue(categorie, installments, value);
        return spent;
    }

    public void update(
            Type typeSpent,
            int installments,
            double value,
            String place,
            String item,
            SpentCategorie categorie
    ) {
        if (categorie != null) {
            this.categorie = categorie;
            this.isMonthly = categorie.isRecurring();
        }
        if (place != null) {
            this.place = place;
        }
        if (item != null) {
            this.item = item;
        }
        if (typeSpent != null) {
            this.type = typeSpent;
        }
        SpentCategorie effectiveCategorie = categorie != null ? categorie : this.categorie;
        calculateInstallmentsAndValue(effectiveCategorie, installments, value);
    }

    private void calculateInstallmentsAndValue(SpentCategorie categorie, int installments, double value) {
        if (categorie != null && !categorie.isRecurring()) {
            this.installments = installments;
            this.value = installments > 0 ? value / installments : value;
        } else {
            this.value = value;
        }
    }

    public Long getId() {
        return id;
    }

    public Type getType() {
        return type;
    }

    public int getInstallments() {
        return installments;
    }

    public double getValue() {
        return value;
    }

    public String getPlace() {
        return place;
    }

    public SpentCategorie getCategorie() {
        return categorie;
    }

    public String getItem() {
        return item;
    }

    public boolean isMonthly() {
        return isMonthly;
    }

    public User getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Spent other)) return false;
        return id != null && Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
