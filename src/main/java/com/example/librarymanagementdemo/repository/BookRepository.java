package com.example.librarymanagementdemo.repository;

import com.example.librarymanagementdemo.entity.Author;
import com.example.librarymanagementdemo.entity.Book;
import com.example.librarymanagementdemo.entity.Checkout;
import com.example.librarymanagementdemo.entity.LibraryBranch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findByLibraryBranch(LibraryBranch libraryBranch);

    List<Book> findByAuthorsContaining(Author author);

    List<Book> findByCheckoutsContaining(Checkout checkout);

    List<Book> findByTitle(String title);

    List<Book> findByISBN(String ISBN);

    List<Book> findByAvailable(boolean available);

    List<Book> findByMultipleAuthors(boolean multipleAuthors);

}
