package com.example.animal.controller;

import com.example.animal.exceptions.DogNotFoundException;
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

    private DogController dogController;
    private DogService mockDogService;

    public final Dog input = new Dog(null, "Maggie", "female", "brown, black, white", 13);
    public final Dog input2 = new Dog(null, "Dakota", "female", "black", 5);
    public final Dog recordWithId = new Dog(UUID.randomUUID(), "Maggie", "female", "brown, black, white", 13);
    public final Dog recordWithId2 = new Dog(recordWithId.getId(), "Dakota", "female", "black", 5);

    public final UUID id = UUID.fromString("59c47568-fde0-4dd7-9aef-03db6a962810");

    @BeforeEach
    public void setup() {
        mockDogService = Mockito.mock(DogService.class);
        dogController = new DogController(mockDogService);
    }

    @Test
    public void createDog_shouldReturnDogAndCREATEDHttpStatus() {
        Mockito.when(mockDogService.create(Mockito.any())).thenReturn(recordWithId);
        ResponseEntity<Dog> response = dogController.createDog(input);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(recordWithId, response.getBody());
    }

    @Test
    public void getAllDogs_shouldReturnListOfDogsAndOKHttpStatus() {
        List<Dog> dogs = new ArrayList<>();
        dogs.add(input);
        dogs.add(input2);
        Mockito.when(mockDogService.findAllDogs()).thenReturn(dogs);
        ResponseEntity<List<Dog>> response = dogController.getAllDogs();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dogs, response.getBody());
    }

    @Test
    public void getDogById_shouldReturnDogAndOKHttpStatus() {
        Mockito.when(mockDogService.getById(recordWithId.getId())).thenReturn(recordWithId);
        ResponseEntity<Dog> response = dogController.getDogById(recordWithId.getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(recordWithId, response.getBody());
    }

    @Test
    public void getDogsById_shouldReturn404WhenDogNotFound() {
        Mockito.when(mockDogService.getById(id)).thenThrow(new DogNotFoundException("A dog with id: " + id + " was not found."));
        ResponseEntity<Dog> response = dogController.getDogById(id);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void getDogByName_shouldReturnListOfDogsAndOKHttpStatus() {
        Mockito.when(mockDogService.getDogByName(recordWithId.getName())).thenReturn(List.of(recordWithId));
        ResponseEntity<List<Dog>> response = dogController.getDogByName(recordWithId.getName());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(List.of(recordWithId), response.getBody());
    }

    @Test
    public void updateDog_shouldReturnDogAndOKHttpStatus() {
        Mockito.when(mockDogService.updateDog(input2, recordWithId.getId())).thenReturn(recordWithId2);
        ResponseEntity<Dog> response = dogController.updateDog(input2, recordWithId.getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(recordWithId2, response.getBody());
    }

    @Test
    public void updateDog_shouldReturn404WhenDogNotFound() {
        Mockito.when(mockDogService.updateDog(input, id)).thenThrow(new DogNotFoundException("A dog with id: " + id + " was not found."));
        ResponseEntity<Dog> response = dogController.updateDog(input, id);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void patchDog_shouldReturnDogAndOKHttpStatus() {
        Mockito.when(mockDogService.patch(input2, recordWithId.getId())).thenReturn(recordWithId2);
        ResponseEntity<Dog> response = dogController.patchDog(input2, recordWithId.getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(recordWithId2, response.getBody());
    }

    @Test
    public void patchDog_shouldReturn404WhenBookNotFound() {
        Mockito.when(mockDogService.patch(input, id)).thenThrow(new DogNotFoundException("A dog with id: " + id + " was not found."));
        ResponseEntity<Dog> response = dogController.patchDog(input, id);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}