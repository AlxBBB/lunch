package topjava.graduation.repository.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import topjava.graduation.model.User;
import topjava.graduation.model.Vote;
import topjava.graduation.repository.vote.VoteRepository;


import java.time.LocalDate;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private static final Sort SORT_NAME_EMAIL = new Sort("name", "email");

    private final CrudUserRepository crudRepository;

    private final VoteRepository voteRepository;

    @Autowired
    public UserRepositoryImpl(CrudUserRepository crudRepository, VoteRepository voteRepository) {
        this.crudRepository = crudRepository;
        this.voteRepository = voteRepository;
    }

    @Override
    public User save(User user) {
        return crudRepository.save(user);
    }

    @Override
    public boolean delete(int id) {
        return crudRepository.delete(id) != 0;
    }

    @Override
    public User get(int id) {
        return crudRepository.findOne(id);
    }

    @Override
    public User getByEmail(String email) {
        return crudRepository.getByEmail(email);
    }

    @Override
    public List<User> getAll() {
        return crudRepository.findAll(SORT_NAME_EMAIL);
    }

    @Override
    public Vote getVote(int id) {
      return voteRepository.getVote(id, LocalDate.now());
    }
}
