package topjava.graduation.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import topjava.graduation.model.Dish;
import topjava.graduation.model.Menu;
import topjava.graduation.model.Restaurant;
import topjava.graduation.service.RestaurantService;
import topjava.graduation.web.json.JsonUtil;


import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static topjava.graduation.RestaurantTestData.*;
import static topjava.graduation.TestUtil.userAuth;
import static topjava.graduation.UserTestData.*;
import static topjava.graduation.web.controller.RestaurantController.REST_URL;


public class RestaurantControllerTest extends AbstractControllerTest {

    @Autowired
    RestaurantService restaurantService;

    @Before
    public void setUp() {
       // userService.evictCache();
        jpaUtil.clear2ndLevelHibernateCache();
    }

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + "/"+RESTAURANT2_ID )
                .with(userAuth(USER1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER_RESTAURANT.contentMatcher(RESTAURANT2));
    }

    @Test
    public void testGetNotAuth() throws Exception {
        mockMvc.perform(get(REST_URL + "/"+RESTAURANT2_ID ))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }


    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL)
                .with(userAuth(USER1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER_RESTAURANT.contentListMatcher(RESTAURANTS));
    }

    @Test
    public void testAdd() throws Exception {
        Restaurant restaurant=new Restaurant(null,"Новый");
        ResultActions action =mockMvc.perform(post(REST_URL+"/admin")
                .with(userAuth(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(restaurant)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        Restaurant returned = MATCHER_RESTAURANT.fromJsonAction(action);
        restaurant.setId(returned.getId());
        MATCHER_RESTAURANT.assertEquals(restaurant, restaurantService.get(returned.getId()));
    }

    @Test
    public void testAddWrongAuth() throws Exception {
        Restaurant restaurant=new Restaurant(null,"Новый");
        ResultActions action =mockMvc.perform(post(REST_URL+"/admin")
                .with(userAuth(USER1))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(restaurant)))
                .andExpect(status().isForbidden())
                .andDo(print());
    }


    @Test
    public void testGetAllMenus() throws Exception {
        mockMvc.perform(get(REST_URL+"/menus")
                .with(userAuth(USER1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER_MENU.contentListMatcher(Arrays.asList(MENU4,MENU3)));
    }

    @Test
    public void testGetAllMenusByDate() throws Exception {
        mockMvc.perform(get(REST_URL+"/menus/admin" )
                .with(userAuth(ADMIN))
                .param("date",LocalDate.now().minusDays(1).toString()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER_MENU.contentListMatcher(Arrays.asList(MENU1,MENU2)));
    }

    @Test
    public void testGetAllMenusByDateWrongAuth() throws Exception {
        mockMvc.perform(get(REST_URL+"/menus/admin" )
                .with(userAuth(USER2))
                .param("date",LocalDate.now().minusDays(1).toString()))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    @Test
    public void testGetMenus() throws Exception {
        mockMvc.perform(get(REST_URL+"/"+RESTAURANT1_ID+"/menus")
                .with(userAuth(USER1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER_MENU.contentMatcher(restaurantService.getMenu(RESTAURANT1_ID,LocalDate.now())));
    }

    @Test
    public void testGetMenusByDate() throws Exception {
        mockMvc.perform(get(REST_URL+"/"+RESTAURANT3_ID+"/menus/admin" )
                .with(userAuth(ADMIN))
                .param("date",LocalDate.now().minusDays(1).toString()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER_MENU.contentMatcher(restaurantService.getMenu(RESTAURANT3_ID,LocalDate.now().minusDays(1))));
    }

    @Test
    public void testSaveMenus() throws Exception {
        Menu newMenu=new Menu(null,LocalDate.now().plusDays(1),RESTAURANT3);
        List<Dish> dishes=Arrays.asList(new Dish("Блюдо", 1000), new Dish("Напиток", 300));
        ResultActions action = mockMvc.perform(post(REST_URL+"/"+RESTAURANT3_ID+"/menus/admin" )
                .with(userAuth(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newMenu)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        Menu returned = MATCHER_MENU.fromJsonAction(action);
        newMenu.setId(returned.getId());
        MATCHER_MENU.assertEquals(newMenu, restaurantService.getMenu(RESTAURANT3_ID,newMenu.getDate()));
    }

    @Test
    public void testSaveMenusWrongAuth() throws Exception {
        Menu newMenu=new Menu(null,LocalDate.now().plusDays(1),RESTAURANT3);
        List<Dish> dishes=Arrays.asList(new Dish("Блюдо", 1000), new Dish("Напиток", 300));
        ResultActions action = mockMvc.perform(post(REST_URL+"/"+RESTAURANT3_ID+"/menus/admin" )
                .with(userAuth(USER1))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newMenu)))
                .andExpect(status().isForbidden())
                .andDo(print());
    }
}
