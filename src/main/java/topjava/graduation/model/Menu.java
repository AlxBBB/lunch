package topjava.graduation.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "menus", uniqueConstraints = {@UniqueConstraint(columnNames = {"id_restaurant", "date"}, name = "menus_unique_restaurant_date_idx")})
public class Menu extends AbstractBaseEntity {

    @Column(name = "date", nullable = false)
    @NotNull
    private LocalDate date;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "menu")
    @OrderBy("cost DESC")
    protected List<Dish> dishes;


  //  @OneToMany(fetch = FetchType.LAZY, mappedBy = "menu")
  //  @OrderBy("date DESC")
  // protected List<Vote> votes;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_restaurant", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    //@NotNull(groups = View.Persist.class)
    private Restaurant restaurant;


    public Menu(Integer id, @NotNull LocalDate date, Restaurant restaurant) {
        super(id);
        this.date = date;
        this.restaurant = restaurant;
    }

    public Menu() {
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }


}
