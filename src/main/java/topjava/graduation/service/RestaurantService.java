package topjava.graduation.service;

import topjava.graduation.exception.NotFoundException;
import topjava.graduation.model.Restaurant;

public interface RestaurantService {
    Restaurant save(Restaurant restaurant);

    void delete(int id) throws NotFoundException;

    Restaurant get(int id) throws NotFoundException;
}
