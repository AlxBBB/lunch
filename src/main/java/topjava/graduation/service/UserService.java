package topjava.graduation.service;




import topjava.graduation.exception.NotFoundException;
import topjava.graduation.model.User;

import java.util.List;

public interface UserService {

    User save(User user);

    void delete(int id) throws NotFoundException;

    User get(int id) throws NotFoundException;

    User getWithVotes(int id) throws NotFoundException;

    User getByEmail(String email) throws NotFoundException;



    //void evictCache();

    //void update(UserTo user);

    List<User> getAll();

}