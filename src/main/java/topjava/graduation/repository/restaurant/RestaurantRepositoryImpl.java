package topjava.graduation.repository.restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import topjava.graduation.model.Menu;
import topjava.graduation.model.Restaurant;
import topjava.graduation.model.core.AbstractNamedEntity;


import java.time.LocalDate;
import java.util.List;


@Repository
public class RestaurantRepositoryImpl implements RestaurantRepository {
    private final CrudRestaurantRepository crudRestaurantRepository;
    private final CrudMenuRepository crudMenuRepository;

    @Autowired
    public RestaurantRepositoryImpl(CrudRestaurantRepository crudRestaurantRepository, CrudMenuRepository menuRepository) {
        this.crudRestaurantRepository = crudRestaurantRepository;
        this.crudMenuRepository=menuRepository;
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        return crudRestaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant get(int id) {
        return crudRestaurantRepository.findOne(id);
    }

    @Override
    public List<Restaurant> getAll() {
        return crudRestaurantRepository.findAll(AbstractNamedEntity.SORT_NAME);
    }

    @Override
    public Menu getMenu(Restaurant restaurant, LocalDate date) {
        return crudMenuRepository.getByRestaurantAndDate(restaurant, date);
    }

    @Override
    public Menu saveMenu(Menu menu) {
        return crudMenuRepository.save(menu);
    }

    @Override
    public List<Menu> getAllMenu(LocalDate date) {
        return crudMenuRepository.findAllByDate(date);
    }
}
