package tacos.configuration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import tacos.persistence.entity.Ingredient;
import tacos.persistence.entity.Taco;
import tacos.persistence.repository.IngredientRepository;
import tacos.persistence.repository.TacoRepository;

import java.util.List;

import static tacos.domain.IngredientType.*;

@Profile("!prod")
@Configuration
public class IngredientDataLoader {
    @Bean
    public CommandLineRunner dataLoader(IngredientRepository repository, TacoRepository tacoRepository) {
        return args -> {
            final Ingredient flourTortilla = new Ingredient("FLTO", "Flour Tortilla", WRAP);
            final Ingredient cornTortilla = new Ingredient("COTO", "Corn Tortilla", WRAP);
            final Ingredient groundBeef = new Ingredient("GRBF", "Ground Beef", PROTEIN);
            final Ingredient carnitas = new Ingredient("CARN", "Carnitas", PROTEIN);
            final Ingredient tomatoes = new Ingredient("TMTO", "Decide Tomatoes", VEGGIES);
            final Ingredient lettuce = new Ingredient("LETC", "Lettuce", VEGGIES);
            final Ingredient cheddar = new Ingredient("CHED", "Cheddar", CHEESE);
            final Ingredient jack = new Ingredient("JACK", "Monterrey Jack", CHEESE);
            final Ingredient salsa = new Ingredient("SLSA", "Salsa", SAUCE);
            final Ingredient sourCream = new Ingredient("SRCR", "Sour cream", SAUCE);

            repository.saveAll(List.of(flourTortilla, cornTortilla, groundBeef, carnitas, tomatoes, lettuce,
                    cheddar, jack, salsa, sourCream));

            tacoRepository.save(new Taco(null, "Carnivore",
                    List.of(flourTortilla, groundBeef, carnitas, sourCream, salsa, cheddar)));

            tacoRepository.save(new Taco(null, "Bovine Bounty",
                    List.of(cornTortilla, groundBeef, cheddar, jack, sourCream)));

            tacoRepository.save(new Taco(null, "Veg-out",
                    List.of(flourTortilla, cornTortilla, tomatoes, lettuce, salsa)));
        };
    }
}
