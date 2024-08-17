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

    @Test
    public void getDogByName_shouldReturnListOfDogs() {
        Mockito.when(mockDogRepository.findByName(recordWithId.getName())).thenReturn(List.of(recordWithId));
        List<Dog> response = dogService.getDogByName(recordWithId.getName());
        assertEquals(List.of(recordWithId), response);
    }

    @Test
    public void updateDog_shouldReturnUpdatedDog() {
        Mockito.when(mockDogRepository.findById(recordWithId.getId())).thenReturn(Optional.of(recordWithId));
        Mockito.when(mockDogRepository.save(Mockito.any())).thenReturn(recordWithId);
        Dog response = dogService.updateDog(input2, recordWithId.getId());
        assertEquals(recordWithId, response);
    }

    @Test
    public void updateDog_throwsExceptionWhenDogWasNotFound() {
        Mockito.when(mockDogRepository.findById(id)).thenReturn(Optional.empty());
        DogNotFoundException exception = assertThrows(DogNotFoundException.class, () -> dogService.updateDog(input, id));
        assertEquals("A dog with id: " + id + " was not found.", exception.getMessage());
    }

    @Test
    public void patchDog_throwsExceptionWhenDogWasNotFound() {
        Mockito.when(mockDogRepository.findById(id)).thenReturn(Optional.empty());
        DogNotFoundException exception = assertThrows(DogNotFoundException.class, () -> dogService.patch(input, id));
        assertEquals("A dog with id: " + id + " was not found.", exception.getMessage());
    }

    @Test
    public void patchDog_shouldReturnUpdatedName() {
        Dog input = new Dog();
        input.setName("Dakota");
        Mockito.when(mockDogRepository.findById(recordWithId.getId())).thenReturn(Optional.of(recordWithId));
        Mockito.when(mockDogRepository.save(Mockito.any())).thenAnswer(i -> i.getArguments()[0]);
        Dog response = dogService.patch(input, recordWithId.getId());
        assertEquals(recordWithId.getId(), response.getId());
        assertEquals("Dakota", response.getName());
        assertEquals("female", response.getGender());
        assertEquals("brown, black, white", response.getColor());
        assertEquals(13, response.getAge());
    }

    @Test
    public void patchDog_shouldReturnUpdatedGender() {
        Dog input = new Dog();
        input.setGender("male");
        Mockito.when(mockDogRepository.findById(recordWithId.getId())).thenReturn(Optional.of(recordWithId));
        Mockito.when(mockDogRepository.save(Mockito.any())).thenAnswer(i -> i.getArguments()[0]);
        Dog response = dogService.patch(input, recordWithId.getId());
        assertEquals(recordWithId.getId(), response.getId());
        assertEquals("Maggie", response.getName());
        assertEquals("male", response.getGender());
        assertEquals("brown, black, white", response.getColor());
        assertEquals(13, response.getAge());
    }

    @Test
    public void patchDog_shouldReturnUpdatedColor() {
        Dog input = new Dog();
        input.setColor("brown");
        Mockito.when(mockDogRepository.findById(recordWithId.getId())).thenReturn(Optional.of(recordWithId));
        Mockito.when(mockDogRepository.save(Mockito.any())).thenAnswer(i -> i.getArguments()[0]);
        Dog response = dogService.patch(input, recordWithId.getId());
        assertEquals(recordWithId.getId(), response.getId());
        assertEquals("Maggie", response.getName());
        assertEquals("female", response.getGender());
        assertEquals("brown", response.getColor());
        assertEquals(13, response.getAge());
    }

    @Test
    public void patchDog_shouldReturnUpdatedAge() {
        Dog input = new Dog();
        input.setAge(14);
        Mockito.when(mockDogRepository.findById(recordWithId.getId())).thenReturn(Optional.of(recordWithId));
        Mockito.when(mockDogRepository.save(Mockito.any())).thenAnswer(i -> i.getArguments()[0]);
        Dog response = dogService.patch(input, recordWithId.getId());
        assertEquals(recordWithId.getId(), response.getId());
        assertEquals("Maggie", response.getName());
        assertEquals("female", response.getGender());
        assertEquals("brown, black, white", response.getColor());
        assertEquals(14, response.getAge());
    }

    @Test
    public void deleteDogById_callsRepositoryDeleteMethod() {
        dogService.deleteDogById(id);
        Mockito.verify(mockDogRepository).deleteById(id);
    }


}