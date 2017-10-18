package topjava.graduation.repository.vote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import topjava.graduation.model.Vote;
import topjava.graduation.repository.restaurant.CrudRestaurantRepository;
import topjava.graduation.repository.user.CrudUserRepository;

import java.time.LocalDate;
import java.time.LocalTime;

@Repository
public class VoteRepositoryImpl implements VoteRepository {

    private final CrudVoteRepository crudRepository;

    private final CrudRestaurantRepository crudRestaurantRepository;

    private final CrudUserRepository crudUserRepository;

    @Autowired
    public VoteRepositoryImpl(CrudVoteRepository crudRepository, CrudRestaurantRepository crudRestaurantRepository, CrudUserRepository crudUserRepository) {
        this.crudRepository = crudRepository;
        this.crudRestaurantRepository = crudRestaurantRepository;
        this.crudUserRepository = crudUserRepository;
    }

    @Override
    public Vote save(Vote vote) {
        return crudRepository.save(vote);
    }

    @Override
    public boolean delete(int id) {
        return crudRepository.delete(id) != 0;
    }

    @Override
    public Vote get(int id) {
        return crudRepository.findOne(id);
    }

    @Override
    public Vote get(int user_id, LocalDate date) {
        return crudRepository.get(user_id, date);
    }

    @Override
    @Transactional
    public Vote set(int user_id, int restaurant_id) {
        Vote oldVote = crudRepository.get(user_id, LocalDate.now());
        if (oldVote == null) {
            return crudRepository.save(new Vote(crudUserRepository.findOne(user_id), crudRestaurantRepository.findOne(restaurant_id)));
        } else {
            if (LocalTime.now().isBefore(LocalTime.of(11, 00))) {
                oldVote.setRestaurant(crudRestaurantRepository.findOne(restaurant_id));
                return crudRepository.save(oldVote);
            } else {
                return null;
            }
        }
    }
}
