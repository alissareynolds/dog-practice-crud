package com.example.animal.repository;

import com.example.animal.model.Dog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DogRepository extends JpaRepository<Dog, UUID> {
    Optional<Dog> findByName(String name);
}
