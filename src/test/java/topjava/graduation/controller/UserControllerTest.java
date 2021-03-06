package topjava.graduation.controller;


import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import topjava.graduation.model.User;
import topjava.graduation.model.Vote;
import topjava.graduation.model.core.Role;
import topjava.graduation.web.json.JsonUtil;

import java.time.LocalTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static topjava.graduation.RestaurantTestData.RESTAURANT3_ID;
import static topjava.graduation.TestUtil.userHttpBasic;
import static topjava.graduation.UserTestData.*;
import static topjava.graduation.web.controller.UserController.REST_URL;

public class UserControllerTest extends AbstractControllerTest {


    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(USER1)))
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
                .content(jsonWithPassword(newUser, newUser.getPassword())))
                .andExpect(status().isOk())
                .andDo(print());
        mockMvc.perform(get(REST_URL + "/")
                .with(userHttpBasic(newUser)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testWrongRegister() throws Exception {
        User newUser = new User(null, "New", "new@gmail.com", null, Role.ROLE_USER);
        mockMvc.perform(post(REST_URL + "/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonWithPassword(newUser, newUser.getPassword())))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    public void testRegisterHtmlUnsafe() throws Exception {
        User newUser = new User(null, "<script>alert(123)</script>", "ne", "new", Role.ROLE_USER);
        mockMvc.perform(post(REST_URL + "/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonWithPassword(newUser, newUser.getPassword())))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    public void testNullRegister() throws Exception {
        mockMvc.perform(post(REST_URL + "/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(null)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    public void testBadRegister() throws Exception {
        mockMvc.perform(post(REST_URL + "/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"test\",\"nickname\":\"nick\"}"))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }


    @Test
    @Transactional(propagation = Propagation.NEVER)
    public void testRegisterDublicate() throws Exception {
        User newUser = new User(null, "New", USER1.getEmail(), "newPass", Role.ROLE_USER);
        mockMvc.perform(post(REST_URL + "/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonWithPassword(newUser, newUser.getPassword())))
                .andExpect(status().isConflict())
                .andDo(print());
    }

    @Test
    public void testNoRegister() throws Exception {
        User newUser = new User(null, "New", "new@gmail.com", "newPass", Role.ROLE_USER);
        mockMvc.perform(post(REST_URL + "/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonWithPassword(newUser, newUser.getPassword()))
                .with(userHttpBasic(USER1)))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    @Test
    public void testGetVote() throws Exception {
        mockMvc.perform(get(REST_URL + "/vote")
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER_VOTE.contentMatcher(USER1_VOTE2));
    }

    @Test
    public void testGetVoteWrongAuth() throws Exception {
        mockMvc.perform(get(REST_URL + "/vote")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isForbidden())
                .andDo(print());

    }

    @Test
    public void testSetVote() throws Exception {
        ResultActions action = mockMvc.perform(post(REST_URL + "/vote/" + RESTAURANT3_ID)
                .with(userHttpBasic(USER2))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        Vote returned = MATCHER_VOTE.fromJsonAction(action);
        MATCHER_VOTE.assertEquals(returned, userService.getVote(USER2_ID));
    }

    @Test
    public void testUpdateVote() throws Exception {
        if (LocalTime.now().isBefore(LocalTime.of(11, 00))) {
            ResultActions action = mockMvc.perform(post(REST_URL + "/vote/" + RESTAURANT3_ID)
                    .with(userHttpBasic(USER1))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk());
            Vote returned = MATCHER_VOTE.fromJsonAction(action);
            MATCHER_VOTE.assertEquals(returned, userService.getVote(USER1_ID));
        } else {
            mockMvc.perform(post(REST_URL + "/vote/" + RESTAURANT3_ID)
                    .with(userHttpBasic(USER1))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isUnprocessableEntity());
        }
    }

    @Test
    public void testSetWrongVote() throws Exception {
        mockMvc.perform(post(REST_URL + "/vote/" + 0)
                .with(userHttpBasic(USER2))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

}
