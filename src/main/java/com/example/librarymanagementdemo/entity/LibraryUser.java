package com.example.librarymanagementdemo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Table(name="libraryuser")
public class LibraryUser { //only contains non-sensitive information, password will not be kept here.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="username")
    private String username;

    @Column(name="email")
    private String email;

    @Column(name="registration_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date registrationDate;

    //user-checkout relationship //jsonmanagedreference?
    @OneToMany(mappedBy = "libraryUser",
    fetch = FetchType.LAZY,
    cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Checkout> checkouts;

    public LibraryUser(){}

    public LibraryUser(String username, String email, Date registrationDate) {
        this.username = username;
        this.email = email;
        this.registrationDate = registrationDate;
    }

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

    public List<Checkout> getCheckouts() {
        return checkouts;
    }

    public void setCheckouts(List<Checkout> checkouts) {
        this.checkouts = checkouts;
    }

    public void addCheckouts(Checkout checkout){
        if(checkouts == null){
            checkouts = new ArrayList<>();
        }
        checkouts.add(checkout);

        checkout.setLibraryUser(this);

    }

    @Override
    public String toString() {
        return "LibraryUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", registrationDate=" + registrationDate +
                //", checkouts=" + checkouts +
                '}';
    }
}
