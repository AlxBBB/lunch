package topjava.graduation.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import topjava.graduation.model.Restaurant;
import topjava.graduation.service.RestaurantService;

@RestController
@RequestMapping(RestaurantController.REST_URL)
public class RestaurantController {
    static final String REST_URL = "/restaurant";
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final RestaurantService service;

    @Autowired
    public RestaurantController(RestaurantService service) {
        this.service = service;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Restaurant get(@PathVariable("id") int id) {
        log.info("get {}", id);
        return service.get(id);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") int id) {
        log.info("delete {}", id);
        service.delete(id);
    }

}
