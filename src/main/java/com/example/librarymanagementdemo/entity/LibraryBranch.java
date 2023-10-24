package com.example.librarymanagementdemo.entity;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "librarybranch")
public class LibraryBranch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name="location")
    private String location;

    @Column(name="capacity")
    private int capacity;

    //librarybranch-book relationship
    @OneToMany(mappedBy = "libraryBranch",
    fetch = FetchType.LAZY,
    cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    private List<Book> books;


    public LibraryBranch(){}

    public LibraryBranch(String name, String location, int capacity) {
        this.name = name;
        this.location = location;
        this.capacity = capacity;
    }

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public void addBooks(Book book){
        if(books == null){
            books = new ArrayList<>();
        }
        books.add(book);

        book.setLibraryBranch(this);
    }

    @Override
    public String toString() {
        return "LibraryBranch{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", capacity=" + capacity +
                ", books=" + books +
                '}';
    }
}
