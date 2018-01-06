package topjava.graduation.repository.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import topjava.graduation.model.User;
import topjava.graduation.model.Vote;
import topjava.graduation.repository.restaurant.CrudRestaurantRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private static final Sort SORT_NAME_EMAIL = new Sort("name", "email");

    private final CrudUserRepository crudUserRepository;
    private final CrudVoteRepository crudVoteRepository;
    private final CrudRestaurantRepository crudRestaurantRepository;

    @Autowired
    public UserRepositoryImpl(CrudUserRepository crudUserRepository, CrudVoteRepository crudVoteRepository, CrudRestaurantRepository crudRestaurantRepository) {
        this.crudUserRepository = crudUserRepository;
        this.crudVoteRepository = crudVoteRepository;
        this.crudRestaurantRepository = crudRestaurantRepository;
    }

    @Override
    public User save(User user) {
        return crudUserRepository.save(user);
    }

    @Override
    public boolean delete(int id) {
        return crudUserRepository.delete(id) != 0;
    }

    @Override
    public User get(int id) {
        return crudUserRepository.findOne(id);
    }

    @Override
    public User getByEmail(String email) {
        return crudUserRepository.getByEmail(email);
    }

    @Override
    public List<User> getAll() {
        return crudUserRepository.findAll(SORT_NAME_EMAIL);
    }

    @Override
    public Vote getVote(int id) {
        return crudVoteRepository.get(id, LocalDate.now());
    }

    @Override
    public Vote setVote(int user_id, int restaurant_id) {
        Vote oldVote = crudVoteRepository.get(user_id, LocalDate.now());
        if (oldVote == null) {
            return crudVoteRepository.save(new Vote(crudUserRepository.findOne(user_id), crudRestaurantRepository.findOne(restaurant_id)));
        } else {
            if (LocalTime.now().isBefore(LocalTime.of(11, 00))) {
                oldVote.setRestaurant(crudRestaurantRepository.findOne(restaurant_id));
                return crudVoteRepository.save(oldVote);
            } else {
                return null;
            }
        }
    }
}
