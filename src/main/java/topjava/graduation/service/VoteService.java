package topjava.graduation.service;

import topjava.graduation.exception.NotFoundException;
import topjava.graduation.model.Vote;

public interface VoteService {

    Vote save(Vote vote);

    void delete(int id) throws NotFoundException;

    Vote get(int id) throws NotFoundException;
}
