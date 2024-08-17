package com.example.animal.controller;

import com.example.animal.model.Dog;
import com.example.animal.service.DogService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class DogControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private DogService mockDogService;

    private final Dog dog = new Dog(UUID.fromString("59c47568-fde0-4dd7-9aef-03db6a962810"), "Maggie", "female", "brown", 13);

    @Test
    public void createDog() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .post("/api/dogs")
                .content(asJsonString(new Dog(null, "Maggie", "female", "brown", 13)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void getAllDogs() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/api/dogs").accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    public void getDogById() throws Exception {
        Mockito.when(mockDogService.getById(UUID.fromString("59c47568-fde0-4dd7-9aef-03db6a962810"))).thenReturn(new Dog());
        mvc.perform(MockMvcRequestBuilders
                .get("/api/dogs/59c47568-fde0-4dd7-9aef-03db6a962810").accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    public void getDogByName() throws Exception {
        Mockito.when(mockDogService.getDogByName("Maggie")).thenReturn(List.of(new Dog()));
        mvc.perform(MockMvcRequestBuilders
                .get("/api/dogs/name/Maggie").accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    public void updateDog() throws Exception {
        Mockito.when(mockDogService.getById(UUID.fromString("59c47568-fde0-4dd7-9aef-03db6a962810"))).thenReturn(dog);
        mvc.perform( MockMvcRequestBuilders
                        .put("/api/dogs/59c47568-fde0-4dd7-9aef-03db6a962810")
                        .content(asJsonString(dog))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
