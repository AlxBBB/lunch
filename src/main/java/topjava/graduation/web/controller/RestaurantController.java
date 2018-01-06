package topjava.graduation.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import topjava.graduation.model.Menu;
import topjava.graduation.model.Restaurant;
import topjava.graduation.service.RestaurantService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(RestaurantController.REST_URL)
public class RestaurantController {
    public static final String REST_URL = "/restaurants";
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final RestaurantService service;

    @Autowired
    public RestaurantController(RestaurantService service) {
        this.service = service;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Restaurant get(@PathVariable("id") int id) {
        return service.get(id);
    }


    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurant> getAll() {
        return service.getAll();
    }

    @PostMapping(value = "/admin", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Restaurant createOrUpdate(@RequestBody Restaurant restaurant) {
        return service.save(restaurant);
    }

    @GetMapping(value = "/menus", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Menu> getAllMenu() {
        return service.getAllMenu(LocalDate.now());
    }

    @GetMapping(value = "/menus/admin", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Menu> getAllMenuByDate(@RequestParam(value = "date", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        return service.getAllMenu(date);
    }

    @GetMapping(value = "/{id}/menus", produces = MediaType.APPLICATION_JSON_VALUE)
    public Menu getMenu(@PathVariable("id") int restaurant_id) {
        return service.getMenu(restaurant_id, LocalDate.now());
    }

    @GetMapping(value = "/{id}/menus/admin", produces = MediaType.APPLICATION_JSON_VALUE)
    public Menu getMenuByDate(@PathVariable("id") int restaurant_id, @RequestParam(value = "date", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        return service.getMenu(restaurant_id, date);
    }

    @PostMapping(value = "/{id}/menus/admin", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Menu saveMenu(@RequestBody Menu menu, @PathVariable("id") int restaurant_id) {
        if (menu.getRestaurant()==null||menu.getRestaurant().getId()!=restaurant_id) {
           menu.setRestaurant(service.get(restaurant_id)); //будем дружелюбными
        }
        return service.saveMenu(menu);
    }
}
