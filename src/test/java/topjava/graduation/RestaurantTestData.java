package topjava.graduation;


import topjava.graduation.matcher.BeanMatcher;
import topjava.graduation.model.Menu;
import topjava.graduation.model.Restaurant;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static topjava.graduation.model.core.AbstractBaseEntity.START_SEQ;

public class RestaurantTestData {
    public static final BeanMatcher<Restaurant> MATCHER_RESTAURANT = BeanMatcher.of(Restaurant.class);
    public static final int RESTAURANT1_ID = START_SEQ+3;
    public static final int RESTAURANT2_ID = START_SEQ+4;
    public static final int RESTAURANT3_ID = START_SEQ+5;
    public static final Restaurant  RESTAURANT1 = new Restaurant(RESTAURANT1_ID,"Биг Мак");
    public static final Restaurant  RESTAURANT2 = new Restaurant(RESTAURANT2_ID,"Дак гриль");
    public static final Restaurant  RESTAURANT3 = new Restaurant(RESTAURANT3_ID,"Пельмени");

    public static final BeanMatcher<Menu> MATCHER_MENU = BeanMatcher.of(Menu.class);
    public static final int MENU1_ID = START_SEQ+6;
    public static final int MENU2_ID = START_SEQ+7;
    public static final int MENU3_ID = START_SEQ+8;
    public static final int MENU4_ID = START_SEQ+9;

    public static final Menu MENU1 = new Menu(MENU1_ID, LocalDate.now().minusDays(1), RESTAURANT1);
    public static final Menu MENU2 = new Menu(MENU2_ID, LocalDate.now().minusDays(1), RESTAURANT3);
    public static final Menu MENU3 = new Menu(MENU3_ID, LocalDate.now(), RESTAURANT1);
    public static final Menu MENU4 = new Menu(MENU4_ID, LocalDate.now(), RESTAURANT2);

    public static final List<Restaurant> RESTAURANTS=Arrays.asList(RESTAURANT1,RESTAURANT2,RESTAURANT3);
}
