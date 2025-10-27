package tacos;

import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class BasicControllerTest extends IntegrationTest {

    @Test
    void testHomePage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect((content().string(containsString("Welcome to..."))))
                .andExpect((content().string(containsString("Create order"))));
    }

    @Test
    void testLoginPage() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect((content().string(containsString("<input type=\"text\" name=\"username\""))))
                .andExpect((content().string(containsString("<input type=\"password\" name=\"password\""))));
    }
}
