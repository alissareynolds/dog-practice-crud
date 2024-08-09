package com.example.animal.controller;

import com.example.animal.exceptions.DogNotFoundException;
import com.example.animal.model.Dog;
import com.example.animal.service.DogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/dogs")
public class DogController {

    private final DogService dogService;

    public DogController(DogService dogService) {
        this.dogService = dogService;
    }

    @PostMapping
    public ResponseEntity<Dog> createDog(@RequestBody Dog dog) {
        Dog newDog = dogService.create(dog);
        return new ResponseEntity<>(newDog, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Dog>> getAllDogs() {
        List<Dog> dogs = dogService.findAllDogs();
        return new ResponseEntity<>(dogs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dog> getDogById(@PathVariable UUID id) {
        Dog newDog;
        try {
            newDog = dogService.getById(id);
        } catch (DogNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(newDog, HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Dog> getDogByName(@PathVariable String name) {
        Dog newDog;
        try {
            newDog = dogService.getDogByName(name);
        } catch (DogNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(newDog, HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Dog> updateDog(@RequestBody Dog dog, @PathVariable UUID id) {
        Dog updatedDog;
        try {
            updatedDog = dogService.updateDog(dog, id);
        } catch (DogNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedDog, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Dog> patchDog(@RequestBody Dog dog, @PathVariable UUID id) {
        Dog updatedDog;
        try {
            updatedDog = dogService.patch(dog, id);
        } catch (DogNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedDog, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Dog> deleteDogById(@PathVariable UUID id) {
        Dog dog;
        try {
            dog = dogService.deleteDogById(id);
        } catch (DogNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(dog, HttpStatus.OK);
    }

}
