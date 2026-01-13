package tacos.web.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tacos.persistence.entity.Ingredient;
import tacos.persistence.repository.IngredientRepository;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/ingredients", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class IngredientController {

    private final IngredientRepository ingredientRepository;

    @GetMapping
    public Flux<Ingredient> allIngredients() {
        return ingredientRepository.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Ingredient> getOne(@PathVariable String id) {
        return ingredientRepository.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Ingredient> create(@RequestBody Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        ingredientRepository.deleteById(id);
    }
}
