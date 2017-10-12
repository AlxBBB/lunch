package topjava.graduation.repository.menu;

import topjava.graduation.model.Menu;

public interface MenuRepository {

    Menu save(Menu menu);

    boolean delete(int id);

    Menu get(int id);



}
