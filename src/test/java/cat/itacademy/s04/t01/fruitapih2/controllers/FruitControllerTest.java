package cat.itacademy.s04.t01.fruitapih2.controllers;

import cat.itacademy.s04.t01.fruitapih2.model.Fruit;
import cat.itacademy.s04.t01.fruitapih2.repository.FruitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FruitControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FruitRepository fruitRepository;

    @BeforeEach
    void setUp() {
        fruitRepository.deleteAll();
    }

    @Test
    void shouldReturnIsCreatedWhenPostFruit() throws Exception{
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
    void shouldReturn400WhenDataInvalid() throws Exception{
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

    @Test
    void shouldReturnListOfFruitsWhenTheyExist() throws Exception{
        Fruit apple = new Fruit();
        apple.setName("Apple");
        apple.setWeightInKg(5);
        fruitRepository.save(apple);

        Fruit banana = new Fruit();
        banana.setName("Banana");
        banana.setWeightInKg(8);
        fruitRepository.save(banana);

        mockMvc.perform(get("/fruits"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.size()").value(2));
    }

    @Test
    void shouldReturnEmptyArrayWhenNoFruitsExist() throws Exception {

        mockMvc.perform(get("/fruits"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());

    }

    @Test
    void shouldReturnFruitWhenIdExists() throws Exception {
        Fruit apple = new Fruit();
        apple.setName("Apple");
        apple.setWeightInKg(5);
        Fruit fruitSaved = fruitRepository.save(apple);
        Long id = fruitSaved.getId();

        mockMvc.perform(get("/fruits/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("Apple"))
                .andExpect(jsonPath("$.weightInKg").value(5));
    }

    @Test
    void shouldReturn404WhenIdDoesNotExist() throws Exception {

        mockMvc.perform(get("/fruits/000"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void shouldReturn200AndUpdatedFruitWhenPut() throws Exception {
        Fruit apple = new Fruit();
        apple.setName("Apple");
        apple.setWeightInKg(5);
        Fruit fruitSaved = fruitRepository.save(apple);

        String updateBody = """
            {"name": "Green Apple", "weightInKg": 10}
            """;

        mockMvc.perform(put("/fruits/" + fruitSaved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(fruitSaved.getId()))
                .andExpect(jsonPath("$.name").value("Green Apple"))
                .andExpect(jsonPath("$.weightInKg").value(10));

    }

    @Test
    void shouldReturn404WhenUpdatingIdInvalid () throws Exception {
        String updateBody = """
            {"name": "Ghost Fruit", "weightInKg": 5}
            """;

        mockMvc.perform(put("/fruits/00")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateBody))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    void shouldReturn400WhenUpdatingDataInvalid() throws Exception {
        String invalidBody = """
            {"name": "", "weightInKg": -5}
            """;

        mockMvc.perform(put("/fruits/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }

    @Test
    void shouldReturn204AndDeleteFruitWhenIdExists() throws Exception {
        Fruit apple = new Fruit();
        apple.setName("Apple");
        apple.setWeightInKg(5);
        Fruit fruitSaved = fruitRepository.save(apple);
        Long id = fruitSaved.getId();

        mockMvc.perform(delete("/fruits/" + id))
                .andExpect(status().isNoContent());

        assertTrue(fruitRepository.findById(id).isEmpty());
    }

    @Test
    void shouldReturn404WhenDeletingNonExistentId() throws Exception {
        mockMvc.perform(delete("/fruits/000"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").exists());
    }
}
