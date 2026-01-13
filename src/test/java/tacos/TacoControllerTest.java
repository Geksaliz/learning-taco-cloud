package tacos;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import tacos.domain.IngredientType;
import tacos.persistence.entity.Ingredient;
import tacos.persistence.entity.Taco;
import tacos.persistence.repository.TacoRepository;

import java.util.Arrays;
import java.util.List;

public class TacoControllerTest extends IntegrationTest {
    @Autowired
    private TacoRepository tacoRepository;

    @Test
    public void shouldReturnRecentTacos() {
        final Taco[] tacos = {
                testTaco(1L), testTaco(2L),
                testTaco(3L), testTaco(4L),
                testTaco(5L), testTaco(6L),
                testTaco(7L), testTaco(8L),
                testTaco(9L), testTaco(10L),
                testTaco(11L), testTaco(12L),
                testTaco(13L), testTaco(14L),
                testTaco(15L), testTaco(16L)
        };

        StepVerifier.create(tacoRepository.saveAll(Arrays.asList(tacos)))
                .expectNextCount(16L)
                .verifyComplete();

        webTestClient.get().uri("/api/v1/tacos?recent")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isArray()
                .jsonPath("$.length()").isEqualTo(10)
                .jsonPath("$[0].name").isEqualTo(tacos[0].getName())
                .jsonPath("$[1].name").isEqualTo(tacos[1].getName())
                .jsonPath("$[9].name").isEqualTo(tacos[9].getName())
                .jsonPath("$[10]").doesNotExist();
    }

    @Test
    public void shouldReturnTacoById() {
        final Taco taco = testTaco(1L);

        StepVerifier.create(tacoRepository.save(taco))
                .expectNextMatches(t -> t.getName().equals(taco.getName()))
                .verifyComplete();

        webTestClient.get().uri("/api/v1/tacos/{id}", taco.getId())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("id").isNotEmpty()
                .jsonPath("name").isEqualTo("Taco 1");
    }

    @Test
    public void shouldSaveTaco() {
        webTestClient.post()
                .uri("/api/v1/tacos")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(testTaco(1L)), Taco.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("id").isNotEmpty()
                .jsonPath("name").isEqualTo("Taco 1");
    }

    private Taco testTaco(Long sequenceNumber) {
        return new Taco(
                "Taco " + sequenceNumber,
                List.of(
                        new Ingredient("INGA", "Ingredient A", IngredientType.WRAP),
                        new Ingredient("INGB", "Ingredient B", IngredientType.PROTEIN)
                )
        );
    }
}