package com.example.animal.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "dogs")
public class Dog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    private String gender;

    private String color;

    private Integer age;

    public Dog() {

    }

    public Dog(UUID id, String name, String gender, String color, Integer age) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.color = color;
        this.age = age;
    }

    public Dog(String name, String gender, String color, Integer age) {
        this.name = name;
        this.gender = gender;
        this.color = color;
        this.age = age;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }


}
