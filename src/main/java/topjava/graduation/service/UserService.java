package topjava.graduation.service;




import topjava.graduation.exception.NotFoundException;
import topjava.graduation.model.User;
import topjava.graduation.model.Vote;

import java.util.List;

public interface UserService {

    User save(User user);

    User get(int id) throws NotFoundException;

    User getByEmail(String email) throws NotFoundException;

    Vote getVote(int id) throws NotFoundException;

    Vote setVote(int user_id, int restaurant_id);

    void evictCache();

    List<User> getAll();

}