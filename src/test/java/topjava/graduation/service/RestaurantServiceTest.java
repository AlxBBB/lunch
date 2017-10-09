package topjava.graduation.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import topjava.graduation.model.Restaurant;

import static topjava.graduation.RestaurantTestData.*;


public class RestaurantServiceTest extends AbstractServiceTest {
    @Autowired
    RestaurantService service;

    @Test
    public void testGet() throws Exception {
        Restaurant restaurant = service.get(RESTAURANT1_ID);
        MATCHER_RESTAURANT.assertEquals(RESTAURANT1, restaurant);
    }
}
