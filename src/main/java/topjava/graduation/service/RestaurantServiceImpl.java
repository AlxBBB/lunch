package topjava.graduation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import topjava.graduation.exception.NotFoundException;
import topjava.graduation.model.Menu;
import topjava.graduation.model.Restaurant;
import topjava.graduation.repository.restaurant.RestaurantRepository;

import java.time.LocalDate;
import java.util.List;

import static topjava.graduation.util.ValidationUtil.*;

@Service
public class RestaurantServiceImpl  implements RestaurantService {

    final private RestaurantRepository repository;

    @Autowired
    public RestaurantServiceImpl(RestaurantRepository repository) {
        this.repository = repository;
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return repository.save(restaurant);
    }

    @Override
    public Restaurant get(int id) throws NotFoundException {
        return checkNotFoundWithId(repository.get(id),id);
    }

    @Override
    public List<Restaurant> getAll() {
        return repository.getAll();
    }

    @Override
    public Menu getMenu(int restaurant_id,LocalDate date) {
        return repository.getMenu(get(restaurant_id),date);
    }

    @Override
    public Menu saveMenu(Menu menu) {
        Assert.notNull(menu, "menu must not be null");
        Assert.isTrue(!menu.getDate().isBefore(LocalDate.now()),"menu can not be change");
        Assert.isNull(menu.getRestaurant(),"restaurant must not be null");
        return repository.saveMenu(menu);
    }

    @Override
    public List<Menu> getAllMenu(LocalDate date) {
        return repository.getAllMenu(date);
    }
}
