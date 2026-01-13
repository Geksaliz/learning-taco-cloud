package tacos.web.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tacos.persistence.entity.Taco;
import tacos.persistence.repository.TacoRepository;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/api/v1/tacos", produces = APPLICATION_JSON_VALUE)
public class TacoController {
    private final TacoRepository tacoRepository;
    private final int tacoPageSize;

    public TacoController(
            TacoRepository tacoRepository,
            @Value("${taco.page-size}") int tacoPageSize
    ) {
        this.tacoRepository = tacoRepository;
        this.tacoPageSize = tacoPageSize;
    }

    @GetMapping(params = "recent")
    public Flux<Taco> recentTacos() {
        return tacoRepository.findAll().take(tacoPageSize);
    }

    @GetMapping("/{id}")
    public Mono<Taco> findById(@PathVariable Long id) {
        return tacoRepository.findById(id);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Taco> save(@RequestBody Mono<Taco> taco) {
        return tacoRepository.saveAll(taco).next();
    }
}
