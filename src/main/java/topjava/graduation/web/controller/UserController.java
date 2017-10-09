package topjava.graduation.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import topjava.graduation.model.User;
import topjava.graduation.service.UserService;

@RestController
@RequestMapping(UserController.REST_URL)
public class UserController {
    static final String REST_URL = "/users";

    protected final Logger log = LoggerFactory.getLogger(getClass());

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User get(@PathVariable("id") int id) {
        return service.get(id);
    }


    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") int id) {
        log.info("delete {}",id);
        service.delete(id);
    }

}
