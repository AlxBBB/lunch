package topjava.graduation.model;

import topjava.graduation.model.core.AbstractNamedEntity;

import javax.persistence.*;

@Entity
@Table(name = "restaurants", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"}, name = "restaurants_unique_name_idx")})
public class Restaurant extends AbstractNamedEntity {

  //  @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
  //  @OrderBy("date DESC")
  //  protected List<Menu> menus;


    public Restaurant(Integer id, String name) {
        super(id, name);
    }

    public Restaurant() {
    }
}
