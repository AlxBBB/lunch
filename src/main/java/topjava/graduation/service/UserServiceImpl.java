package topjava.graduation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import topjava.graduation.exception.NotFoundException;
import topjava.graduation.model.User;
import topjava.graduation.model.Vote;
import topjava.graduation.repository.user.UserRepository;

import java.util.List;

import static topjava.graduation.ValidationUtil.checkNotFound;
import static topjava.graduation.ValidationUtil.checkNotFoundWithId;


@Service("userService")
public class UserServiceImpl implements UserService  {  //, UserDetailsService

    private final UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @CacheEvict(value = "users", allEntries = true)
    @Override
    public User save(User user) {
        Assert.notNull(user, "user must not be null");
        //return repository.save(prepareToSave(user));
        return repository.save(user);
    }

    @CacheEvict(value = "users", allEntries = true)
    @Override
    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id), id);
    }

    @Override
    public User get(int id) throws NotFoundException {
        return checkNotFoundWithId(repository.get(id), id);
    }

    @Override
    public Vote getVote(int id) {
        return checkNotFoundWithId(repository.getVote(id),id);
    }

    @Override
    public User getByEmail(String email) throws NotFoundException {
        Assert.notNull(email, "email must not be null");
        return checkNotFound(repository.getByEmail(email), "email=" + email);
    }

    //@Cacheable("users")
    @Override
    public List<User> getAll() {
        return repository.getAll();
    }


    /*@CacheEvict(value = "users", allEntries = true)
    @Transactional
    @Override
    public void update(UserTo userTo) {
        User user = updateFromTo(get(userTo.getId()), userTo);
        repository.save(prepareToSave(user));
    }*/

   /* @CacheEvict(value = "users", allEntries = true)
    @Override
    public void evictCache() {
        // only for evict cache
    }*/



  /*  @Override
    public AuthorizedUser loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.getByEmail(email.toLowerCase());
        if (user == null) {
            throw new UsernameNotFoundException("User " + email + " is not found");
        }
        return new AuthorizedUser(user);
    } */


}