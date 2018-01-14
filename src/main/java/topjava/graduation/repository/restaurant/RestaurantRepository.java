package topjava.graduation.repository.restaurant;

import topjava.graduation.model.Menu;
import topjava.graduation.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

public interface RestaurantRepository {

    Restaurant save(Restaurant restaurant);

    Restaurant get(int id);

    List<Restaurant> getAll();

    Menu getMenu(int menu_id);

    Menu getMenu(int restaurant_id, LocalDate date);

    Menu saveMenu(Menu menu);

    boolean deleteMenu(int id);

    List<Menu> getAllMenu(LocalDate date);
}
