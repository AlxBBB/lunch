package topjava.graduation;

import topjava.graduation.matcher.BeanMatcher;
import topjava.graduation.model.Vote;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


import static topjava.graduation.RestaurantTestData.*;
import static topjava.graduation.UserTestData.USER1;
import static topjava.graduation.model.AbstractBaseEntity.START_SEQ;

public class VoteTestData {
    public static final BeanMatcher<Vote> MATCHER_VOTE = BeanMatcher.of(Vote.class);

    public static final Vote USER1_VOTE1 = new Vote(START_SEQ+10, LocalDate.now().minusDays(1),USER1,RESTAURANT1);
    public static final Vote USER1_VOTE2 = new Vote(START_SEQ+11, LocalDate.now(),USER1,RESTAURANT1);

    public static final List<Vote> USER1_VOTES= Arrays.asList(USER1_VOTE2,USER1_VOTE1);

 /*   public static final BeanMatcher<Vote> MATCHER_VOTE = BeanMatcher.of(Vote.class,
            (expected, actual) -> expected == actual ||
                    (Objects.equals(expected.getId(), actual.getId())
                            && Objects.equals(expected.getDate(), actual.getDate())
                            && Objects.equals(expected.getRestaurant(), actual.getRestaurant())
                    //        && Objects.equals(expected.getUser(), actual.getUser())
                    )
    );*/

}
