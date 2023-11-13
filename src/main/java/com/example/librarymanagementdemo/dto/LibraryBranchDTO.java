package com.example.librarymanagementdemo.dto;

import java.util.List;

public class LibraryBranchDTO {

    private int id;
    private String name;
    private String location;
    private Integer capacity;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public List<Integer> getBookIds() {
        return bookIds;
    }

    public void setBookIds(List<Integer> bookIds) {
        this.bookIds = bookIds;
    }

    public LibraryBranchDTO(){}

    @Override
    public String toString() {
        return "LibraryBranchDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", capacity=" + capacity +
                ", bookIds=" + bookIds +
                '}';
    }
}
