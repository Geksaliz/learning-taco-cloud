package tacos.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Value;

@Value
@Entity(name = "ingredient_ref")
public class IngredientRef {
    @Id
    String id;
    String ingredient;
    Integer taco;
    Integer taco_key;
}
