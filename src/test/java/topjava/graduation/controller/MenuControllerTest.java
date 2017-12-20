package topjava.graduation.controller;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import topjava.graduation.service.MenuService;
import topjava.graduation.web.controller.MenuController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static topjava.graduation.RestaurantTestData.*;
import static topjava.graduation.web.controller.MenuController.REST_URL;

public class MenuControllerTest extends AbstractControllerTest {
   @Autowired
   MenuService  menuService;

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + "/"+MENU1_ID ))
                //   .with(userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER_MENU.contentMatcher(MENU1));
    }

}
