package com.example.librarymanagementdemo.service;

import com.example.librarymanagementdemo.dto.AuthorDTO;
import com.example.librarymanagementdemo.dto.BookDTO;
import com.example.librarymanagementdemo.entity.Author;
import com.example.librarymanagementdemo.entity.Book;
import com.example.librarymanagementdemo.entity.LibraryUser;

import java.util.Date;
import java.util.List;

public interface AuthorService {
    List<Author> findAll();

    Author findById(int id);

    Author save(Author author);

    void deleteById(int id);

    List<Author> findByBook(Book book);

    List<Author> findByFilter(String name, Date birthdate, String nationality);

    Author convertAuthorDTOToAuthorEntity(AuthorDTO dto);

}
