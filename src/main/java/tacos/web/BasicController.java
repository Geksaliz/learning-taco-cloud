package tacos.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

@Controller
public class BasicController {

    @GetMapping("/")
    public Mono<String> home() {
        return Mono.just("home");
    }

    @GetMapping("/login")
    public Mono<String> login() {
        return Mono.just("login");
    }
}
