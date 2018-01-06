package topjava.graduation.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.NestedServletException;
import topjava.graduation.model.User;
import topjava.graduation.model.Vote;
import topjava.graduation.model.core.Role;
import topjava.graduation.service.UserService;
import topjava.graduation.web.json.JsonUtil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static topjava.graduation.RestaurantTestData.*;
import static topjava.graduation.TestUtil.userAuth;
import static topjava.graduation.UserTestData.*;
import static topjava.graduation.VoteTestData.*;
import static topjava.graduation.web.controller.UserController.REST_URL;

public class UserControllerTest extends AbstractControllerTest {

    @Autowired
    UserService userService;

    @Before
    public void setUp() {
        userService.evictCache();
        jpaUtil.clear2ndLevelHibernateCache();
    }

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL)
                .with(userAuth(USER1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER_USER.contentMatcher(USER1));
    }

    @Test
    public void testGetNotAuth() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }


    @Test
    public void testRegister() throws Exception {
        User newUser = new User(null, "New", "new@gmail.com", "newPass", Role.ROLE_USER);
        mockMvc.perform(post(REST_URL + "/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonWithPassword(newUser,newUser.getPassword())))
                .andExpect(status().isOk())
                .andDo(print());
        mockMvc.perform(get(REST_URL + "/")
                .with(userAuth(newUser)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test(expected = NestedServletException.class)
    @Transactional(propagation = Propagation.NEVER)
    public void testRegisterDublicate() throws Exception {
        User newUser = new User(null, "New", USER1.getEmail(), "newPass", Role.ROLE_USER);
        mockMvc.perform(post(REST_URL + "/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonWithPassword(newUser,newUser.getPassword())))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testNoRegister() throws Exception {
        User newUser = new User(null, "New", "new@gmail.com", "newPass", Role.ROLE_USER);
        mockMvc.perform(post(REST_URL + "/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonWithPassword(newUser,newUser.getPassword()))
                .with(userAuth(USER1)))
                .andExpect(status().isForbidden())
                .andDo(print());
     }

    @Test
    public void testGetVote() throws Exception {
        mockMvc.perform(get(REST_URL +"/vote")
                .with(userAuth(USER1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER_VOTE.contentMatcher(USER1_VOTE2));
    }
    @Test
    public void testGetVoteWrongAuth() throws Exception {
        mockMvc.perform(get(REST_URL +"/vote")
                .with(userAuth(ADMIN)))
                .andExpect(status().isForbidden())
                .andDo(print());

    }

    @Test
    public void testSetVote() throws Exception {
        Vote vote = new Vote(USER2,RESTAURANT3);
        ResultActions action =mockMvc.perform(put(REST_URL +"/vote/"+RESTAURANT3_ID)
                .with(userAuth(USER2))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(vote)))
                .andDo(print())
                .andExpect(status().isOk());
        Vote returned = MATCHER_VOTE.fromJsonAction(action);
        vote.setId(returned.getId());
        MATCHER_VOTE.assertEquals(vote, userService.getVote(USER2_ID));
    }

}
