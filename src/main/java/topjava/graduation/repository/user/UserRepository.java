package topjava.graduation.repository.user;

import topjava.graduation.model.User;

import java.util.List;

public interface UserRepository {
    User save(User user);

    boolean delete(int id);

    User get(int id);

    User getWithVotes(int id);

    User getByEmail(String email);

    List<User> getAll();
}