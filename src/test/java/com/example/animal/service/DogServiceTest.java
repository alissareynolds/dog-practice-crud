package com.example.animal.service;

import com.example.animal.model.Dog;
import com.example.animal.repository.DogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class DogServiceTest {

    private DogService dogService;
    private DogRepository mockDogRepository;

    public final Dog input = new Dog(null, "Maggie", "female", "brown, black, white", 13);
    public final Dog input2 = new Dog(null, "Dakota", "female", "black", 5);
    public final Dog recordWithId = new Dog(UUID.randomUUID(), "Maggie", "female", "brown, black, white", 13);
    public final Dog recordWithId2 = new Dog(recordWithId.getId(), "Dakota", "female", "black", 5);


    @BeforeEach
    public void setup() {
        mockDogRepository = Mockito.mock(DogRepository.class);
        dogService = new DogService(mockDogRepository);
    }


}