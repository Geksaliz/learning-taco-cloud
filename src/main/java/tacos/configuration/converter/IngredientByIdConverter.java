package tacos.configuration.converter;

import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import tacos.persistence.entity.Ingredient;
import tacos.persistence.repository.IngredientRepository;

import static java.lang.String.format;

@Component
public class IngredientByIdConverter implements Converter<String, Mono<Ingredient>> {
    private final IngredientRepository ingredientRepository;

    public IngredientByIdConverter(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public Mono<Ingredient> convert(@NonNull String id) {
        return ingredientRepository.findById(id);
    }
}
