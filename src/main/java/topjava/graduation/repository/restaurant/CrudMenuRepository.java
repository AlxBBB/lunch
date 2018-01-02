package topjava.graduation.repository.restaurant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import topjava.graduation.model.Menu;
import topjava.graduation.model.Restaurant;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;


@Transactional(readOnly = true)
public interface CrudMenuRepository  extends JpaRepository<Menu, Integer> {
    @Transactional
    @Override
    Menu save(Menu menu);

    @Override
    Menu findOne(Integer id);

    Menu getByRestaurantAndDate(Restaurant restaurant, @NotNull LocalDate date);

    List<Menu> findAllByDate(LocalDate date);
}
