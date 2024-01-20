package com.example.librarymanagementdemo.service;

import com.example.librarymanagementdemo.dto.BookDTO;
import com.example.librarymanagementdemo.entity.Author;
import com.example.librarymanagementdemo.entity.Book;
import com.example.librarymanagementdemo.entity.LibraryBranch;

import java.util.List;

public interface BookService {

    List<Book> findAllWithOptionalFilter(String title, String ISBN, Integer publicationYear, String genre, Boolean available, Boolean multipleAuthors);

    Book findById(int id);

    Book save(Book book);

    void deleteById(int id);

    List<Book> findByAuthor(Author author);

    List<Book> findByLibraryBranch(LibraryBranch libraryBranch);

    Book setLibraryBranchOfBook(Book book, LibraryBranch libraryBranch);

    Book setAuthorsOfBook(Book book, List<Author> authors);

    Book setFieldsAndSaveBook(Book book, LibraryBranch libraryBranch, List<Author> authors);

    Book convertBookDTOToBookEntity(BookDTO dto);

    BookDTO convertBookEntityToBookDTO(Book book);

    Book updateBookPartially(Book book, BookDTO bookDTO);

    void validateAddBook(BookDTO bookDTO);

    void validateUpdateBook(BookDTO bookDTO);
}
