package tacos.persistence.entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.List;

@Table
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Taco {
    @Id
    private Long id;

    @CreatedDate
    private LocalDateTime createdAt;

    @NotNull
    @Size(min = 5, message = "Name must be at least 5 characters long")
    @Setter
    private String name;

    @Size(min = 1, message = "You must choose at least 1 ingredient")
    @Setter
    @Transient
    private List<Ingredient> ingredients;

    public Taco(String name, List<Ingredient> ingredients) {
        this.name = name;
        this.ingredients = ingredients;
    }
}
