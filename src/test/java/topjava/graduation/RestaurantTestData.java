package topjava.graduation;


import topjava.graduation.model.Menu;
import topjava.graduation.model.Restaurant;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static topjava.graduation.model.AbstractBaseEntity.START_SEQ;

public class RestaurantTestData {
    public static final BeanMatcher<Restaurant> MATCHER_RESTAURANT = BeanMatcher.of(Restaurant.class);
    public static final int RESTAURANT1_ID = START_SEQ+3;
    public static final Restaurant  RESTAURANT1 = new Restaurant(RESTAURANT1_ID,"Биг Мак");

    public static final Menu MENU1 = new Menu(START_SEQ+6, LocalDate.now().minusDays(1), RESTAURANT1);
    public static final Menu MENU2 = new Menu(START_SEQ+8, LocalDate.now(), RESTAURANT1);

    public static final List<Menu> RESTAURANT1_MENUS=Arrays.asList(MENU2,MENU1);
}
