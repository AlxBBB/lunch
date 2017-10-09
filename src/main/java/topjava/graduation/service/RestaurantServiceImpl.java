package topjava.graduation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import topjava.graduation.exception.NotFoundException;
import topjava.graduation.model.Restaurant;
import topjava.graduation.repository.restaurant.RestaurantRepository;
import static topjava.graduation.ValidationUtil.*;

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
    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id),id);
    }

    @Override
    public Restaurant get(int id) throws NotFoundException {
        return checkNotFoundWithId(repository.get(id),id);
    }
}
