package topjava.graduation.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import topjava.graduation.model.Dish;
import topjava.graduation.model.Menu;
import topjava.graduation.model.Restaurant;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static topjava.graduation.RestaurantTestData.*;


public class RestaurantServiceTest extends AbstractServiceTest {
    @Autowired
    RestaurantService service;

    @Test
    public void testGet() throws Exception {
        Restaurant restaurant = service.get(RESTAURANT1_ID);
        MATCHER_RESTAURANT.assertEquals(RESTAURANT1, restaurant);
    }

    @Test
    public void testGetAll() throws Exception {
        List<Restaurant> restaurants = service.getAll();
        MATCHER_RESTAURANT.assertListEquals(restaurants,RESTAURANTS);
    }

    @Test
    public void testAddRestaurante() throws Exception {
        Restaurant restaurant=new Restaurant(null,"Новый");
        restaurant=service.save(restaurant);
        MATCHER_RESTAURANT.assertListEquals(Arrays.asList(RESTAURANT1,RESTAURANT2,restaurant,RESTAURANT3),service.getAll());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testAddDoubleRestaurante() throws Exception {
        Restaurant restaurant=new Restaurant();
        restaurant.setName(RESTAURANT2.getName());
        service.save(restaurant);
    }

    @Test
    public void testUpdateRestaurante() throws Exception {
        Restaurant restaurant=new Restaurant(RESTAURANT2_ID,RESTAURANT2.getName());
        restaurant.setName("Правка");
        restaurant=service.save(restaurant);
        MATCHER_RESTAURANT.assertListEquals(Arrays.asList(RESTAURANT1,RESTAURANT3,restaurant),service.getAll());
    }

    @Test
    public void testGetMenu() throws Exception {
        Menu menu = service.getMenu(RESTAURANT1_ID, LocalDate.now());
        MATCHER_MENU.assertEquals(menu,MENU3);
    }

    @Test
    public void testGetAllMenu() throws Exception {
        List<Menu> menu = service.getAllMenu(LocalDate.now().minusDays(1));
        MATCHER_MENU.assertListEquals(menu,Arrays.asList(MENU1,MENU2));
    }

    @Test
    public void testSaveMenu() throws Exception {
        Menu newMenu=new Menu(null,LocalDate.now().plusDays(1),RESTAURANT1);
        List<Dish> dishes=Arrays.asList(new Dish("Блюдо", 1000), new Dish("Напиток", 300));
        newMenu = service.saveMenu(newMenu);
        MATCHER_MENU.assertEquals(newMenu,service.getMenu(RESTAURANT1_ID,LocalDate.now().plusDays(1)));
    }

}
