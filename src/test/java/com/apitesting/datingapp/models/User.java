package com.apitesting.datingapp.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

public class User {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("gender")
    private String gender;

    @JsonProperty("age")
    private Integer age;

    @JsonProperty("city")
    private String city;

    @JsonProperty("registrationDate")
    private String registrationDate;

    // Constructors
    public User() {}

    public User(Integer id, String name, String gender, Integer age,
                String city, String registrationDate) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.city = city;
        this.registrationDate = registrationDate;
    }

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getRegistrationDate() { return registrationDate; }
    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public String toString() {
        return String.format("User{id=%d, name='%s', gender='%s', age=%d, city='%s', registrationDate='%s'}",
                id, name, gender, age, city, registrationDate);
    }
}