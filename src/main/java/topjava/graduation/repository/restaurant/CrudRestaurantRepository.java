package topjava.graduation.repository.restaurant;


import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import topjava.graduation.model.Restaurant;


import java.util.List;


@Transactional(readOnly = true)
public interface CrudRestaurantRepository  extends JpaRepository<Restaurant, Integer> {

    @Transactional
    @Override
    Restaurant save(Restaurant restaurant);

    @Override
    Restaurant findOne(Integer id);

    @Override
    List<Restaurant> findAll(Sort sort);

}
