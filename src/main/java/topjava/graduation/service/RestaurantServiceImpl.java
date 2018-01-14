package topjava.graduation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import topjava.graduation.exception.NotFoundException;
import topjava.graduation.model.Menu;
import topjava.graduation.model.Restaurant;
import topjava.graduation.repository.restaurant.RestaurantRepository;

import java.time.LocalDate;
import java.util.List;

import static topjava.graduation.util.ValidationUtil.checkNotFoundWithId;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    final private RestaurantRepository repository;

    @Autowired
    public RestaurantServiceImpl(RestaurantRepository repository) {
        this.repository = repository;
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    @Override
    public Restaurant save(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return repository.save(restaurant);
    }

    @Override
    public Restaurant get(int id) throws NotFoundException {
        return checkNotFoundWithId(repository.get(id), id);
    }

    @Cacheable("restaurants")
    @Override
    public List<Restaurant> getAll() {
        return repository.getAll();
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    @Override
    public void evictCache() {
        // only for evict cache
    }

    @Override
    public Menu getMenu(int restaurant_id, LocalDate date) {
        return repository.getMenu(restaurant_id, date);
    }

    @Override
    public Menu saveMenu(Menu menu) {
        Assert.notNull(menu, "menu must not be null");
        Assert.isTrue(menu.getDate().isAfter(LocalDate.now()), "menu can not be change");
        return repository.saveMenu(menu);
    }

    @Override
    public void deleteMenu(int menu_id) {
        Menu menu=repository.getMenu(menu_id);
        if (menu==null) {
            throw new NotFoundException(String.format("Menu id=%d not exists",menu_id));
        }
        Assert.isTrue(menu.getDate().isAfter(LocalDate.now()), "menu can not be change");
        checkNotFoundWithId(repository.deleteMenu(menu_id), menu_id);
    }

    @Override
    public List<Menu> getAllMenu(LocalDate date) {
        return repository.getAllMenu(date);
    }
}
