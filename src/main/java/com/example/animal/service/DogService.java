package com.example.animal.service;

import com.example.animal.exceptions.DogNotFoundException;
import com.example.animal.model.Dog;
import com.example.animal.repository.DogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DogService {

    @Autowired
    DogRepository dogRepository;

    public Dog create(Dog dog) {
        Dog newDog = new Dog(dog.getName(), dog.getGender(), dog.getColor(), dog.getAge());
        return dogRepository.save(newDog);
    }

    public Dog getById(UUID id) {
        Optional<Dog> dog = dogRepository.findById(id);
        if (dog.isEmpty()) {
            throw new DogNotFoundException("A dog with that id was not found");
        }
        return dog.get();
    }

    public Dog getDogByName(String name) {
        Optional<Dog> dog = dogRepository.findByName(name);
        if (dog.isEmpty()) {
            throw new DogNotFoundException("A dog with that name was not found");
        }
        return dog.get();
    }

    public Dog updateDog(Dog dog, UUID id) {
        Optional<Dog> oldDog = dogRepository.findById(id);
        if (oldDog.isEmpty()) {
            throw new DogNotFoundException("A dog with that id does not exist");
        }
        Dog updatedDog = new Dog(id, dog.getName(), dog.getGender(), dog.getColor(), dog.getAge());
        return dogRepository.save(updatedDog);
    }

    public Dog deleteDogById(UUID id) {
        Optional<Dog> dog = dogRepository.findById(id);
        if(dog.isEmpty()) {
            throw new DogNotFoundException("A dog with that id does not exist");
        }
        dogRepository.delete(dog.get());
        return dog.get();
    }

    public List<Dog> findAllDogs() {
        return dogRepository.findAll();
    }
}
