package topjava.graduation.repository.restaurant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import topjava.graduation.model.Menu;
import topjava.graduation.model.Restaurant;

import java.time.LocalDate;
import java.util.List;


@Transactional(readOnly = true)
public interface CrudMenuRepository extends JpaRepository<Menu, Integer> {
    @Transactional
    @Override
    Menu save(Menu menu);

    @Override
    Menu findOne(Integer id);

    @Transactional
    @Modifying
    @Query("DELETE FROM Menu m WHERE m.id=:id")
    int delete(@Param("id") int id);

    @Query("SELECT m FROM Menu m LEFT JOIN FETCH m.restaurant r LEFT JOIN FETCH m.dishes WHERE m.restaurant.id = ?1 and m.date = ?2")
    Menu get(int restaurant_id, LocalDate date);


    @Query("SELECT DISTINCT m FROM Menu m LEFT JOIN FETCH m.restaurant r LEFT JOIN FETCH m.dishes WHERE m.date = ?1")
    List<Menu> findAll(LocalDate date);
}
