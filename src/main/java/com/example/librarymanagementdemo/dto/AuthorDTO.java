package com.example.librarymanagementdemo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;
import jakarta.validation.constraints.*;

public class AuthorDTO {

    private int id;

    @Pattern(regexp = "^(?:(?!\\s*$).+)?$", message = "Author name must contain at least one non-whitespace character")
    @Size(min=2, message="Author name must be at least two characters long")
    private String name;
    private String biography;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthdate;
    private String nationality;
    private List<Integer> bookIds;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public List<Integer> getBookIds() {
        return bookIds;
    }

    public void setBookIds(List<Integer> bookIds) {
        this.bookIds = bookIds;
    }

    public AuthorDTO(){}

    @Override
    public String toString() {
        return "AuthorDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", biography='" + biography + '\'' +
                ", birthdate=" + birthdate +
                ", nationality='" + nationality + '\'' +
                ", bookIds=" + bookIds +
                '}';
    }
}
