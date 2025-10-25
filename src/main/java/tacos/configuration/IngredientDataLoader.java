package tacos.configuration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tacos.persistence.entity.Ingredient;
import tacos.persistence.repository.IngredientRepository;

import static tacos.domain.IngredientType.*;

@Configuration
public class IngredientDataLoader {
    @Bean
    public CommandLineRunner dataLoader(IngredientRepository repository) {
        return args -> {
            repository.save(new Ingredient("FLTO", "Flour Tortilla", WRAP));
            repository.save(new Ingredient("COTO", "Corn Tortilla", WRAP));
            repository.save(new Ingredient("GRBF", "Ground Beef", PROTEIN));
            repository.save(new Ingredient("CARN", "Carnitas", PROTEIN));
            repository.save(new Ingredient("TMTO", "Decide Tomatoes", VEGGIES));
            repository.save(new Ingredient("LETC", "Lettuce", VEGGIES));
            repository.save(new Ingredient("CHED", "Cheddar", CHEESE));
            repository.save(new Ingredient("JACK", "Monterrey Jack", CHEESE));
            repository.save(new Ingredient("SLSA", "Salsa", SAUCE));
            repository.save(new Ingredient("SRCR", "Sour cream", SAUCE));
        };
    }
}
