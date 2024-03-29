package com.example.librarymanagementdemo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.List;

public class LibraryUserDTO {

    private int id;

    @NotNull(message = "Username field not provided") //non existent ekle
    private String username;

    @NotNull(message = "Email field not provided")
    @Email(message="Wrong email format") //non existent ekle
    private String email;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date registrationDate;

    private List<Integer> checkoutIds;

    public LibraryUserDTO(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public List<Integer> getCheckoutIds() {
        return checkoutIds;
    }

    public void setCheckoutIds(List<Integer> checkoutIds) {
        this.checkoutIds = checkoutIds;
    }


    @Override
    public String toString() {
        return "LibraryUserDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", registrationDate=" + registrationDate +
                ", checkoutIds=" + checkoutIds +
                '}';
    }
}
