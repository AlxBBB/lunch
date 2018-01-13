package topjava.graduation.model;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cache;
import topjava.graduation.model.core.AbstractNamedEntity;
import javax.persistence.*;


@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)

@Entity
@Table(name = "restaurants", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"}, name = "restaurants_unique_name_idx")})
public class Restaurant extends AbstractNamedEntity {

    public Restaurant(Integer id, String name) {
        super(id, name);
    }

    public Restaurant() {
    }
}
