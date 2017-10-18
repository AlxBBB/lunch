package topjava.graduation.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import topjava.graduation.model.User;
import topjava.graduation.model.Vote;
import topjava.graduation.service.UserService;

@RestController
@RequestMapping(UserController.REST_URL)
public class UserController {
    static final String REST_URL = "/user";
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User get(@PathVariable("id") int id) {
        log.info("get {}", id);
        return service.get(id);
    }

    @GetMapping(value = "/{id}/vote", produces = MediaType.APPLICATION_JSON_VALUE)
    public Vote getVote(@PathVariable("id") int id) {
        log.info("get vote today by {}", id);
        return service.getVote(id);
    }

    @PostMapping(value = "/{id_user}/vote/{id_restaurant}")
    public void setVote(@PathVariable("id_user") int id_user, @PathVariable("id_restaurant") int id_restaurant) {
        log.info("set vote today by  user={}  restaurant={}", id_user, id_restaurant);
        service.setVote(id_user,id_restaurant);
    }


    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") int id) {
        log.info("delete {}", id);
        service.delete(id);
    }

}
