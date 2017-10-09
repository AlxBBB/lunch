package topjava.graduation.repository.restaurant;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import topjava.graduation.model.Restaurant;


@Transactional(readOnly = true)
public interface CrudRestaurantRepository  extends JpaRepository<Restaurant, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Restaurant r WHERE r.id=:id")
    int delete(@Param("id") int id);

    @Transactional
    @Override
    Restaurant save(Restaurant restaurant);

    @Override
    Restaurant findOne(Integer id);

}
