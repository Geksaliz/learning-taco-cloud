package tacos.persistence.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import tacos.domain.IngredientType;

@Table
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class Ingredient {
    @Id
    private String id;
    private String name;
    private IngredientType type;
}
