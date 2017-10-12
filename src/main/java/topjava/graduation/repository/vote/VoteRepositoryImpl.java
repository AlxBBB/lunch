package topjava.graduation.repository.vote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import topjava.graduation.model.Vote;

import java.time.LocalDate;

@Repository
public class VoteRepositoryImpl implements VoteRepository {
    private final CrudVoteRepository crudRepository;

    @Autowired
    public VoteRepositoryImpl(CrudVoteRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    @Override
    public Vote save(Vote vote) {
        return crudRepository.save(vote);
    }

    @Override
    public boolean delete(int id) {
        return crudRepository.delete(id)!=0;
    }

    @Override
    public Vote get(int id) {
        return crudRepository.findOne(id);
    }

    @Override
    public Vote getVote(int user_id, LocalDate date) {
        return crudRepository.getVote(user_id,date);
    }
}
