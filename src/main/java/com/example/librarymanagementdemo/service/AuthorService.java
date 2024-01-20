package com.example.librarymanagementdemo.service;

import com.example.librarymanagementdemo.dto.AuthorDTO;
import com.example.librarymanagementdemo.entity.Author;
import com.example.librarymanagementdemo.entity.Book;

import java.util.Date;
import java.util.List;

public interface AuthorService {
    List<Author> findAllWithOptionalfilter(String name, Date birthdate, String nationality);

    Author findById(int id);

    Author save(Author author);

    void deleteById(int id);

    List<Author> findByBook(Book book);

    Author convertAuthorDTOToAuthorEntity(AuthorDTO dto);

    AuthorDTO convertAuthorEntityToAuthorDTO(Author author);

    Author updateAuthorPartially(Author author, AuthorDTO authorDTO);

    Author setBooksAndSaveAuthor(Author author, List<Book> books);

    void validateAuthor(AuthorDTO authorDTO);
}
