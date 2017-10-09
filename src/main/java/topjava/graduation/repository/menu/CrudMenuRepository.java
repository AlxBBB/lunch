package topjava.graduation.repository.menu;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import topjava.graduation.model.Menu;



@Transactional(readOnly = true)
public interface CrudMenuRepository  extends JpaRepository<Menu, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Menu m WHERE m.id=:id")
    int delete(@Param("id") int id);

    @Transactional
    @Override
    Menu save(Menu menu);

    @Override
    Menu findOne(Integer id);


}
