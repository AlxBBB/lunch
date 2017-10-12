package topjava.graduation.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import topjava.graduation.exception.NotFoundException;
import topjava.graduation.model.Role;
import topjava.graduation.model.User;
import java.util.Arrays;

import static topjava.graduation.UserTestData.*;
import static topjava.graduation.VoteTestData.MATCHER_VOTE;
import static topjava.graduation.VoteTestData.USER1_VOTES;

//import static org.hsqldb.Tokens.ADMIN;

public class UserServiceTest extends AbstractServiceTest {

    @Autowired
    protected UserService service;

    @Before
    public void setUp() throws Exception {
        //    service.evictCache();
    }


    @Test
    public void testGet() throws Exception {
        User user = service.get(ADMIN_ID);
        MATCHER_USER.assertEquals(ADMIN, user);
    }


    @Test
    public void testCreate() throws Exception {
        User newUser = new User(null, "New", "new@gmail.com", "newPass", Role.ROLE_USER);
        User created = service.save(newUser);
        newUser.setId(created.getId());
        MATCHER_USER.assertListEquals(Arrays.asList(newUser, ADMIN, USER1, USER2), service.getAll());
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(USER1_ID);
        MATCHER_USER.assertListEquals(Arrays.asList(ADMIN, USER2), service.getAll());
    }

    @Test
    public void testGetByEmail() throws Exception {
        User user = service.getByEmail("admin@mail.ru");
        MATCHER_USER.assertEquals(ADMIN, user);
    }


    @Test(expected = NotFoundException.class)
    public void testNotFoundDelete() throws Exception {
        service.delete(1);
    }

    @Test(expected = NotFoundException.class)
    public void testGetNotFound() throws Exception {
        service.get(1);
    }


    @Test
    public void testUpdate() throws Exception {
        User updated = new User(USER1);
        updated.setName("UpdatedName");
        updated.setEmail("updated@mail.ru");
        service.save(updated);
        MATCHER_USER.assertEquals(updated, service.get(USER1_ID));
    }



    /*
    @Test(expected = DataAccessException.class)
    public void testDuplicateMailCreate() throws Exception {
        service.create(new User(null, "Duplicate", "user@yandex.ru", "newPass", 2000, Role.ROLE_USER));
    }




    @Test
    public void testGetAll() throws Exception {
        List<User> all = service.getAll();
        MATCHER.assertListEquals(Arrays.asList(ADMIN, USER), all);
    }


     */
}