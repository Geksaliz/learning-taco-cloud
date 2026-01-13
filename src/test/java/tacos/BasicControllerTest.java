package tacos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BasicControllerTest extends IntegrationTest {

    @Test
    void testHomePage() throws Exception {
        webTestClient.get().uri("/")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .consumeWith(response -> {
                    final String body = response.getResponseBody();

                    assertNotNull(body);
                    assertTrue(body.contains("Welcome to..."));
                    assertTrue(body.contains("Create order"));
                });
    }

    @Test
    void testLoginPage() throws Exception {
        webTestClient.get().uri("/login")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .consumeWith(response -> {
                    final String body = response.getResponseBody();

                    assertNotNull(body);
                    assertTrue(body.contains("<input type=\"text\" name=\"username\""));
                    assertTrue(body.contains("<input type=\"password\" name=\"password\""));
                });
    }
}
