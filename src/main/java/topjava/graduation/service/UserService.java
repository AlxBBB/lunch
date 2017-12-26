package topjava.graduation.service;




import topjava.graduation.exception.NotFoundException;
import topjava.graduation.model.User;
import topjava.graduation.model.Vote;
import topjava.graduation.repository.to.UserTo;

import java.util.List;

public interface UserService {

    User save(User user);

    void delete(int id) throws NotFoundException;

    User get(int id) throws NotFoundException;

    User getByEmail(String email) throws NotFoundException;

    Vote getVote(int id) throws NotFoundException;

    Vote setVote(int user_id, int restaurant_id);

    void evictCache();

    List<User> getAll();

}