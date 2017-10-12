package topjava.graduation.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "votes", uniqueConstraints = {@UniqueConstraint(columnNames = {"id_user", "date"}, name = "votes_unique_user_date_idx")})
public class Vote extends AbstractBaseEntity {
    @Column(name = "date", nullable = false)
    @NotNull
    LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    //@NotNull(groups = View.Persist.class)
    private User user;

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_restaurant", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    //@NotNull(groups = View.Persist.class)
    private Restaurant restaurant;


    public Vote(Integer id, LocalDate date, Restaurant restaurant) {
        super(id);
        this.date = date;
        this.restaurant=restaurant;
    }

    public Vote(Integer id, LocalDate date, Restaurant restaurant, User user) {
        this(id,date,restaurant);
        this.user=user;
    }

    public Vote() {
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
