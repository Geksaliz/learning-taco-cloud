package tacos.web;

import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import tacos.domain.IngredientType;
import tacos.persistence.entity.Ingredient;
import tacos.persistence.entity.Taco;
import tacos.persistence.entity.TacoOrder;
import tacos.persistence.repository.IngredientRepository;

import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
public class DesignController {
    private final IngredientRepository ingredientRepository;

    public DesignController(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @ModelAttribute
    public void addIngredientsToModel(Model model) {
        final Iterable<Ingredient> ingredients = ingredientRepository.findAll();

        IngredientType[] types = IngredientType.values();
        for (IngredientType type : types) {
            model.addAttribute(
                    type.toString().toLowerCase(),
                    filterByType(ingredients, type)
            );
        }
    }

    @ModelAttribute(name = "tacoOrder")
    public TacoOrder order() {
        return new TacoOrder();
    }

    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }

    @GetMapping
    public String showDesignForm() {
        return "design";
    }

    @PostMapping
    public String processTaco(
            @Valid Taco taco,
            @NonNull Errors errors,
            @ModelAttribute TacoOrder tacoOrder
    ) {
        if (errors.hasErrors()) {
            return "design";
        }

        tacoOrder.add(taco);
        log.info("Processing taco: {}", taco);

        return "redirect:/orders/current";
    }

    private Iterable<Ingredient> filterByType(
            Iterable<Ingredient> ingredients,
            IngredientType type
    ) {
        return StreamSupport.stream(ingredients.spliterator(), false)
                .filter(x -> x.getType().equals(type))
                .collect(toList());
    }
}
