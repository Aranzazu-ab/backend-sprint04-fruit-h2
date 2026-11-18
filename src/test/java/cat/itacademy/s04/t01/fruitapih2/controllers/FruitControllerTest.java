package cat.itacademy.s04.t01.fruitapih2.controllers;

import cat.itacademy.s04.t01.fruitapih2.repository.FruitRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FruitControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FruitRepository fruitRepository;

    @Test
    void createFruitShouldSaveIt() throws Exception{
        String body = """
                {"name": "apples", "weightInKg": 5}
                """;

        mockMvc.perform(post("/fruits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value("apples"))
                .andExpect(jsonPath("$.weightInKg").value(5));
    }

    @Test
    void createFruitShouldReturn400WhenDataInvalid() throws Exception{
        String body= """
                {"name":"", "weightInKg": 0 }
                """;

        mockMvc.perform(post("/fruits")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").exists());
    }


}
