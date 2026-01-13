package tacos.persistence.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import tacos.persistence.entity.Ingredient;

@Repository
public interface IngredientRepository extends ReactiveCrudRepository<Ingredient, String> {
}
