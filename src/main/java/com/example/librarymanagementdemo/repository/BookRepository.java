package com.example.librarymanagementdemo.repository;

import com.example.librarymanagementdemo.entity.Author;
import com.example.librarymanagementdemo.entity.Book;
import com.example.librarymanagementdemo.entity.LibraryBranch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findByLibraryBranch(LibraryBranch libraryBranch);

    List<Book> findByAuthorsContaining(Author author);

    @Query("SELECT b FROM Book b " +
            "WHERE (:title is null or b.title = :title) " +
            "AND (:ISBN is null or b.ISBN = :ISBN) " +
            "AND (:publicationYear is null or b.publicationYear = :publicationYear) " +
            "AND (:genre is null or b.genre = :genre) " +
            "AND (:available is null or b.available = :available) " +
            "AND (:multipleAuthors is null or b.multipleAuthors = :multipleAuthors)")
    List<Book> findByTitleAndISBNAndPublicationYearAndGenreAndAvailableEqualsAndMultipleAuthorsEquals(
            String title, String ISBN, Integer publicationYear, String genre, Boolean available, Boolean multipleAuthors
    );
}

