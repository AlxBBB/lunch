package topjava.graduation.service;

import topjava.graduation.exception.NotFoundException;
import topjava.graduation.model.Menu;
import topjava.graduation.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

public interface RestaurantService {
    Restaurant save(Restaurant restaurant);

    Restaurant get(int id) throws NotFoundException;

    List<Restaurant> getAll();

    Menu getMenu(int restaurant_id, LocalDate date);

    Menu saveMenu(Menu menu);

    List<Menu> getAllMenu(LocalDate date);
}
