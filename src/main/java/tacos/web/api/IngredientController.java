package tacos.web.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import tacos.persistence.entity.Ingredient;
import tacos.persistence.repository.IngredientRepository;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/ingredients/ingredients", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class IngredientController {

    private final IngredientRepository ingredientRepository;

    @GetMapping
    public Iterable<Ingredient> allIngredients() {
        return ingredientRepository.findAll();
    }

    @GetMapping("/{id}")
    public Ingredient getOne(@PathVariable String id) {
        final Optional<Ingredient> ingredientOptional = ingredientRepository.findById(id);
        return ingredientOptional.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingredient with id `%s` not found".formatted(id)));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Ingredient create(@RequestBody Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        ingredientRepository.deleteById(id);
    }
}
