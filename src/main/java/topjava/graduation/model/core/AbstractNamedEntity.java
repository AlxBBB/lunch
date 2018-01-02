package topjava.graduation.model.core;

//import org.hibernate.validator.constraints.SafeHtml;
//import ru.javawebinar.topjava.View;

import org.springframework.data.domain.Sort;
import topjava.graduation.model.core.AbstractBaseEntity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;


@MappedSuperclass
public abstract class AbstractNamedEntity extends AbstractBaseEntity {
    public static final Sort SORT_NAME = new Sort("name");

    @NotBlank
    @Column(name = "name", nullable = false)
 //   @SafeHtml(groups = {View.ValidatedRestUI.class})
    protected String name;

    public AbstractNamedEntity() {
    }

    protected AbstractNamedEntity(Integer id, String name) {
        super(id);
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return String.format("Entity %s (%s, '%s')", getClass().getName(), getId(), name);
    }
}