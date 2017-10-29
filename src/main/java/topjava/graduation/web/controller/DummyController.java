package topjava.graduation.web.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DummyController {

    @GetMapping("/")
    public String root() {
        return "readme";
    }

}
