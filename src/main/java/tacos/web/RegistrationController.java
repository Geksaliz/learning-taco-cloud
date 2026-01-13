package tacos.web;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;
import tacos.configuration.security.RegistrationForm;
import tacos.persistence.repository.UserRepository;

@Controller
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegistrationController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    public Mono<String> registerForm() {
        return Mono.just("registration");
    }

    @PostMapping
    public Mono<String> processRegistration(RegistrationForm form) {
        userRepository.save(form.toUser(passwordEncoder));

        return Mono.just("redirect:/login");
    }
}
