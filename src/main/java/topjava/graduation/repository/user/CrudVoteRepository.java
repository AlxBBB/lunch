package topjava.graduation.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import topjava.graduation.model.User;
import topjava.graduation.model.Vote;

import java.time.LocalDate;

@Transactional(readOnly = true)
public interface CrudVoteRepository extends JpaRepository<Vote, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Vote v WHERE v.id=:id")
    int delete(@Param("id") int id);

    @Transactional
    @Override
    Vote save(Vote vote);

    @Override
    Vote findOne(Integer id);

    @Query("SELECT v FROM Vote v WHERE v.user.id=:user_id and v.date=:date")
    Vote get(@Param("user_id") int user_id, @Param("date") LocalDate date);
}
