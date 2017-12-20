package topjava.graduation.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import topjava.graduation.model.Menu;
import topjava.graduation.service.MenuService;

@RestController
@RequestMapping(MenuController.REST_URL)
public class MenuController {
    public static final String REST_URL = "/menus";
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final MenuService service;

    @Autowired
    public MenuController(MenuService service) {
        this.service = service;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Menu get(@PathVariable("id") int id) {
        log.info("get {}", id);
        return service.get(id);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") int id) {
        log.info("delete {}", id);
        service.delete(id);
    }
}
