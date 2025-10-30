package tacos.web.api;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tacos.persistence.entity.Taco;
import tacos.persistence.repository.TacoRepository;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/api/tacos", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class TacoController {
    private final TacoRepository tacoRepository;

    @Value("${taco.page-size}")
    private int tacoPageSize;

    @GetMapping(params = "recent")
    public Iterable<Taco> recentTacos() {
        final PageRequest pageRequest = PageRequest.of(
                0,
                tacoPageSize,
                Sort.by("createdAt").descending()
        );

        return tacoRepository.findAll(pageRequest).getContent();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Taco> findById(@PathVariable Long id) {
        final Optional<Taco> optionalTaco = tacoRepository.findById(id);

        return tacoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Taco save(@RequestBody Taco taco) {
        return tacoRepository.save(taco);
    }
}
