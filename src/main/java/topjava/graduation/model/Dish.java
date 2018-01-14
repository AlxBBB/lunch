package topjava.graduation.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;
import topjava.graduation.model.core.AbstractNamedEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "dishes")
public class Dish extends AbstractNamedEntity {

    @Column(name = "cost", nullable = false)
    @Range(min = 0, max = 10000000)
    @NotNull
    int cost;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "menu_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    @NotNull
    private Menu menu;


    public Dish() {
    }

    public Dish(String name, @Range(min = 0, max = 10000000) @NotNull int cost) {
        super(null, name);
        this.cost = cost;
    }
}
