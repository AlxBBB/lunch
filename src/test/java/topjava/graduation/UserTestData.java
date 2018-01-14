package topjava.graduation;


import topjava.graduation.matcher.BeanMatcher;
import topjava.graduation.model.Vote;
import topjava.graduation.model.core.Role;
import topjava.graduation.model.User;
import topjava.graduation.web.json.JsonUtil;

import java.time.LocalDate;
import java.util.Objects;

import static topjava.graduation.RestaurantTestData.RESTAURANT1;
import static topjava.graduation.model.core.AbstractBaseEntity.START_SEQ;


public class UserTestData {
    public static final int USER1_ID = START_SEQ;
    public static final int USER2_ID = START_SEQ+1;
    public static final int ADMIN_ID = START_SEQ + 2;

    public static final User USER1 = new User(USER1_ID, "Посетитель", "user@mail.ru", "uPassword",  Role.ROLE_USER);
    public static final User USER2 = new User(USER2_ID, "Ценитель", "expert@mail.ru", "ePassword",  Role.ROLE_USER);
    public static final User ADMIN = new User(ADMIN_ID, "Админ", "admin@mail.ru", "aPassword",  Role.ROLE_ADMIN);


    public static final BeanMatcher<User> MATCHER_USER = BeanMatcher.of(User.class,
            (expected, actual) -> expected == actual ||
                    (Objects.equals(expected.getId(), actual.getId())
                            && Objects.equals(expected.getName(), actual.getName())
                            && Objects.equals(expected.getEmail(), actual.getEmail())
                            && Objects.equals(expected.getRole(), actual.getRole())
                    )
    );

    public static String jsonWithPassword(User user, String passw) {
        return JsonUtil.writeWithExtraProps(user, "password", passw);
    }

    public static final BeanMatcher<Vote> MATCHER_VOTE = BeanMatcher.of(Vote.class);

    public static final Vote USER1_VOTE1 = new Vote(START_SEQ+10, LocalDate.now().minusDays(1),USER1,RESTAURANT1);
    public static final Vote USER1_VOTE2 = new Vote(START_SEQ+11, LocalDate.now(),USER1,RESTAURANT1);
}