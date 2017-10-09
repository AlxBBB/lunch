package topjava.graduation.repository.restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import topjava.graduation.model.Restaurant;

@Repository
public class RestaurantRepositoryImpl implements RestaurantRepository {
    private final CrudRestaurantRepository crudRestaurantRepository;

    @Autowired
    public RestaurantRepositoryImpl(CrudRestaurantRepository crudRestaurantRepository) {
        this.crudRestaurantRepository = crudRestaurantRepository;
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        return crudRestaurantRepository.save(restaurant);
    }

    @Override
    public boolean delete(int id) {
        return crudRestaurantRepository.delete(id)!=0;
    }

    @Override
    public Restaurant get(int id) {
        return crudRestaurantRepository.findOne(id);
    }
}
