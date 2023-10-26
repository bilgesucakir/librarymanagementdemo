package com.example.librarymanagementdemo.service;

import com.example.librarymanagementdemo.entity.Author;
import com.example.librarymanagementdemo.entity.LibraryUser;

import java.util.List;

public interface AuthorService {
    List<Author> findAll();

    Author findById(int id);

    Author save(Author author);

    void deleteById(int id);
}
