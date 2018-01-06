package topjava.graduation.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import topjava.graduation.model.User;
import topjava.graduation.model.Vote;
import topjava.graduation.model.core.Role;
import topjava.graduation.service.UserService;
import topjava.graduation.util.AuthorizedUser;

@RestController
@RequestMapping(UserController.REST_URL)
public class UserController {
    public static final String REST_URL = "/users";
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public User get(@AuthenticationPrincipal AuthorizedUser authorizedUser) {
        int id=authorizedUser.getId();
        return service.get(id);
    }


    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void create(@RequestBody User user) {
        user.setRole(Role.ROLE_USER);
        service.save(user);
    }


    @GetMapping(value = "/vote", produces = MediaType.APPLICATION_JSON_VALUE)
    public Vote getVote(@AuthenticationPrincipal AuthorizedUser authorizedUser) {
        int id=authorizedUser.getId();
        return service.getVote(id);
    }

    @PutMapping(value = "/vote/{id_menu}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Vote setVote(@AuthenticationPrincipal AuthorizedUser authorizedUser, @PathVariable("id_menu") int id_restaurant) {
        int id=authorizedUser.getId();
        return service.setVote(id,id_restaurant);
    }

}
