package com.example.animal.controller;

import com.example.animal.model.Dog;
import com.example.animal.service.DogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class DogControllerTest {

    private DogController mockDogController;
    private DogService mockDogService;

    public final Dog input = new Dog(null, "Maggie", "female", "brown, black, white", 13);
    public final Dog input2 = new Dog(null, "Dakota", "female", "black", 5);
    public final Dog recordWithId = new Dog(UUID.randomUUID(), "Maggie", "female", "brown, black, white", 13);
    public final Dog recordWithId2 = new Dog(recordWithId.getId(), "Dakota", "female", "black", 5);

    @BeforeEach
    public void setup() {
        mockDogService = Mockito.mock(DogService.class);
        mockDogController = new DogController(mockDogService);
    }

    @Test
    public void createDog_shouldReturnDogAndCREATEDHttpStatus() {
        Mockito.when(mockDogService.create(Mockito.any())).thenReturn(recordWithId);
        ResponseEntity<Dog> response = mockDogController.createDog(input);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(recordWithId, response.getBody());
    }

    @Test
    public void getAllDogs_shouldReturnListOfDogsAndOKHttpStatus() {
        List<Dog> dogs = new ArrayList<>();
        dogs.add(input);
        dogs.add(input2);
        Mockito.when(mockDogService.findAllDogs()).thenReturn(dogs);
        ResponseEntity<List<Dog>> response = mockDogController.getAllDogs();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dogs, response.getBody());
    }

    @Test
    public void getDogById_shouldReturnDogAndOKHttpStatus() {
        Mockito.when(mockDogService.getById(recordWithId.getId())).thenReturn(recordWithId);
        ResponseEntity<Dog> response = mockDogController.getDogById(recordWithId.getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(recordWithId, response.getBody());
    }

    @Test
    public void getDogByName_shouldReturnDogAndOKHttpStatus() {
        Mockito.when(mockDogService.getDogByName(recordWithId.getName())).thenReturn(recordWithId);
        ResponseEntity<Dog> response = mockDogController.getDogByName(recordWithId.getName());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(recordWithId, response.getBody());
    }


}