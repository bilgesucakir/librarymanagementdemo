package com.example.librarymanagementdemo.repository;

import com.example.librarymanagementdemo.entity.Author;
import com.example.librarymanagementdemo.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

// automatically considered a Spring bean by Spring Data JPA
public interface AuthorRepository extends JpaRepository<Author, Integer> { //2nd param is type of the id field in entity
    List<Author> findByBooksContaining(Book book);



}
