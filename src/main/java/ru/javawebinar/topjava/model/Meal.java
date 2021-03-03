package ru.javawebinar.topjava.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@NamedQueries({
        @NamedQuery(name = Meal.DELETE, query = "DELETE FROM Meal m WHERE m.id=:id"),
        @NamedQuery(name = Meal.ALL_SORTED, query = "SELECT m FROM Meal m LEFT JOIN FETCH m.user WHERE m.user.id=?1 ORDER BY m.dateTime"),
//        @NamedQuery(name = Meal.BY_ID, query = "SELECT m FROM Meal m LEFT JOIN FETCH m.user.id WHERE m.id=:id"),
//        @NamedQuery(name = Meal.BY_BETWEEN_HALF_OPEN, query = "SELECT m FROM Meal m WHERE m.user.id=:userId AND m.dateTime >=: dateTime AND m.dateTime <: dateTime " +
//                "ORDER BY m.dateTime asc"),
})
@Entity
@Table(name = "meals", uniqueConstraints = {@UniqueConstraint(columnNames = "user_id", name = "meals_unique_user_datetime_idx")})
public class Meal extends AbstractBaseEntity {

    public static final String DELETE = "Meal.delete";
    public static final String ALL_SORTED = "Meal.getAllSorted";
    public static final String BY_ID = "Meal.getId";
    public static final String BY_BETWEEN_HALF_OPEN = "Meal.getBetweenHalfOpen";

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(name = "date_time", nullable = false, columnDefinition = "timestamp default now()")
    @NotNull
    private LocalDateTime dateTime;

    @Column(name = "description", nullable = false, columnDefinition = "")
    @NotNull
    private String description;

    @Column(name = "calories", nullable = false, columnDefinition = "")
    @NotNull
    private Integer calories;

    public Meal() {
    }

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories);
    }

    public Meal(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
//        this.userId = userId;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
