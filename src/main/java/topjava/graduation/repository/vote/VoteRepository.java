package topjava.graduation.repository.vote;

import topjava.graduation.model.Vote;

import java.time.LocalDate;
import java.util.List;

public interface VoteRepository {
    Vote save(Vote vote);

    boolean delete(int id);

    Vote get(int id);

    Vote get(int user_id, LocalDate date);

    Vote set(int user_id, int restaurant_id);
}
