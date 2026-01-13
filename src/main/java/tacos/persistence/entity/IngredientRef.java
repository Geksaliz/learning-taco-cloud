package tacos.persistence.entity;

import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Value
@Table(name = "ingredient_ref")
public class IngredientRef {
    @Id
    String id;
    String ingredient;
    Integer taco;
    Integer taco_key;
}
