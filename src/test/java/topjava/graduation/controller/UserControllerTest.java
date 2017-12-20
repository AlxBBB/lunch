package topjava.graduation.controller;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import topjava.graduation.model.Vote;
import topjava.graduation.service.UserService;
import topjava.graduation.web.json.JsonUtil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static topjava.graduation.RestaurantTestData.*;
import static topjava.graduation.UserTestData.*;
import static topjava.graduation.VoteTestData.*;
import static topjava.graduation.web.controller.UserController.REST_URL;

public class UserControllerTest extends AbstractControllerTest {
    @Autowired
    UserService userService;

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + "/" + USER1_ID))
                //   .with(userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER_USER.contentMatcher(USER1));
    }

    @Test
    public void testGetVote() throws Exception {
        mockMvc.perform(get(REST_URL + "/" + USER1_ID+"/vote"))
                //   .with(userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER_VOTE.contentMatcher(USER1_VOTE2));
    }

    @Test
    public void testSetVote() throws Exception {
        Vote vote = new Vote(USER2,RESTAURANT3);
        ResultActions action =mockMvc.perform(put(REST_URL +"/"+ USER2_ID+"/vote/"+RESTAURANT3_ID)
                .contentType(MediaType.APPLICATION_JSON)
                //.with(userHttpBasic(USER2))
                .content(JsonUtil.writeValue(vote)))
                .andDo(print())
                .andExpect(status().isOk());
        Vote returned = MATCHER_VOTE.fromJsonAction(action);
        vote.setId(returned.getId());
        MATCHER_VOTE.assertEquals(vote, userService.getVote(USER2_ID));
    }

}
