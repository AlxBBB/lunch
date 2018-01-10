package topjava.graduation.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import topjava.graduation.exception.NotFoundException;
import topjava.graduation.model.core.Role;
import topjava.graduation.model.User;
import java.util.Arrays;

import static topjava.graduation.UserTestData.*;

public class UserServiceTest extends AbstractServiceTest {

    @Autowired
    protected UserService service;

    @Before
    public void setUp() throws Exception {
      service.evictCache();
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

    @Test(expected = DataIntegrityViolationException.class)
    public void testCreateDublEmail() throws Exception {
        User newUser = new User(null, "New", USER1.getEmail(), "newPass", Role.ROLE_USER);
        service.save(newUser);
    }

    @Test
    public void testGetByEmail() throws Exception {
        User user = service.getByEmail("admin@mail.ru");
        MATCHER_USER.assertEquals(ADMIN, user);
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

}