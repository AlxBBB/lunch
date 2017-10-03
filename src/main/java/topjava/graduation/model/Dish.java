package topjava.graduation.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;

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
    @JoinColumn(name = "id_menu", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    //@NotNull(groups = View.Persist.class)
    private Menu menu;

}
