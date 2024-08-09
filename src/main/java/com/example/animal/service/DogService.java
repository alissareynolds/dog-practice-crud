package com.example.animal.service;

import com.example.animal.exceptions.DogNotFoundException;
import com.example.animal.model.Dog;
import com.example.animal.repository.DogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DogService {

    private final DogRepository dogRepository;

    public DogService(DogRepository dogRepository) {
        this.dogRepository = dogRepository;
    }

    public Dog create(Dog dog) {
        Dog newDog = new Dog(dog.getName(), dog.getGender(), dog.getColor(), dog.getAge());
        return dogRepository.save(newDog);
    }

    public List<Dog> findAllDogs() {
        return dogRepository.findAll();
    }

    public Dog getById(UUID id) {
        Optional<Dog> dog = dogRepository.findById(id);
        if (dog.isEmpty()) {
            throw new DogNotFoundException("A dog with id: " + id + " was not found.");
        }
        return dog.get();
    }

    public Dog getDogByName(String name) {
        Optional<Dog> dog = dogRepository.findByName(name);
        if (dog.isEmpty()) {
            throw new DogNotFoundException("A dog with name: " + name + " was not found.");
        }
        return dog.get();
    }

    public Dog updateDog(Dog dog, UUID id) {
        Optional<Dog> oldDog = dogRepository.findById(id);
        if (oldDog.isEmpty()) {
            throw new DogNotFoundException("A dog with id: " + id + " was not found.");
        }
        Dog updatedDog = new Dog(id, dog.getName(), dog.getGender(), dog.getColor(), dog.getAge());
        return dogRepository.save(updatedDog);
    }

    public Dog patch(Dog dog, UUID id) {
        Optional<Dog> originalDog = dogRepository.findById(id);
        if (originalDog.isEmpty()) {
            throw new DogNotFoundException("A dog with id: " + id + " was not found.");
        }
        Dog updatedDog = originalDog.get();
        if (dog.getName() != null) {
            updatedDog.setName(dog.getName());
        }
        if (dog.getGender() != null) {
            updatedDog.setGender(dog.getGender());
        }
        if (dog.getColor() != null) {
            updatedDog.setColor(dog.getColor());
        }
        if (dog.getAge() != null) {
            updatedDog.setAge(dog.getAge());
        }
        return dogRepository.save(updatedDog);
    }

    public Dog deleteDogById(UUID id) {
        Optional<Dog> dog = dogRepository.findById(id);
        if(dog.isEmpty()) {
            throw new DogNotFoundException("A dog with id: " + id + " was not found.");
        }
        dogRepository.delete(dog.get());
        return dog.get();
    }

}
