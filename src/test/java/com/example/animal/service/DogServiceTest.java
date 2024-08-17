package com.example.animal.service;

import com.example.animal.exceptions.DogNotFoundException;
import com.example.animal.model.Dog;
import com.example.animal.repository.DogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class DogServiceTest {

    private DogService dogService;
    private DogRepository mockDogRepository;

    public final Dog input = new Dog(null, "Maggie", "female", "brown, black, white", 13);
    public final Dog input2 = new Dog(null, "Dakota", "female", "black", 5);
    public final Dog recordWithId = new Dog(UUID.randomUUID(), "Maggie", "female", "brown, black, white", 13);
    public final Dog recordWithId2 = new Dog(recordWithId.getId(), "Dakota", "female", "black", 5);

    public final UUID id = UUID.fromString("59c47568-fde0-4dd7-9aef-03db6a962810");

    @BeforeEach
    public void setup() {
        mockDogRepository = Mockito.mock(DogRepository.class);
        dogService = new DogService(mockDogRepository);
    }

    @Test
    public void create_shouldReturnCreatedDog() {
        Mockito.when(mockDogRepository.save(Mockito.any())).thenReturn(recordWithId);
        Dog response = dogService.create(input);
        assertEquals(recordWithId, response);
    }

    @Test
    public void findAllDogs_shouldReturnListOfDogs() {
        List<Dog> dogs = new ArrayList<>();
        dogs.add(input);
        dogs.add(input2);
        Mockito.when(mockDogRepository.findAll()).thenReturn(dogs);
        List<Dog> response = dogService.findAllDogs();
        assertEquals(dogs, response);
    }

    @Test
    public void getById_shouldReturnDog() {
        Mockito.when(mockDogRepository.findById(recordWithId.getId())).thenReturn(Optional.of(recordWithId));
        Dog response = dogService.getById(recordWithId.getId());
        assertEquals(recordWithId, response);
    }

    @Test
    public void getById_throwsExceptionWhenBookWasNotFound() {
        Mockito.when(mockDogRepository.findById(id)).thenReturn(Optional.empty());
        DogNotFoundException exception = assertThrows(DogNotFoundException.class, () -> dogService.getById(id));
        assertEquals("A dog with id: " + id + " was not found.", exception.getMessage());
    }


}