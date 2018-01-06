package topjava.graduation.repository.user;

import topjava.graduation.model.User;
import topjava.graduation.model.Vote;

import java.time.LocalDate;
import java.util.List;

public interface UserRepository {
    User save(User user);

    boolean delete(int id);

    User get(int id);

    User getByEmail(String email);

    Vote getVote(int id);

    Vote setVote(int user_id, int restaurant_id);

    List<User> getAll();

}