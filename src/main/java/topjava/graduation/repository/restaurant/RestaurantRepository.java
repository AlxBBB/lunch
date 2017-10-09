package topjava.graduation.repository.restaurant;

import topjava.graduation.model.Restaurant;

public interface RestaurantRepository {
    Restaurant save(Restaurant restaurant);

    boolean delete(int id);

    Restaurant get(int id);
}
