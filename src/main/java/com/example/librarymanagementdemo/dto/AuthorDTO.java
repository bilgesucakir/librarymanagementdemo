package com.example.librarymanagementdemo.dto;

import com.example.librarymanagementdemo.entity.Author;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.List;

public class AuthorDTO {

    private Author author;
    private List<Integer> bookIds;

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
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
                "author=" + author +
                ", bookIds=" + bookIds +
                '}';
    }
}
